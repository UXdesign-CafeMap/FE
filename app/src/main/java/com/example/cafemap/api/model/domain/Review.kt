package com.example.cafemap.api.model.domain

import com.google.gson.annotations.SerializedName

data class Review (
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("reviewId") val reviewId: Int? = null,
    @SerializedName("cafeId") val cafeId: Int,
    @SerializedName("content") val content: String,
    @SerializedName("imgUrlList") val imgUrlList: List<String>,
)
