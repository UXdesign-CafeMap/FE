package com.example.cafemap.ui.cafe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cafemap.api.CafeDetailResponse
import com.example.cafemap.api.Menu
import com.google.gson.annotations.SerializedName

class CafeDetailViewModel : ViewModel() {

    private var cd = CafeDetailResponse (
    -1, "", "", -1, -1,
        -1, -1, "", "",
        "","","",0.0,0.0,
        listOf(Menu("","")
    )
    )
    private val _cafeDetail = MutableLiveData<CafeDetailResponse>()

    val cafeDetail: LiveData<CafeDetailResponse> get() = _cafeDetail

    init {
        _cafeDetail.value = cd
    }

    fun setDetail(item: CafeDetailResponse) {
        cd = item
        _cafeDetail.value = cd
    }
}
