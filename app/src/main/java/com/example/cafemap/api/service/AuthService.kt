package com.example.cafemap.api.service

import com.example.cafemap.api.ApiResponse
import com.example.cafemap.api.RetrofitClient.authRepository
import com.example.cafemap.api.RetrofitClient.nicknameRepository
import com.example.cafemap.api.model.dto.NicknameDto
import com.example.cafemap.api.model.dto.SignInRequest
import com.example.cafemap.api.model.dto.SignInResponse
import com.example.cafemap.api.model.dto.SignUpRequest
import com.example.cafemap.api.model.dto.SignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AuthService {
    fun signUp(email: String, password: String,  onSuccess: (SignUpResponse) -> Unit = {},  onFailure: (Throwable) -> Unit = {}) {
        nicknameRepository.getRandomNickname("json", 1, 8).enqueue(object : Callback<NicknameDto> {
            override fun onResponse(call: Call<NicknameDto>, response: Response<NicknameDto>) {
                if (response.isSuccessful) {
                    println(response.body())
                    val signUpRequest = SignUpRequest(email,
                        response.body()?.words?.get(0) ?: "-",
                        password)
                    authRepository.signUp(signUpRequest).enqueue(object : Callback<ApiResponse<SignUpResponse>> {
                        override fun onResponse(call: Call<ApiResponse<SignUpResponse>>, response: Response<ApiResponse<SignUpResponse>>) {
                            if (response.isSuccessful) {
                                response.body()?.result?.let(onSuccess)
                            } else {
                                onFailure(RuntimeException(response.body()?.message))
                            }
                        }
                        override fun onFailure(call: Call<ApiResponse<SignUpResponse>>, t: Throwable) {
                            onFailure(t)
                        }
                    })
                } else {
                    onFailure(RuntimeException("Failed to get random nickname: " + response.body()))
                }
            }
            override fun onFailure(call: Call<NicknameDto>, t: Throwable) {
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
                    onFailure(RuntimeException(response.body()?.message))
                }
            }
            override fun onFailure(call: Call<ApiResponse<SignInResponse>>, t: Throwable) {
                onFailure(t)
            }
        })
    }
}