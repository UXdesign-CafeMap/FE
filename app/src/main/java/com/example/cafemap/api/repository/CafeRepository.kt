package com.example.cafemap.api.repository

import com.example.cafemap.api.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CafeRepository {
    // 카페 전체 조회
    @GET("cafe/")
    fun fetchCafeList(): Call<Void>
    // 카페 상세 조회
    @GET("cafe/{cafe_id}")
    fun getCafeDetail(@Path("cafe_id") cafeId: String): Call<ApiResponse<Void>>
    // 마커 위 카페 조회
    @GET("cafe/marker")
    fun getCafeMarker(@Query("longitude") longitude: Double, @Query("latitude") latitude: Double): Call<ApiResponse<Void>>
    // 카페 검색
    @GET("cafe/search")
    fun searchCafeMarker( @Query("keyword") keyword: String): Call<ApiResponse<Void>>
}