package com.example.cafemap.ui.cafe

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cafemap.R
import com.example.cafemap.api.model.domain.Review
import com.example.cafemap.databinding.ActivityPostReviewBinding
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID
import com.example.cafemap.api.service.ReviewService


class PostReviewActivity : AppCompatActivity() {
    private val PICK_IMAGE_REQUEST = 1
    private val uploadedImageUrls = ArrayList<String>() // 업로드된 이미지 URL을 저장할 리스트
    private lateinit var submitButton: Button
    lateinit var _binding: ActivityPostReviewBinding
    private lateinit var reviewService: ReviewService
    private val binding: ActivityPostReviewBinding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPostReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reviewService = ReviewService

        initlayout()
    }

    fun initlayout() {
        binding.ivPrAddPhoto.setOnClickListener { openGallery() }
        binding.ivPrLeftChevron.setOnClickListener { finish() }
        binding.btnPrSubmit.setOnClickListener { onClickSubmit() }

    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data?.clipData != null) {
                val count = data.clipData!!.itemCount
                for (i in 0 until count) {
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    addImageToLayoutAndUpload(imageUri)
                }
            } else if (data?.data != null) {
                val imageUri = data.data!!
                addImageToLayoutAndUpload(imageUri)
            }
        }
    }

    private fun addImageToLayoutAndUpload(imageUri: Uri) {
        val imageLayout = FrameLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(120.dpToPx(), 120.dpToPx()).also {
                it.setMargins(4.dpToPx(), 0, 4.dpToPx(), 0)
            }
        }

        val imageView = ImageView(this).apply {
            layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
            setImageURI(imageUri)
        }

        val spinner = ProgressBar(this, null, android.R.attr.progressBarStyleLarge).apply {
            layoutParams = FrameLayout.LayoutParams(60.dpToPx(), 60.dpToPx(), Gravity.CENTER)
            visibility = View.VISIBLE // 업로드 중에만 보임
        }

        imageLayout.addView(imageView)
        imageLayout.addView(spinner)

//        binding.selectedImagesContainer.addView(imageLayout)

        uploadImageToFirebase(imageUri, imageLayout, spinner)
    }

    private fun uploadImageToFirebase(imageUri: Uri, imageLayout: FrameLayout, spinner: ProgressBar) {
        val fileRef = FirebaseStorage.getInstance().getReference("uploads/" + UUID.randomUUID().toString())
        fileRef.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    // show url on console
                    println(imageUrl)
                    uploadedImageUrls.add(imageUrl)
                    runOnUiThread {
                        spinner.visibility = View.GONE // 업로드 성공시 스피너 숨김
                    }
                }
            }
            .addOnFailureListener {
                runOnUiThread {
//                    binding.selectedImagesContainer.removeView(imageLayout) // 업로드 실패시 레이아웃 제거
                }
            }
    }

    private fun Int.dpToPx(): Int = (this * resources.displayMetrics.density).toInt()

    private fun onClickSubmit() {
        reviewService.createReview(
            postId = "1",
            review = Review(
                memberId = 1,
                cafeId = 1,
                imgUrlList = uploadedImageUrls,
                content = "테스트 제출",
            ),
            onSuccess = { response ->
                Toast.makeText(this, "리뷰가 등록되었습니다.", Toast.LENGTH_SHORT).show()
                println(response)

            },
            onFailure = { throwable ->
                Toast.makeText(this, "리뷰 등록에 실패했습니다.", Toast.LENGTH_SHORT).show()
                println(throwable)
            }
        )
    }
}
