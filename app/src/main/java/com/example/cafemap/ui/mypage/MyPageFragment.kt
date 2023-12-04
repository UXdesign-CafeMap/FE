package com.example.cafemap.ui.mypage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cafemap.databinding.FragmentHomeBinding
import com.example.cafemap.databinding.FragmentMyPageBinding

class MyPageFragment : Fragment() {

    private var _binding: FragmentMyPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sp = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        val nickname = sp.getString("nickname", "No Nickname") // 찾지 못했을 경우 기본값
        val email = sp.getString("email", "No Email") // 찾지 못했을 경우 기본값

        binding.tvMpUserName.text = nickname
        binding.tvMpUserEmail.text = email

        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}