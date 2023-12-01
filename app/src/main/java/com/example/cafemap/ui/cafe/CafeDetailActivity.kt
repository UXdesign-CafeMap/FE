package com.example.cafemap.ui.cafe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.cafemap.api.getCafeId
import com.example.cafemap.api.service.ListService
import com.example.cafemap.databinding.ActivityCafeDetailBinding

class CafeDetailActivity : AppCompatActivity() {

    lateinit var _binding: ActivityCafeDetailBinding
    val binding : ActivityCafeDetailBinding get() = _binding

    private lateinit var userService: ListService
    private var cafeId = -1

    private lateinit var cafeViewModel: CafeDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCafeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cafeId = intent.getIntExtra("cafeId", -1)

        init()

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

    fun init() {
        userService = ListService
        userService.getCafeDetail(cafeId)

        cafeViewModel = userService.getCafeDetailViewModel()
//        Log.d("seohyunId", cafeId.toString())

        cafeViewModel.cafeDetail.observe(this, Observer { cafeDetailResponse ->
            // CafeDetailResponse가 변경될 때마다 호출되는 코드
            binding.tvCdCafeName.text = cafeDetailResponse.name
//            Log.d("seohyunName", cafeDetailResponse.name)
//            binding.tvCdLocation.text = cafeDetailResponse.address
        })

        binding.ivRlLeftChevron.bringToFront()
        binding.ivRlLeftChevron.setOnClickListener {
            finish()
        }

        binding.cvCdReviewContainer.setOnClickListener {
            var i = Intent(this, ReviewListActivity::class.java)
            startActivity(i)
        }

    }
}