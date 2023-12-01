package com.example.cafemap.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cafemap.R
import com.example.cafemap.api.model.domain.Cafe
import com.example.cafemap.databinding.ItemHomeCafeListBinding
import com.example.cafemap.databinding.ItemSearchCafeListBinding
import com.example.cafemap.ui.cafe.SearchCafeAdapter

class HomeListAdapter() : RecyclerView.Adapter<HomeListAdapter.ViewHolder>() {

    var items = arrayListOf<Cafe>()

    var itemClickListener : OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClicked(cafeId: Int)
    }

    inner class ViewHolder(val binding: ItemHomeCafeListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Cafe){
            binding.root.setOnClickListener {
                itemClickListener?.onItemClicked(item.cafeId)
            }

            val totalSeat = item.totalSeat
            val remainSeat = item.remainSeat

            binding.apply {
                tvHlCafeName.text = item.name
                tvHlCafeDistance.text = item.distance
                tvHlCafeSeat.text = remainSeat.toString() + '/' + totalSeat.toString()
            }

            val dens = (remainSeat.toDouble() / totalSeat.toDouble()) * 100
            val densText = binding.tvHlCafeDenseLabel
            val densColor = binding.cvHlCafeDense
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
    ): HomeListAdapter.ViewHolder {
        val binding = ItemHomeCafeListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(list: List<Cafe>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

}