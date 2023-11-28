package com.example.cafemap.api.repository

import com.example.cafemap.api.MemberRequest
import com.example.cafemap.api.MemberResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthRepository {
    @FormUrlEncoded
    @POST("/member/signup")
    fun signUp(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<MemberResponse>

    @FormUrlEncoded
    @POST("/member/signin")
    fun signIn(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<Void>
}