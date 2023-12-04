package com.example.cafemap.ui.cafe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafemap.R
import com.example.cafemap.api.getCafeId
import com.example.cafemap.api.service.AuthService
import com.example.cafemap.api.service.ListService
import com.example.cafemap.databinding.FragmentSearchCafeBinding

class SearchCafeFragment : Fragment() {

    private var _binding: FragmentSearchCafeBinding? = null
    private val binding get() = _binding!!

    private lateinit var userService: ListService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchCafeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        init()
        return root
    }

    fun init() {
        userService = ListService
        userService.getCafes()

        binding.rvSc.layoutManager = LinearLayoutManager(requireContext())

        val adapter = SearchCafeAdapter()
        adapter.itemClickListener = object: SearchCafeAdapter.OnItemClickListener {
            override fun onItemClicked(cafeId: Int) {
                val i = Intent(requireContext(), CafeDetailActivity::class.java)
                i.putExtra("cafeId", cafeId)
                startActivity(i)
            }
        }

        binding.rvSc.adapter = adapter

        val searchCafeViewModel = userService.getSearchCafeViewModel()

        searchCafeViewModel.itemList.observe(viewLifecycleOwner, Observer {
            (binding.rvSc.adapter as SearchCafeAdapter).setData(it)
        })

        val spinnerItems = resources.getStringArray(R.array.spinner_array)
        val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.item_spinner, spinnerItems)

        binding.spSc.adapter = spinnerAdapter
        binding.spSc.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                view?.let {
                    when (position) {
                        0 -> { //거리순
                            (binding.rvSc.adapter as SearchCafeAdapter).sortByDistance()
                        }
                        1 -> { //리뷰순
                            (binding.rvSc.adapter as SearchCafeAdapter).sortByReview()
                        }
                    }
                }
            }
            // spinner 선택하지 않은 경우
            override fun onNothingSelected(parent: AdapterView<*>) {
                searchCafeViewModel.itemList.observe(viewLifecycleOwner, Observer {
                    (binding.rvSc.adapter as SearchCafeAdapter).setData(it)
                })
            }
        }
        binding.spSc.setSelection(0)

        binding.svScSearchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // 사용자가 검색 버튼을 눌렀을 때 호출됨
                // query 매개변수에 검색어가 전달됨
                // 원하는 검색 로직을 구현
                if (query != null) {
                    userService.searchCafe(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // 검색어가 변경될 때마다 호출됨
                // newText 매개변수에 변경된 검색어가 전달됨
                // 원하는 동적 검색 로직을 구현
                if (newText != null) {
                    userService.searchCafe(newText)
                }
                return true
            }
        })
    }

//    private fun performSearch(query: String?) {
//        adapter.filter(query)
//    }
}