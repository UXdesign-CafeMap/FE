package com.example.cafemap.ui.cafe

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.example.cafemap.databinding.ActivityPostReviewBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.IOException
import java.util.UUID


class PostReviewActivity : AppCompatActivity() {
    private val PICK_IMAGE_REQUEST = 1
    lateinit var _binding: ActivityPostReviewBinding

    val binding : ActivityPostReviewBinding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPostReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initlayout()

    }

    fun initlayout() {
        binding.addPhoto.setOnClickListener { openGallery() }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            val selectedImage = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
                binding.addPhoto.setImageBitmap(bitmap) // ImageView에 이미지 표시
                uploadImageToFirebase(selectedImage) // Firebase로 이미지 업로드
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadImageToFirebase(imageUri: Uri?) {
        val fileRef =
            FirebaseStorage.getInstance().getReference("uploads/" + UUID.randomUUID().toString())
        fileRef.putFile(imageUri!!)
            .addOnSuccessListener {
                // 업로드 성공 시 동작
            }
            .addOnFailureListener {
                // 업로드 실패 시 동작
            }
    }

}