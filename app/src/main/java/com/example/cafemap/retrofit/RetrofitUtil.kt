package com.example.cafemap.retrofit

import com.example.cafemap.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val BASE_URL = BuildConfig.BASE_URL

object RetrofitUtil {
    private var instance: RetrofitInterface? = null

    fun getOKHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    fun getRetrofitUtil(): RetrofitInterface {
        if (instance == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOKHttpClient())
                .build()
            instance = retrofit.create(RetrofitInterface::class.java)
        }
        return instance!!
    }
}