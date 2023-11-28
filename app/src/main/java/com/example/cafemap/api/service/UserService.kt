package com.example.cafemap.api.service

import com.example.cafemap.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserService {
    private val userRepository = RetrofitClient.userRepository
    fun getUser(userId: String) {
        // userApiService 사용...
    }

    fun signUp(email: String, password: String, callback: (Boolean, String) -> Unit){
        userRepository.signUp(email, password).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                // 성공 처리...
                callback(true, "회원가입에 성공했습니다.");
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // 실패 처리...
            }
        })
    }
}