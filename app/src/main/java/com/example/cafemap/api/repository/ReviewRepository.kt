package com.example.cafemap.api.repository


import com.example.cafemap.api.ApiResponse
import com.example.cafemap.api.model.dto.CreateReviewRequest
import com.example.cafemap.api.model.dto.CreateReviewResponse
import com.example.cafemap.api.model.dto.GetReviewCountResponse
import com.example.cafemap.api.model.dto.GetReviewResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewRepository {
    @POST("review/{post_id}")
    fun createReview(@Path("post_id") postId: String, @Body request: CreateReviewRequest): Call<ApiResponse<CreateReviewResponse>>
    @GET("review/{post_id}")
    fun getReview(@Path("post_id") postId: String): Call<ApiResponse<GetReviewResponse>>
    @GET("review/count")
    fun getReviewCount(): Call<ApiResponse<GetReviewCountResponse>>
}