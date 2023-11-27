package com.example.cafemap.ui.cafe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cafemap.databinding.ItemSearchCafeListBinding

class SearchCafeAdapter(var items : List<String>) : RecyclerView.Adapter<SearchCafeAdapter.ViewHolder>() {

    private var onItemClickListener : OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClicked(cafeId: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    inner class ViewHolder(val binding: ItemSearchCafeListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : String) {
            binding.root.setOnClickListener {
//                onItemClickListener!!.onItemClicked(item.cafeId)
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