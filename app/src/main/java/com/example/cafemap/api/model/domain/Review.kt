package com.example.cafemap.api.model.domain

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Review (
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("reviewId") val reviewId: Int? = null,
    @SerializedName("upload_date") val uploadDate: Date? = null,
    @SerializedName("content") val content: String,
    @SerializedName("reviewImgList") val reviewImgList: List<String>,
    @SerializedName("cafeId") val cafeId: Int
)
