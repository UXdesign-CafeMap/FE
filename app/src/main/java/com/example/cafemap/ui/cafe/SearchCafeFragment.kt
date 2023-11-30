package com.example.cafemap.ui.cafe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
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

        userService = ListService

        binding.rvSc.layoutManager = LinearLayoutManager(requireContext())

//        binding.rvSc.adapter = SearchCafeAdapter(searchCafes.itemList.value!!)
        binding.rvSc.adapter = SearchCafeAdapter(emptyList())

        val searchCafeViewModel = userService.getSearchCafeViewModel()

        searchCafeViewModel.itemList.observe(viewLifecycleOwner, Observer {
            (binding.rvSc.adapter as SearchCafeAdapter).setData(it)
        })

        userService.getCafes()

        return root
    }

//    private fun showCafeList() {
//        userService.
//    }
}