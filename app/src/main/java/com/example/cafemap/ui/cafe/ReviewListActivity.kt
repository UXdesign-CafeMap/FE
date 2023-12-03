package com.example.cafemap.ui.cafe

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
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
            val i = Intent(applicationContext, WriteReviewActivity::class.java)
            i.putExtra("cafeId", cafeId)
            startActivity(i)
        }
    }
}