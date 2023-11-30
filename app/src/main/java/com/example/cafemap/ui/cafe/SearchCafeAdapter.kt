package com.example.cafemap.ui.cafe

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.cafemap.R
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

            val totalSeat = item.totalSeat
            val remainSeat = item.remainSeat
            binding.tvScCafeName.text = item.name
            binding.tvScCafeDistance.text = item.distance
            binding.tvScCafeSeat.text = remainSeat.toString() + '/' + totalSeat.toString()
            binding.tvScReview.text = item.review

            val dens = (remainSeat.toDouble() /totalSeat.toDouble()) * 100
            val densText = binding.tvScCafeDenseLabel
            val densColor = binding.cvScCafeDense
            if (dens <= 30) {
                densText.text = "여유"
                densColor.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.DENS_100))
            } else if (dens <= 60) {
                densText.text = "보통"
                densColor.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.DENS_200))
            } else if (dens <= 90) {
                densText.text = "혼잡"
                densColor.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.DENS_300))
            } else {
                densText.text = "만석"
                densColor.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.DENS_400))
            }

//            binding.cvScCafeContainer.setOnClickListener {
//            Log.d("seohyunDetail", this)
//            val i = Intent(requireContext(), CafeDetailActivity::class.java)
//            startActivity(i)
//        }
        }
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