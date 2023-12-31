package com.example.cafemap.ui.cafe

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
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
    private var cafeName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityReviewListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cafeId = intent.getIntExtra("cafeId", -1)
        cafeName = intent.getStringExtra("cafeName").toString()

        binding.tvRlCafeName.text = "<$cafeName> 리뷰"
        initLayout()
    }



    private val writeReviewActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        println(result.resultCode)
        if (result.resultCode == Activity.RESULT_OK) {
            userService.getReview(cafeId)
        }
    }

    fun initLayout() {
        userService = ReviewService
        userService.getReview(cafeId)

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
            i.putExtra("cafeName", cafeName)
            writeReviewActivityResultLauncher.launch(i)
        }
    }
}