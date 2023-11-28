package com.example.cafemap.retrofit

import com.google.gson.annotations.SerializedName
import java.util.Date

//data class Border(
//    @SerializedName("id") val id: Int,
//    @SerializedName("memberBorders") val memberBorders: List<MemberBorder>
//)

data class Cafe (
    @SerializedName("cafeId") val cafeId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("totalSeat") val totalSeat: Int,
    @SerializedName("remainSeat") val remainSeat: Int,
    @SerializedName("distance") val distance: String,
    @SerializedName("review") val review: String,
    @SerializedName("cafeImage") val cafeImage: String,
)

data class Member (
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
)

data class Menu (
    @SerializedName("menuName") val menuName: String,
    @SerializedName("menuPrice") val menuPrice: String,
)

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

data class Review (
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("uploadDate") val uploadDate: Date,
    @SerializedName("content") val content: String,
    @SerializedName("reviewImg") val reviewImg: String,
)