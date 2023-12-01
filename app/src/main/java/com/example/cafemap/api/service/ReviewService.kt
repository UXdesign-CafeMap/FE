package com.example.cafemap.api.service

import com.example.cafemap.api.ApiResponse
import com.example.cafemap.api.RetrofitClient.reviewRepository
import com.example.cafemap.api.model.domain.Review
import com.example.cafemap.api.model.dto.CreateReviewRequest
import com.example.cafemap.api.model.dto.CreateReviewResponse
import com.example.cafemap.api.model.dto.GetReviewCountResponse
import com.example.cafemap.api.model.dto.GetReviewResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
object ReviewService {
    fun createReview(postId: String, review: Review, onSuccess: (CreateReviewResponse) -> Unit, onFailure: (Throwable) -> Unit) {
        val createReviewRequest = CreateReviewRequest(
            memberId = review.memberId,
            cafeId = review.cafeId,
            content = review.content,
            imgUrlList = review.imgUrlList
        )

        reviewRepository.createReview(postId, createReviewRequest).enqueue(object : Callback<ApiResponse<CreateReviewResponse>> {
            override fun onResponse(call: Call<ApiResponse<CreateReviewResponse>>, response: Response<ApiResponse<CreateReviewResponse>>) {
                if (response.isSuccessful) {
                    response.body()?.result?.let(onSuccess)
                } else {
                    onFailure(RuntimeException("Failed to create review"))
                }
            }

            override fun onFailure(call: Call<ApiResponse<CreateReviewResponse>>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    fun getReview(postId: String, onSuccess: (GetReviewResponse) -> Unit, onFailure: (Throwable) -> Unit) {
        reviewRepository.getReview(postId).enqueue(object : Callback<ApiResponse<GetReviewResponse>> {
            override fun onResponse(call: Call<ApiResponse<GetReviewResponse>>, response: Response<ApiResponse<GetReviewResponse>>) {
                if (response.isSuccessful) {
                    response.body()?.result?.let(onSuccess)
                } else {
                    onFailure(RuntimeException("Failed to get review"))
                }
            }

            override fun onFailure(call: Call<ApiResponse<GetReviewResponse>>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    fun getReviewCount(onSuccess: (GetReviewCountResponse) -> Unit, onFailure: (Throwable) -> Unit) {
        reviewRepository.getReviewCount().enqueue(object : Callback<ApiResponse<GetReviewCountResponse>> {
            override fun onResponse(call: Call<ApiResponse<GetReviewCountResponse>>, response: Response<ApiResponse<GetReviewCountResponse>>) {
                if (response.isSuccessful) {
                    response.body()?.result?.let(onSuccess)
                } else {
                    onFailure(RuntimeException("Failed to get review count"))
                }
            }

            override fun onFailure(call: Call<ApiResponse<GetReviewCountResponse>>, t: Throwable) {
                onFailure(t)
            }
        })
    }
}