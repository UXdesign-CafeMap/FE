package com.example.cafemap.ui.cafe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafemap.R
import com.example.cafemap.databinding.FragmentSearchCafeBinding

class SearchCafeFragment : Fragment() {

    private var _binding: FragmentSearchCafeBinding? = null
    private val binding get() = _binding!!

    private val searchCafes = SearchCafeViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchCafeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rvSc.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSc.adapter = SearchCafeAdapter(searchCafes.itemList.value!!)

        searchCafes.itemList.observe(viewLifecycleOwner, Observer {
            (binding.rvSc.adapter as SearchCafeAdapter).setData(it)
        })

//        RetrofitUtil.getRetrofitUtil()
//            .getChallenge(GetMemberAllChallengesRequest(getUserName(requireContext()), 0, 10))
//            .enqueue(object : Callback<GetMemberAllChallengesResponse> {
//                override fun onResponse(
//                    call: Call<GetMemberAllChallengesResponse>,
//                    response: Response<GetMemberAllChallengesResponse>
//                ) {
//                    // 서버 응답 처리
//                }
//
//                override fun onFailure(call: Call<GetMemberAllChallengesResponse>, t: Throwable) {
//                    // 통신 실패 처리
//                }
//            })

        val searchView = root.findViewById<androidx.appcompat.widget.SearchView>(R.id.sv_home)
        searchView.setIconifiedByDefault(false)
        searchView.isSubmitButtonEnabled = false;

        return root
    }
}