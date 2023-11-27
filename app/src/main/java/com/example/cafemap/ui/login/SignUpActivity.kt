package com.example.cafemap.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cafemap.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    lateinit var _binding: ActivitySignUpBinding

    val binding : ActivitySignUpBinding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignUpBinding.inflate(layoutInflater)
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

        // 회원가입 버튼 클릭 시 홈화면으로 이동 + alert("회원가입이 완료됐습니다")
//        binding.btLiLoginButton.setOnClickListener {
//            val i = Intent(applicationContext, MainActivity::class.java)
//            startActivity(i)
//        }
    }
}