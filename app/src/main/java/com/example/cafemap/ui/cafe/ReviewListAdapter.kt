package com.example.cafemap.ui.cafe

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cafemap.api.model.domain.Review
import com.example.cafemap.databinding.ItemCafeReviewBinding
import java.text.SimpleDateFormat

class ReviewListAdapter() : RecyclerView.Adapter<ReviewListAdapter.ViewHolder>() {

    var items = arrayListOf<Review>()

    var itemClickListener: OnItemClickListener? = null
    interface OnItemClickListener {
        fun onItemClicked(reviewId: Int)
    }

    inner class ViewHolder(val binding: ItemCafeReviewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : Review) {
            Log.d("seohyunBinding", item.cafeId.toString())
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            binding.tvCrProfileName.text = item.nickname
            binding.tvCrProfileDetail.text = "리뷰 " + item.reviewCount + " | " + dateFormat.format(item.uploadDate) // 2013-03-01 only date
            println(item.reviewImgList)
            if(item.reviewImgList != null && item.reviewImgList.isNotEmpty()){
                Glide.with(itemView.context)
                    .load(item.reviewImgList?.get(0))
                    .into(binding.ivReviewImg)
            }
            binding.tvCrReviewContent.text = item.content
            if (binding.tvCrReviewContent.lineCount > 3){
                binding.tvCrSeeMore.visibility = View.VISIBLE
                binding.tvCrSeeMore.setOnClickListener {
                    binding.tvCrSeeMore.visibility = View.GONE
                    binding.tvCrReviewContent.ellipsize = null
                    binding.tvCrReviewContent.maxLines = Int.MAX_VALUE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCafeReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(list: List<Review>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}