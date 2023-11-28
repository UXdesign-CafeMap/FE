package com.example.cafemap.api.repository

import com.example.cafemap.api.model.SignInRequest
import com.example.cafemap.api.model.SignUpRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRepository {
    @POST("member/signup")
    fun signUp(@Body request: SignUpRequest): Call<Void>

    @POST("member/signin")
    fun signIn(@Body request: SignInRequest): Call<Void>
}