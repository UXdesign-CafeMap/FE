package com.example.cafemap.ui.auth

import android.content.Intent
import com.example.cafemap.R
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cafemap.MainActivity
import com.example.cafemap.api.service.AuthService


class SignInActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signInButton: Button
    private lateinit var authService: AuthService
    private lateinit var findIdTextButton: TextView
    private lateinit var findPasswordTextButton: TextView
    private lateinit var signUpTextButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        authService = AuthService

        emailEditText = findViewById<EditText>(R.id.et_li_email)
        passwordEditText = findViewById<EditText>(R.id.et_li_password)
        signInButton = findViewById<Button>(R.id.bt_li_sign_in)
        findIdTextButton = findViewById(R.id.bt_li_find_id)
        findPasswordTextButton = findViewById(R.id.bt_li_find_password)
        signUpTextButton = findViewById(R.id.bt_li_signup)

        signInButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (!isInputValid(email, password)) {
                return@setOnClickListener
            }
            performSignIn(email, password)
        }

        signUpTextButton.setOnClickListener {
            val signUpIntent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(signUpIntent)
        }
    }

    private fun isInputValid(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            return false
        }
        return true
    }

    private fun performSignIn(email: String, password: String) {
        authService.signIn(email, password
            , onSuccess = { data ->
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()

                // SharedPreferences를 사용하여 사용자 memberId, email, nickname 저장
                val sp = getSharedPreferences("pref", MODE_PRIVATE)
                val editor = sp.edit()
                editor.putInt("memberId", data.memberId)
                editor.putString("email", data.email)
                editor.putString("nickname", data.nickname)
                editor.apply()

                val mainIntent = Intent(this@SignInActivity, MainActivity::class.java)
                startActivity(mainIntent)
            }
            , onFailure = { t ->
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        )
    }
}