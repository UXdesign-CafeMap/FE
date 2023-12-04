package com.example.cafemap.ui.cafe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
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
            val totalSeat = cafeDetailResponse.totalSeat
            val remainSeat = cafeDetailResponse.remainSeat

            val totalMultiTap = cafeDetailResponse.totalMultitap
            val remainMultiTap = cafeDetailResponse.remainMultitap

//            Log.d("seohyunHours", cafeDetailResponse.onpeningHours)

            binding.apply{
                tvCdLocation.text = cafeDetailResponse.address
                tvCdCafeName.text = cafeDetailResponse.name
                tvCdOpenHours.text = cafeDetailResponse.onpeningHours
                tvCdSeats.text = remainSeat.toString() + '/' + totalSeat.toString()
                tvCdSeatsMultiTab.text = remainMultiTap.toString() + '/' + totalMultiTap.toString()
                Glide.with(this@CafeDetailActivity)
                    .load(cafeDetailResponse.cafeImage)
                    .into(ivCdImg)


                rvCdMenus.layoutManager = GridLayoutManager(this@CafeDetailActivity, 2)
                rvCdMenus.adapter = MenuListAdapter()

                val menusViewModel = userService.getDetailMenusViewModel()

                menusViewModel.itemList.observe(this@CafeDetailActivity, Observer {
                    (rvCdMenus.adapter as MenuListAdapter).setData(it)
                })
            }

        })

        binding.ivRlLeftChevron.bringToFront()
        binding.ivRlLeftChevron.setOnClickListener {
            finish()
        }

        binding.cvCdReviewContainer.setOnClickListener {
            var i = Intent(this, ReviewListActivity::class.java)
            i.putExtra("cafeId", cafeId)
            startActivity(i)
        }
    }
}