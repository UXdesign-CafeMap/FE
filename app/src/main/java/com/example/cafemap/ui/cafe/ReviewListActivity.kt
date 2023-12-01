package com.example.cafemap.ui.cafe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cafemap.databinding.ActivityReviewListBinding

class ReviewListActivity : AppCompatActivity() {
    lateinit var _binding: ActivityReviewListBinding

    val binding : ActivityReviewListBinding get() = _binding
    private val reviewViewModel = ReviewViewModel()
    private var reviewId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityReviewListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reviewId = intent.getIntExtra("reviewId", -1)

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

        binding.rvRl.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = ReviewListAdapter()
//        adapter.setOnItemClickListener(object : ReviewListAdapter.OnItemClickListener {
//            override fun onItemClicked(reviewId: Int) {
//                val i = Intent(applicationContext, ReviewDetail어쩌고)
//            }
//        })
        binding.rvRl.adapter = adapter
        reviewViewModel.itemList.observe(this, Observer {
            (binding.rvRl.adapter as ReviewListAdapter).setData(it)
        })
        binding.ivRlLeftChevron.setOnClickListener {
            finish()
        }
        binding.fabRlAdd.setOnClickListener {
            val i = Intent(applicationContext, PostReviewActivity::class.java)
            startActivity(i)
        }
    }
}