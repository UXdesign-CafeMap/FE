package com.example.cafemap.api.model.domain

import com.google.gson.annotations.SerializedName
data class Cafe (
    @SerializedName("cafeId") val cafeId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("totalSeat") val totalSeat: Int,
    @SerializedName("remainSeat") val remainSeat: Int,
    @SerializedName("distance") val distance: String,
    @SerializedName("review") val review: String,
    @SerializedName("cafeImage") val cafeImage: String,
)