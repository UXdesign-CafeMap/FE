package com.example.cafemap.api.model.domain

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Review (
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("reviewId") val reviewId: Int? = null,
    @SerializedName("uploadData") val uploadData: Date,
    @SerializedName("content") val content: String,
    @SerializedName("imgUrlList") val imgUrlList: List<String>,

    @SerializedName("cafeId") val cafeId: Int
)
