package com.example.cafemap.api.model.domain

import com.google.gson.annotations.SerializedName

data class MyNearCafe (
    @SerializedName("cafeId") val cafeId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("totalSeat") val totalSeat: Int,
    @SerializedName("remainSeat") val remainSeat: Int,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("distance") val distance: String,
//    @SerializedName("review") val review: String,
    @SerializedName("cafeImage") val cafeImage: String,
)
