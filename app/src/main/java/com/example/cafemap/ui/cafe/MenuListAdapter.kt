package com.example.cafemap.ui.cafe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cafemap.databinding.ItemDetailMenuBinding

class MenuListAdapter() : RecyclerView.Adapter<MenuListAdapter.ViewHolder>() {

    var items = arrayListOf<com.example.cafemap.api.model.domain.Menu>()

    var itemClickListener : OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClicked(menu: com.example.cafemap.api.model.domain.Menu)
    }

    inner class ViewHolder(val binding: ItemDetailMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : com.example.cafemap.api.model.domain.Menu) {
            binding.tvIdmMenuName.text = item.menuName
            binding.tvIdmMenuPrice.text = item.menuPrice
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuListAdapter.ViewHolder {
        val binding = ItemDetailMenuBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuListAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(list: List<com.example.cafemap.api.model.domain.Menu>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}