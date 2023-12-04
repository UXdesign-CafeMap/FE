package com.example.cafemap.api.service

import android.util.Log
import com.example.cafemap.api.ApiResponse
import com.example.cafemap.api.RetrofitClient
import com.example.cafemap.api.RetrofitClient.reviewRepository
import com.example.cafemap.api.model.domain.Review
import com.example.cafemap.api.model.dto.BaseResponse
import com.example.cafemap.api.model.dto.CreateReviewRequest
import com.example.cafemap.api.model.dto.CreateReviewResponse
import com.example.cafemap.api.model.dto.GetReviewCountResponse
import com.example.cafemap.api.model.dto.GetReviewResponse
import com.example.cafemap.ui.cafe.ReviewViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
object ReviewService {
    private val userRepository = RetrofitClient.reviewRepository

    private val reviews = ReviewViewModel()

    fun createReview( review: Review, onSuccess: (CreateReviewResponse) -> Unit, onFailure: (Throwable) -> Unit) {
        val createReviewRequest = CreateReviewRequest(
            memberId = review.memberId,
            cafeId = review.cafeId,
            content = review.content,
            imgUrlList = review.reviewImgList
        )

        reviewRepository.createReview( createReviewRequest).enqueue(object : Callback<ApiResponse<CreateReviewResponse>> {
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

    fun getReview(postId: Int) {

        userRepository.getReview(postId).enqueue(object : Callback<BaseResponse<GetReviewResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<GetReviewResponse>>,
                response: Response<BaseResponse<GetReviewResponse>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.result?.reviewList.let{
                        if (it != null) {
                            reviews.setReview(it)
                            Log.d("seohyunReview",it.toString())
                        }
                    }
                } else {
                    Log.d("seohyun", response.errorBody().toString())
                }
            }
            override fun onFailure(call: Call<BaseResponse<GetReviewResponse>>, t: Throwable) {
                Log.d("seohyun", t.message.toString())
            }
        })
    }

    fun getReviews() : ReviewViewModel {
        return reviews
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