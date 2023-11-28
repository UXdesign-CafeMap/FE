package com.example.cafemap.ui.login

import android.os.Bundle
import android.util.Patterns.EMAIL_ADDRESS
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cafemap.R
import com.example.cafemap.api.service.UserService
class SignUpActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var checkPasswordEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var userService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        userService = UserService;

        // UI 요소 연결
        emailEditText = findViewById(R.id.et_su_email)
        passwordEditText = findViewById(R.id.et_su_password)
        checkPasswordEditText = findViewById(R.id.et_su_password_again)
        signUpButton = findViewById(R.id.bt_li_login_button)

        // 회원가입 버튼 클릭 이벤트 처리
        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val checkPassword = checkPasswordEditText.text.toString().trim()

            // 입력값 검증
            if (!isInputValid(email, password, checkPassword)) {
                Toast.makeText(this, "올바른 정보를 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            performSignUp(email, password)
        }
    }

    // 입력값 검증 함수
    private fun isInputValid(email: String, password: String, checkPassword: String): Boolean {
        if (email.isEmpty() || password.isEmpty() || checkPassword.isEmpty()) {
            return false
        }
        if (password != checkPassword) {
            return false
        }
        if(!EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "이메일 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
            return false
        }
        if(password.length < 8) {
            Toast.makeText(this, "비밀번호는 8자리 이상이어야 합니다.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun performSignUp(email: String, password: String) {
        // 서버에 회원가입 요청을 보내는 코드
        // 예: Retrofit을 사용한 네트워크 요청
        userService.signUp(email, password) { isSuccess, message ->
            if (isSuccess) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}