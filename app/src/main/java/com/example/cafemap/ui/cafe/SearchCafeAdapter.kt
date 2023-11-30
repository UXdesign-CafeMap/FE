package com.example.cafemap.ui.cafe

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.cafemap.api.Cafe
import com.example.cafemap.api.CafeListResponse
import com.example.cafemap.databinding.ItemSearchCafeListBinding

class SearchCafeAdapter(var items : List<Cafe>) : RecyclerView.Adapter<SearchCafeAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClicked(cafeId: Int)
    }

    private var onItemClickListener : OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    inner class ViewHolder(val binding: ItemSearchCafeListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : Cafe) {
//            binding.root.setOnClickListener {
//                onItemClickListener!!.onItemClicked(item.cafeId)

            binding.tvScCafeName.text = item.name
            Log.d("seohyun", binding.tvScCafeName.text.toString())
        }


//            binding.tvMcTitle.text = item.contents.title
//            binding.tvItemMyChallengePercent.text = (item.progressRate * 100).toInt().toString() + "%"
//            binding.cvMcProgress.layoutParams.width = (360 * item.progressRate).toInt()

//            val layoutParams = binding.cvMcProgress.layoutParams
//            layoutParams.width = (binding.cvMcBackProgress.layoutParams.width * item.progressRate).toInt()
//            binding.cvMcProgress.layoutParams = layoutParams

//            // 레이아웃을 다시 요청
//            binding.cvMcProgress.requestLayout()

        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemSearchCafeListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setData(list: List<Cafe>) {
        items = list
        notifyDataSetChanged()
    }
}