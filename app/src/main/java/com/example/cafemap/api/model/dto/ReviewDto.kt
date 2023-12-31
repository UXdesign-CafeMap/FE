package com.example.cafemap.api.model.dto

import com.example.cafemap.api.model.domain.Review
import com.google.gson.annotations.SerializedName

data class CreateReviewRequest(
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("cafeId")
    val cafeId: Int,
    @SerializedName("content")
    val content: String,
    @SerializedName("imgUrlList")
    val imgUrlList: List<String>
)

data class CreateReviewResponse(
    @SerializedName("reviewId")
    val reviewId: Int
)


data class GetReviewResponse(
    @SerializedName("reviewList") val reviewList: List<Review>
)

data class GetReviewCountResponse(
    @SerializedName("count")
    val count: Int
)