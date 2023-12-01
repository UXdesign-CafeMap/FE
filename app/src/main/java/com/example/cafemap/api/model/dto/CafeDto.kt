package com.example.cafemap.api.model.dto

import com.google.gson.annotations.SerializedName

data class BaseResponse<T> (
    val code: Int,
    val status: String,
    val message: String,
    val result: T
)
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

data class Review(
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("cafeId")
    val cafeId: Int,
    @SerializedName("content")
    val content: String,
    @SerializedName("imgUrlList")
    val imgUrlList: List<String>
)

data class CafeIdRequest (
    @SerializedName("cafeId") val cafeId: Int
)

data class LocationRequest (
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
)

data class MemberIdRequest (
    @SerializedName("memberId") val memberId: Int
)

data class MemberRequest (
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
)
data class CafeDetailResponse (
    @SerializedName("cafeId") val cafeId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("volume") val volume: String,
    @SerializedName("totalSeat") val totalSeat: Int,
    @SerializedName("remainSeat") val remainSeat: Int,
    @SerializedName("totalMultitap") val totalMultitap: Int,
    @SerializedName("remainMultitap") val remainMultitap: Int,
    @SerializedName("distance") val distance: String,
    @SerializedName("address") val address: String,
    @SerializedName("openingHours") val openingHours: String,
    @SerializedName("isOpen") val isOpen: String,
    @SerializedName("cafeImage") val cafeImage: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("menus") val menus: List<Menu>,
)

data class CafeListResponse (
    @SerializedName("cafeList") val cafeList: List<Cafe>
)

data class MarkerCafeResponse (
    @SerializedName("cafeId") val cafeId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("volume") val volume: String,
    @SerializedName("totalSeat") val totalSeat: Int,
    @SerializedName("remainSeat") val remainSeat: Int,
    @SerializedName("totalMultitap") val totalMultitap: Int,
    @SerializedName("remainMultitap") val remainMultitap: Int,
    @SerializedName("distance") val distance: String,
    @SerializedName("address") val address: String,
    @SerializedName("openingHours") val openingHours: String,
    @SerializedName("cafeImage") val cafeImage: String,
)

data class MemberResponse (
    @SerializedName("memberId") val memberId: Int
)

data class MyNearCafeListResponse (
    @SerializedName("myNearCafeList") val myNearCafeList: List<MyNearCafe>
)

data class ReviewResponse (
    @SerializedName("reviewList") val reviewList: List<Review>
)
data class ReviewRequest (
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("cafeId") val cafeId: Int,
    @SerializedName("content") val content: String,
)

data class SearchRequest (
    @SerializedName("search") val search: String
)
