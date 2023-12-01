package com.example.cafemap.ui.cafe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cafemap.api.model.domain.Cafe
import com.example.cafemap.api.model.domain.Menu

class HomeListViewModel : ViewModel() {
    private var list = ArrayList<Menu>()
    private var _itemList = MutableLiveData<List<Menu>>()

    val itemList : LiveData<List<Menu>> get() = _itemList

    init {
        list = arrayListOf()
        _itemList.value = list
    }

    fun setDetailMenu(cList: List<Menu>) {
        list.clear()
        list.addAll(cList)
        _itemList.value = list
    }
}