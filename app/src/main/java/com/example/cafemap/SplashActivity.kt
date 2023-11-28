package com.example.cafemap

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)
            // 로그인이 되어있는지 확인
            val isSignedIn = checkSignIn()
            // 로그인 상태에 따른 화면 이동
            if (isSignedIn) {
                // 로그인이 되어 있으면 메인 화면으로 이동
                val mainIntent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(mainIntent)
            } else {
                // 로그인이 안 되어 있으면 로그인 화면으로 이동
                val signInIntent = Intent(this@SplashActivity, com.example.cafemap.ui.auth.SignInActivity::class.java)
                startActivity(signInIntent)
            }
            // 현재 액티비티 종료
            finish()
        }

    }

    private fun checkSignIn(): Boolean {
        val sharedPreferences = getSharedPreferences("sign_in_sp", MODE_PRIVATE)

        return false
    }
}