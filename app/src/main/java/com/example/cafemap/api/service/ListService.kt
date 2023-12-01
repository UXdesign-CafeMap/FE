package com.example.cafemap.api.service

import android.util.Log
import com.example.cafemap.api.RetrofitClient
import com.example.cafemap.api.model.dto.BaseResponse
import com.example.cafemap.api.model.dto.CafeDetailResponse
import com.example.cafemap.api.model.dto.CafeListResponse
import com.example.cafemap.ui.cafe.CafeDetailViewModel
import com.example.cafemap.ui.cafe.SearchCafeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ListService {
    private val userRepository = RetrofitClient.searchRepository

    private val searchCafes = SearchCafeViewModel()
    private val detailCafes = CafeDetailViewModel()

    fun getCafes(){
        userRepository.getCafes().enqueue(object: Callback<BaseResponse<CafeListResponse>>{
            override fun onResponse(
                call: Call<BaseResponse<CafeListResponse>>,
                response: Response<BaseResponse<CafeListResponse>>
            ) {
                // 서버 응답 처리
                if (response.isSuccessful) {
//                    val list = ArrayList<Cafe>()
                    response.body()?.result?.cafeList.let {
//                        for (i in it){
//                            // 각각의 Cafe 객체에 접근
//                            list.add(i)
//                            Log.d("seohyun", i.toString())
//                        }
                        if (it != null) {
                            searchCafes.setSearchCafe(it)
                        }
                    }
//                    list.addAll((response.body()?.cafeList!!))
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
                        }
                    }
                    Log.d("seohyunBody", response.body()?.result.toString())
                } else {
                    Log.d("seohyunDetail", response.body()?.result?.name.toString())
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
    fun getCafeMarker(){}
    fun searchCafe(){}


}