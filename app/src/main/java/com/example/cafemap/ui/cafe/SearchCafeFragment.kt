package com.example.cafemap.ui.cafe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.cafemap.R
import com.example.cafemap.databinding.FragmentSearchCafeBinding

class SearchCafeFragment : Fragment() {

    private lateinit var binding: FragmentSearchCafeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_search_cafe, container, false)

        val searchView = rootView.findViewById<androidx.appcompat.widget.SearchView>(R.id.sv_home)
        searchView.setIconifiedByDefault(false)

        return rootView
    }
}