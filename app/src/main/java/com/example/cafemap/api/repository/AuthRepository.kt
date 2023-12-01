package com.example.cafemap.api.repository

import com.example.cafemap.api.ApiResponse
import com.example.cafemap.api.model.dto.SignInRequest
import com.example.cafemap.api.model.dto.SignInResponse
import com.example.cafemap.api.model.dto.SignUpRequest
import com.example.cafemap.api.model.dto.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRepository {
    @POST("member/signup")
    fun signUp(@Body request: SignUpRequest): Call<ApiResponse<SignUpResponse>>
    @POST("member/signin")
    fun signIn(@Body request: SignInRequest): Call<ApiResponse<SignInResponse>>
}