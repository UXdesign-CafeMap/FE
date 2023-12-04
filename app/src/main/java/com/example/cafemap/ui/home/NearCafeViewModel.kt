package com.example.cafemap.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cafemap.api.model.dto.MarkerCafeResponse

class NearCafeViewModel : ViewModel() {

    private var cd = MarkerCafeResponse (
        0, "", "", 0,0,0,0,
        "", "", "", "",""
    )
    private val _markerCafe = MutableLiveData<MarkerCafeResponse>()
    val markerCafe : LiveData<MarkerCafeResponse> get() = _markerCafe

    init {
        _markerCafe.value = cd
    }

    fun setMarker(item: MarkerCafeResponse){
        cd = item
        _markerCafe.value = cd
    }
}