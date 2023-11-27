package com.example.cafemap.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.example.cafemap.MainActivity
import com.example.cafemap.R
import com.example.cafemap.databinding.ActivityLogInBinding
import com.example.cafemap.ui.home.HomeFragment

class LogInActivity : AppCompatActivity() {
    lateinit var _binding: ActivityLogInBinding

    val binding : ActivityLogInBinding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initlayout()

//        RetrofitUtil.getRetrofitUtil().getProofPosts(challengeId).enqueue(object : Callback<GetProofPostsResponse> {
//            override fun onResponse(
//                call: Call<GetProofPostsResponse>,
//                response: Response<GetProofPostsResponse>
//            ) {
//                if(response.isSuccessful) {
//                    recordViewModel.addRecords(response.body()!!.proofPosts)
//                } else {
//                    Log.d("seohyun", response.errorBody().toString())
//                }
//            }
//
//            override fun onFailure(call: Call<GetProofPostsResponse>, t: Throwable) {
//                Log.d("seohyun", t.message.toString())
//            }
    }

    fun initlayout() {

        // 로그인 버튼 클릭 시 홈화면으로 이동
//        binding.btLiLoginButton.setOnClickListener {
//            val i = Intent(applicationContext, MainActivity::class.java)
//            startActivity(i)
//        }
    }
}