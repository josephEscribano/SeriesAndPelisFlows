package com.example.seriesandpelisjoseph.data.sources.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.seriesandpelisjoseph.R
import com.example.seriesandpelisjoseph.databinding.RecyclerstyleBinding

class MovieAdapter():ListAdapter<String,MovieAdapter.ItemViewHolder>(DiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerstyle, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int)  = with(holder){
        val name = getItem(position)
        bind(name)
    }


    class ItemViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        private val binding = RecyclerstyleBinding.bind(itemView)
        fun bind(name: String) {
            binding.name.text = name
        }
    }
}

class DiffCallBack : DiffUtil.ItemCallback<String>(){
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem.contentEquals(newItem)
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}
