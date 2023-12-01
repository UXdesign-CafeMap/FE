package com.example.cafemap.ui.cafe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cafemap.api.model.domain.Cafe

class SearchCafeViewModel : ViewModel() {
    private var list = ArrayList<Cafe>()
    private var _itemList = MutableLiveData<List<Cafe>>()

    val itemList : LiveData<List<Cafe>> get() = _itemList

    init {
        list = arrayListOf()
        _itemList.value = list
    }

    fun setSearchCafe(cList: List<Cafe>) {
        list.clear()
        list.addAll(cList)
        _itemList.value = list
    }
}