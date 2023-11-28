package com.example.cafemap.api.repository

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserRepository {
    @FormUrlEncoded
    @POST("member/signup")
    fun signUp(@Field("email") email: String, @Field("password") password: String): Call<Void>
}