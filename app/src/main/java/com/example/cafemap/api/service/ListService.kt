package com.example.cafemap.api.service

import android.util.Log
import com.example.cafemap.api.RetrofitClient
import com.example.cafemap.api.model.dto.BaseResponse
import com.example.cafemap.api.model.dto.CafeDetailResponse
import com.example.cafemap.api.model.dto.CafeListResponse
import com.example.cafemap.api.model.dto.LocationRequest
import com.example.cafemap.api.model.dto.MarkerCafeResponse
import com.example.cafemap.ui.cafe.CafeDetailViewModel
import com.example.cafemap.ui.cafe.MenuListViewModel
import com.example.cafemap.ui.cafe.SearchCafeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ListService {
    private val userRepository = RetrofitClient.searchRepository

    private val searchCafes = SearchCafeViewModel()
    private val detailCafes = CafeDetailViewModel()
    private val detailMenus = MenuListViewModel()

    fun getCafes(){
        userRepository.getCafes().enqueue(object: Callback<BaseResponse<CafeListResponse>>{
            override fun onResponse(
                call: Call<BaseResponse<CafeListResponse>>,
                response: Response<BaseResponse<CafeListResponse>>
            ) {
                // 서버 응답 처리
                if (response.isSuccessful) {
                    response.body()?.result?.cafeList.let {
                        if (it != null) {
                            searchCafes.setSearchCafe(it)
                        }
                    }
                } else {
                    Log.d("seohyun", response.errorBody().toString())
                }
            }
            override fun onFailure(call: Call<BaseResponse<CafeListResponse>>, t: Throwable) {
                // 통신 실패 처리
                Log.d("seohyun", t.message.toString())
            }
        })
    }

    fun getSearchCafeViewModel(): SearchCafeViewModel {
        return searchCafes
    }
    fun getCafeDetail(cafeId: Int){
//        val request = CafeIdRequest(cafeId)
        userRepository.getCafeDetail(cafeId).enqueue(object: Callback<BaseResponse<CafeDetailResponse>>{
            override fun onResponse(
                call: Call<BaseResponse<CafeDetailResponse>>,
                response: Response<BaseResponse<CafeDetailResponse>>
            ) {
                // 서버 응답 처리
                if (response.isSuccessful) {
                    response.body()?.result.let {
                        if(it != null) {
                            detailCafes.setDetail(it)
                            detailMenus.setDetailMenu(it.menus)
                        }
                    }
                } else {
                    Log.d("seohyun", response.body()?.result?.name.toString())
                }
            }
            override fun onFailure(call: Call<BaseResponse<CafeDetailResponse>>, t: Throwable) {
                // 통신 실패 처리
                Log.d("seohyun", t.message.toString())
            }
        })
    }

    fun getCafeDetailViewModel() : CafeDetailViewModel {
        return detailCafes
    }

    fun getDetailMenusViewModel() : MenuListViewModel {
        return detailMenus
    }
    fun getCafeMarker(location: LocationRequest){
        userRepository.getCafeMarker(location).enqueue(object: Callback<BaseResponse<MarkerCafeResponse>>{
            override fun onResponse(
                call: Call<BaseResponse<MarkerCafeResponse>>,
                response: Response<BaseResponse<MarkerCafeResponse>>
            ) {
                // 서버 응답 처리
                if (response.isSuccessful) {
                    response.body()?.result?.let {
                        if (it != null) {
//                            searchCafes.setSearchCafe(it)
                        }
                    }
                } else {
                    Log.d("seohyun", response.errorBody().toString())
                }

            }

            override fun onFailure(call: Call<BaseResponse<MarkerCafeResponse>>, t: Throwable) {
                Log.d("seohyun", t.message.toString())
            }

        })
    }
    fun searchCafe(){}


}