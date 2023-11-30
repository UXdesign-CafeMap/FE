package com.example.cafemap.api.service

import android.util.Log
import com.example.cafemap.api.BaseResponse
import com.example.cafemap.api.Cafe
import com.example.cafemap.api.CafeListResponse
import com.example.cafemap.api.RetrofitClient
import com.example.cafemap.ui.cafe.SearchCafeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ListService {
    private val userRepository = RetrofitClient.searchRepository

    private val searchCafes = SearchCafeViewModel()

    //        RetrofitUtil.getRetrofitUtil()
//            .getChallenge(GetMemberAllChallengesRequest(getUserName(requireContext()), 0, 10))
//            .enqueue(object : Callback<GetMemberAllChallengesResponse> {
//                override fun onResponse(
//                    call: Call<GetMemberAllChallengesResponse>,
//                    response: Response<GetMemberAllChallengesResponse>
//                ) {
//                    // 서버 응답 처리
//                }
//
//                override fun onFailure(call: Call<GetMemberAllChallengesResponse>, t: Throwable) {
//                    // 통신 실패 처리
//                }
//            })
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

                    Log.d("seohyun", response.body()?.result?.cafeList.toString())
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
//        getCafes()
        return searchCafes
    }
    fun getCafeDetail(){}
    fun getCafeMarker(){}
    fun searchCafe(){}


}