package com.example.cafemap.ui.cafe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cafemap.api.model.domain.Review
import com.example.cafemap.api.model.dto.GetReviewResponse

class ReviewViewModel: ViewModel() {
    private var list = ArrayList<Review>()
    private val _itemList = MutableLiveData<List<Review>>()

    val itemList : LiveData<List<Review>> get() = _itemList

    init {
        list = arrayListOf()
        _itemList.value = list
    }

    fun setReview(reviews : List<Review>) {
        list.clear()
        list.addAll(reviews)
        _itemList.value = list
    }

}