package com.example.cafemap.ui.cafe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchCafeViewModel : ViewModel() {
    private var list = ArrayList<String>()
    private var _itemList = MutableLiveData<List<String>>()

    val itemList : LiveData<List<String>> get() = _itemList

    init {
        list = arrayListOf()
        _itemList.value = list
    }
}