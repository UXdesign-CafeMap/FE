package com.example.cafemap.api.repository


import com.example.cafemap.api.ApiResponse
import com.example.cafemap.api.model.dto.BaseResponse
import com.example.cafemap.api.model.dto.CreateReviewRequest
import com.example.cafemap.api.model.dto.CreateReviewResponse
import com.example.cafemap.api.model.dto.GetReviewCountResponse
import com.example.cafemap.api.model.dto.GetReviewResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewRepository {
    @POST("review/")
    fun createReview( @Body request: CreateReviewRequest): Call<ApiResponse<CreateReviewResponse>>
    @GET("/review/")
    fun getReview(
        @Query("cafeId")
        postId: Int
    ): Call<BaseResponse<GetReviewResponse>>
    @GET("/review/count")
    fun getReviewCount(): Call<ApiResponse<GetReviewCountResponse>>
}