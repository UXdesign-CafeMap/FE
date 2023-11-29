package com.example.cafemap.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns.EMAIL_ADDRESS
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cafemap.R
import com.example.cafemap.api.service.AuthService
class SignUpActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordCheckEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var authService: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        authService = AuthService

        emailEditText = findViewById(R.id.et_su_email)
        passwordEditText = findViewById(R.id.et_su_password)
        passwordCheckEditText = findViewById(R.id.et_su_password_check)
        signUpButton = findViewById(R.id.bt_li_sign_up_button)

        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val checkPassword = passwordCheckEditText.text.toString().trim()

            // validation
            if(email.isEmpty()){
                Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(checkPassword.isEmpty()){
                Toast.makeText(this, "비밀번호 확인란을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password != checkPassword) {
                Toast.makeText(this, "비밀번호와 비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "이메일 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password.length < 10) {
                Toast.makeText(this, "비밀번호는 10자 이상이어야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            performSignUp(email, password)
        }
    }

    private fun performSignUp(email: String, password: String) {
        authService.signUp(email, password,
            onSuccess = { data ->
                Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                // 로그인 화면으로 이동
                val signInIntent = Intent(this@SignUpActivity, SignInActivity::class.java)
                startActivity(signInIntent)
            },
            onFailure = {
                Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
            }
        )
    }
}