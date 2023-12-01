package com.example.cafemap.ui.cafe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cafemap.databinding.ActivityPostReviewBinding
import com.example.cafemap.databinding.ActivitySignUpBinding

class PostReviewActivity : AppCompatActivity() {

    lateinit var _binding: ActivityPostReviewBinding

    val binding : ActivityPostReviewBinding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPostReviewBinding.inflate(layoutInflater)
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
        binding.ivPrLeftChevron.setOnClickListener {
            finish()
        }

    }
}