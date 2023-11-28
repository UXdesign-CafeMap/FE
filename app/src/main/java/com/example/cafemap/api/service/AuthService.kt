package com.example.cafemap.api.service

import android.util.Log
import com.example.cafemap.api.MemberRequest
import com.example.cafemap.api.MemberResponse
import com.example.cafemap.api.RetrofitClient
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AuthService {
    private val userRepository = RetrofitClient.authRepository
    fun getUser(userId: String) {
        // userApiService 사용...
    }

    fun signUp(email: String, password: String, callback: (Boolean) -> Unit){
        userRepository.signUp(email, password).enqueue(object : Callback<MemberResponse> {
            override fun onResponse(
                call: Call<MemberResponse>,
                response: Response<MemberResponse>
            ) {
                if(response.isSuccessful) { // 성공 응답
                    callback(true)
                } else { // 실패 응답
                    Log.d("seohyun", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
                // 통신 실패
                Log.d("seohyun",t.message.toString())
            }
        })
    }

//    fun signIn (email: String, password: String, callback: (Boolean) -> Unit){
//        val signInRequest = SignInRequest(email, password)
//        userRepository.signIn(signInRequest).enqueue(object : Callback<Void> {
//            override fun onResponse(call: Call<Void>, response: Response<Void>) {
//                // 성공 처리...
//                callback(true)
//            }
//
//            override fun onFailure(call: Call<Void>, t: Throwable) {
//                // 실패 처리...
//                callback(false)
//            }
//        })
//    }
}