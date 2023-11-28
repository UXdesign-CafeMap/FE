package com.example.cafemap.api.service

import com.example.cafemap.api.RetrofitClient
import com.example.cafemap.api.model.SignInRequest
import com.example.cafemap.api.model.SignUpRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AuthService {
    private val userRepository = RetrofitClient.authRepository
    fun getUser(userId: String) {
        // userApiService 사용...
    }

    fun signUp(email: String, password: String, callback: (Boolean) -> Unit){
        val signUpRequest = SignUpRequest(email, password)
        userRepository.signUp(signUpRequest).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                // 성공 처리...
                callback(true)
                // set preference
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // 실패 처리...
                callback(false)
            }
        })
    }

    fun signIn (email: String, password: String, callback: (Boolean) -> Unit){
        val signInRequest = SignInRequest(email, password)
        userRepository.signIn(signInRequest).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                // 성공 처리...
                callback(true)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // 실패 처리...
                callback(false)
            }
        })
    }
}