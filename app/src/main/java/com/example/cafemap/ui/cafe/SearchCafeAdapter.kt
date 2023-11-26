package com.example.cafemap.ui.cafe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cafemap.databinding.ItemSearchCafeListBinding

class SearchCafeAdapter(var items : List<String>) : RecyclerView.Adapter<SearchCafeAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemSearchCafeListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : String) {
//            binding.root.setOnClickListener {
//                itemClickListener!!.onItemClicked(item.challengeId)
//            }

//            binding.tvMcTitle.text = item.contents.title
//            binding.tvItemMyChallengePercent.text = (item.progressRate * 100).toInt().toString() + "%"
//            binding.cvMcProgress.layoutParams.width = (360 * item.progressRate).toInt()

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemSearchCafeListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(list:List<String>) {
        items = list
        notifyDataSetChanged()
    }
}