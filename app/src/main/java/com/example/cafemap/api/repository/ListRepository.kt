package com.example.cafemap.api.repository

import com.example.cafemap.api.model.dto.BaseResponse
import com.example.cafemap.api.model.dto.CafeDetailResponse
import com.example.cafemap.api.model.dto.CafeIdRequest
import com.example.cafemap.api.model.dto.CafeListResponse
import com.example.cafemap.api.model.dto.LocationRequest
import com.example.cafemap.api.model.dto.MarkerCafeResponse
import com.example.cafemap.api.model.dto.SearchRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface ListRepository {

    // 카페 전체 조회
    @GET("/cafe/")
    fun getCafes(
    ): Call<BaseResponse<CafeListResponse>>

    // 카페 개별 조회
    @GET("/cafe/detail")
    fun getCafeDetail(
        @Query("cafeId")
        cafeId: Int
    ): Call<BaseResponse<CafeDetailResponse>>

    // 해당 마커 카페 조회
    @GET("/cafe/marker")
    fun getCafeMarker(
        @Query("longitude") longitude: Double,
        @Query("latitude") latitude: Double
    ): Call<BaseResponse<MarkerCafeResponse>>

    // 카페 이름 검색
    @GET("/cafe/search")
    fun searchCafe(
        @Query("searchRequest")
        searchRequest: SearchRequest
    ): Call<BaseResponse<CafeListResponse>>

}