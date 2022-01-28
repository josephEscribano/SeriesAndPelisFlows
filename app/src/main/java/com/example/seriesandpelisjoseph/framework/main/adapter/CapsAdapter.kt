package com.example.seriesandpelisjoseph.framework.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.seriesandpelisjoseph.R
import com.example.seriesandpelisjoseph.databinding.RecyclercapsapiBinding
import com.example.seriesandpelisjoseph.domain.Capitulo

class CapsAdapter :
    ListAdapter<Capitulo, CapsAdapter.ItemviewHolder>(DiffCallBackCaps()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemviewHolder {
        return ItemviewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclercapsapi, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemviewHolder, position: Int) = with(holder) {
        val cap = getItem(position)
        bind(cap)
    }

    inner class ItemviewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val binding = RecyclercapsapiBinding.bind(itemview)
        fun bind(capitulo: Capitulo) {
            binding.tvCap.text = capitulo.nombre
        }
    }


}

class DiffCallBackCaps : DiffUtil.ItemCallback<Capitulo>() {

    override fun areItemsTheSame(oldItem: Capitulo, newItem: Capitulo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Capitulo, newItem: Capitulo): Boolean {
        return oldItem == newItem
    }
}


