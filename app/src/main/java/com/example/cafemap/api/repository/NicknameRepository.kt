package com.example.cafemap.api.repository

import com.example.cafemap.api.model.dto.NicknameDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NicknameRepository {
    @GET("/")
    fun getRandomNickname(
        @Query ("format") format: String,
        @Query ("count") count: Int,
        @Query ("max_length") maxLength: Int,
    ): Call<NicknameDto>
}