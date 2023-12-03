package com.example.cafemap.api.model.dto

import com.example.cafemap.api.model.domain.Cafe
import com.example.cafemap.api.model.domain.Menu
import com.example.cafemap.api.model.domain.MyNearCafe
import com.example.cafemap.api.model.domain.Review
import com.google.gson.annotations.SerializedName

data class BaseResponse<T> (
    val code: Int,
    val status: String,
    val message: String,
    val result: T
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
    @SerializedName("onpeningHours") val onpeningHours: String,
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
    @SerializedName("onpeningHours") val onpeningHours: String,
    @SerializedName("cafeImage") val cafeImage: String,
)

data class MemberResponse (
    @SerializedName("memberId") val memberId: Int
)

data class MyNearCafeListResponse (
    @SerializedName("myNearCafeList") val myNearCafeList: List<MyNearCafe>
)

data class SearchRequest (
    @SerializedName("search") val search: String
)
