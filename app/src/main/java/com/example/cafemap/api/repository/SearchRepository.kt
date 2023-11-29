package com.example.cafemap.api.repository

import com.example.cafemap.api.CafeDetailResponse
import com.example.cafemap.api.CafeIdRequest
import com.example.cafemap.api.CafeListResponse
import com.example.cafemap.api.LocationRequest
import com.example.cafemap.api.MarkerCafeResponse
import com.example.cafemap.api.SearchRequest
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchRepository {

    // 카페 전체 조회
    @GET("/cafe/")
    fun getCafes(
    ): Call<CafeListResponse>

    @GET("/cafe/detail")
    fun getCafeDetail(
        @Query("cafeIdRequest")
        cafeIdRequest: CafeIdRequest
    ): Call<CafeDetailResponse>

    @GET("/cafe/marker")
    fun getCafeMarker(
        @Query("locationRequest")
        locationRequest: LocationRequest
    ): Call<MarkerCafeResponse>

    @GET("/cafe/search")
    fun searchCafe(
        @Query("searchRequest")
        searchRequest: SearchRequest
    ): Call<CafeListResponse>

}