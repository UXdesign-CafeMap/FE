package com.example.cafemap.api

import com.example.cafemap.api.repository.UserRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// RetrofitClient.kt
object RetrofitClient {
    private const val BASE_URL = "https://15.165.63.107:9000"

    private val retrofit:Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val userRepository: UserRepository by lazy {
        retrofit.create(UserRepository::class.java)
    }
}