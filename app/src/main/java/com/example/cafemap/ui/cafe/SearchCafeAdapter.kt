package com.example.cafemap.ui.cafe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cafemap.R
import com.example.cafemap.api.model.domain.Cafe
import com.example.cafemap.databinding.ItemSearchCafeListBinding

class SearchCafeAdapter() : RecyclerView.Adapter<SearchCafeAdapter.ViewHolder>() {

    var items = arrayListOf<Cafe>()

    var itemClickListener : OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClicked(cafeId: Int)
    }

    inner class ViewHolder(val binding: ItemSearchCafeListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : Cafe) {
            binding.root.setOnClickListener {
                itemClickListener?.onItemClicked(item.cafeId)
            }

            val totalSeat = item.totalSeat
            val remainSeat = item.remainSeat

            binding.apply {
                tvScCafeName.text = item.name
                tvScCafeDistance.text = item.distance
                tvScCafeSeat.text = remainSeat.toString() + '/' + totalSeat.toString()
                tvScReview.text = item.review
            }

            val dens = (remainSeat.toDouble() / totalSeat.toDouble()) * 100
            val densText = binding.tvScCafeDenseLabel
            val densColor = binding.cvScCafeDense
            if (dens <= 30) {
                densText.text = "여유"
                densColor.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.DENS_100
                    )
                )
            } else if (dens <= 60) {
                densText.text = "보통"
                densColor.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.DENS_200
                    )
                )
            } else if (dens <= 90) {
                densText.text = "혼잡"
                densColor.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.DENS_300
                    )
                )
            } else {
                densText.text = "만석"
                densColor.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.DENS_400
                    )
                )
            }
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
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun sortByDistance() {
        items = ArrayList(items.sortedBy { it.distance })
        notifyDataSetChanged()
    }
}