package com.example.cafemap.ui.cafe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReviewViewModel: ViewModel() {
    private var list = ArrayList<String>()
    private val _itemList = MutableLiveData<List<String>>()

    val itemList : LiveData<List<String>> get() = _itemList

    init {
        list = arrayListOf()
        _itemList.value = list
    }

    fun setReview(reviews : List<String>) {
        list.clear()
        list.addAll(reviews)
        _itemList.value = list
    }

}