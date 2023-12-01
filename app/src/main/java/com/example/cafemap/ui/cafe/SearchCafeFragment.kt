package com.example.cafemap.ui.cafe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
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

        userService = ListService
        userService.getCafes()

        binding.rvSc.layoutManager = LinearLayoutManager(requireContext())

        val adapter = SearchCafeAdapter()
        adapter.itemClickListener = object: SearchCafeAdapter.OnItemClickListener {
            override fun onItemClicked(cafeId: Int) {
                val i = Intent(requireContext(), CafeDetailActivity::class.java)
                Log.d("seohyunDetail", cafeId.toString())
                i.putExtra("cafeId", cafeId)
                startActivity(i)
            }
        }

        binding.rvSc.adapter = adapter

        val searchCafeViewModel = userService.getSearchCafeViewModel()

        searchCafeViewModel.itemList.observe(viewLifecycleOwner, Observer {
            (binding.rvSc.adapter as SearchCafeAdapter).setData(it)
        })

        return root
    }

//    private fun showCafeList() {
//        userService.
//    }
}