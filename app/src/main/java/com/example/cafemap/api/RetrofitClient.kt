package com.example.cafemap.api

import com.example.cafemap.api.repository.AuthRepository
import com.example.cafemap.api.repository.ListRepository
import com.example.cafemap.api.repository.CafeRepository
import com.example.cafemap.api.repository.ReviewRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor

// RetrofitClient.kt
object RetrofitClient {
    private const val BASE_URL = "http://15.165.63.107:9000"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit:Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authRepository: AuthRepository by lazy {
        retrofit.create(AuthRepository::class.java)
    }

    val searchRepository: ListRepository by lazy {
        retrofit.create(ListRepository::class.java)
    }
    val reviewRepository: ReviewRepository by lazy {
        retrofit.create(ReviewRepository::class.java)
    }

    val cafeRepository: CafeRepository by lazy {
        retrofit.create(CafeRepository::class.java)

    }
}