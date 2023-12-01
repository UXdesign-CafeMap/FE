package com.example.cafemap.ui.cafe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafemap.api.getCafeId
import com.example.cafemap.api.service.ListService
import com.example.cafemap.api.service.ReviewService
import com.example.cafemap.databinding.ActivityReviewListBinding

class ReviewListActivity : AppCompatActivity() {
    lateinit var _binding: ActivityReviewListBinding
    val binding : ActivityReviewListBinding get() = _binding

    private lateinit var userService: ReviewService

    private lateinit var reviewsViewModel : ReviewViewModel

    private var cafeId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityReviewListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cafeId = intent.getIntExtra("cafeId", -1)
        initLayout()

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

    fun initLayout() {
        userService = ReviewService
        userService.getReview(cafeId)
//        Log.d("seohyunReview", cafeId.toString())

        binding.rvRl.layoutManager = LinearLayoutManager(this)

        binding.rvRl.adapter = ReviewListAdapter()

        reviewsViewModel = userService.getReviews()

        reviewsViewModel.itemList.observe(this@ReviewListActivity, Observer {
            (binding.rvRl.adapter as ReviewListAdapter).setData(it)
        })

        binding.ivRlLeftChevron.setOnClickListener {
            finish()
        }
        binding.fabRlAdd.setOnClickListener {
            val i = Intent(applicationContext, PostReviewActivity::class.java)
//            i.putExtra("")
            startActivity(i)
        }
    }
}