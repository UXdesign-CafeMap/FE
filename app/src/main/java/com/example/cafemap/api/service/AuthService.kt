package com.example.cafemap.api.service

import com.example.cafemap.api.ApiResponse
import com.example.cafemap.api.RetrofitClient.authRepository
import com.example.cafemap.api.model.dto.SignInRequest
import com.example.cafemap.api.model.dto.SignInResponse
import com.example.cafemap.api.model.dto.SignUpRequest
import com.example.cafemap.api.model.dto.SignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AuthService {
    fun signUp(email: String, password: String,  onSuccess: (SignUpResponse) -> Unit = {},  onFailure: (Throwable) -> Unit = {}) {
        val request = SignUpRequest(email, password)
        authRepository.signUp(request).enqueue(object : Callback<ApiResponse<SignUpResponse>> {
            override fun onResponse(call: Call<ApiResponse<SignUpResponse>>, response: Response<ApiResponse<SignUpResponse>>) {
                if (response.isSuccessful) {
                    response.body()?.result?.let(onSuccess)
                } else {
                    onFailure(RuntimeException("Failed to sign up"))
                }
            }

            override fun onFailure(call: Call<ApiResponse<SignUpResponse>>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    fun signIn( email: String, password: String, onSuccess: (SignInResponse) -> Unit = {}, onFailure: (Throwable) -> Unit = {}) {
        val request = SignInRequest(email, password)
        authRepository.signIn(request).enqueue(object : Callback<ApiResponse<SignInResponse>> {
            override fun onResponse(call: Call<ApiResponse<SignInResponse>>, response: Response<ApiResponse<SignInResponse>>) {
                if (response.isSuccessful) {
                    response.body()?.result?.let(onSuccess)
                } else {
                    onFailure(RuntimeException("Failed to sign in"))
                }
            }

            override fun onFailure(call: Call<ApiResponse<SignInResponse>>, t: Throwable) {
                onFailure(t)
            }
        })
    }
}