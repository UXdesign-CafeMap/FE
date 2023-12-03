package com.example.cafemap.ui.cafe

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.cafemap.api.service.ReviewService
import com.example.cafemap.databinding.ActivityWriteReviewBinding
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID


class WriteReviewActivity : AppCompatActivity() {
    private val PICK_IMAGE_REQUEST = 1

    // variables
    private lateinit var reviewService: ReviewService
    private var cafeId = -1
    private val uploadedImageUrls = ArrayList<String>() // 업로드된 이미지 URL을 저장할 리스트

    // View Binding
    lateinit var _binding: ActivityWriteReviewBinding
    private val binding: ActivityWriteReviewBinding get() = _binding
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cafeId = intent.getIntExtra("cafeId", -1)

        _binding = ActivityWriteReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reviewService = ReviewService

        submitButton = findViewById(R.id.submit_button)

        binding.addPhoto.setOnClickListener { openGallery() }
        binding.backButton.setOnClickListener { finish() }
        binding.submitButton.setOnClickListener { onClickSubmit() }

        binding.reviewText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.tvPrTextCount.setText(s.length.toString()+"/1000")
            }
            override fun afterTextChanged(s: Editable) {}
        })
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
            layoutParams = LinearLayout.LayoutParams(100.dpToPx(), 100.dpToPx()).also {
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

        binding.selectedImagesContainer.addView(imageLayout)

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
                    binding.selectedImagesContainer.removeView(imageLayout) // 업로드 실패시 레이아웃 제거
                }
            }
    }

    private fun Int.dpToPx(): Int = (this * resources.displayMetrics.density).toInt()

    private fun onClickSubmit() {
        // validation
        if (binding.reviewText.text.toString().isEmpty()) {
            Toast.makeText(this, "리뷰를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (binding.reviewText.text.toString().length > 1000) {
            Toast.makeText(this, "리뷰는 1000자 이내로 작성해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        reviewService.createReview(
            review = Review(
                memberId = 1,
                cafeId = cafeId,
                imgUrlList = uploadedImageUrls,
                content = binding.reviewText.text.toString(),
            ),
            onSuccess = { response ->
                Toast.makeText(this, "리뷰가 등록되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
                val intent = Intent(this, ReviewListActivity::class.java)
                // console cafeId
                println(cafeId)
                intent.putExtra("cafeId", cafeId)
            },
            onFailure = { throwable ->
                Toast.makeText(this, "리뷰 등록에 실패했습니다.", Toast.LENGTH_SHORT).show()
                println(throwable)
            }
        )
    }
}