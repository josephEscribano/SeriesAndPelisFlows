package com.example.seriesandpelisjoseph.framework.main.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.seriesandpelisjoseph.R
import com.example.seriesandpelisjoseph.databinding.RecyclercapsBinding
import com.example.seriesandpelisjoseph.domain.Capitulo

class CapsAdapterRoom(val actions: CapsActions) :
    ListAdapter<Capitulo, CapsAdapterRoom.ItemviewHolder>(DiffCallBackCapsRoom()) {

    interface CapsActions {

        fun onStarSelectMode()
        fun itemHasClicked(capitulo: Capitulo)
        fun isItemSelected(capitulo: Capitulo): Boolean
        fun updateCapitulo(capitulo: Capitulo)
    }

    fun resetSelectMode() {
        selectMode = false
        notifyDataSetChanged()
    }

    private var selectMode: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemviewHolder {
        return ItemviewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclercaps, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemviewHolder, position: Int) = with(holder) {
        val cap = getItem(position)
        bind(cap)
    }

    inner class ItemviewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val binding = RecyclercapsBinding.bind(itemview)
        fun bind(capitulo: Capitulo) {

            itemView.setOnLongClickListener {
                if (!selectMode) {
                    selectMode = true
                    actions.onStarSelectMode()
                    capitulo.isSelected = true
                    binding.checkBox.isChecked = true
                    actions.itemHasClicked(capitulo)
                    notifyDataSetChanged()
                }
                true
            }

            binding.checkBox.setOnClickListener {
                if (selectMode) {
                    if (binding.checkBox.isChecked) {
                        capitulo.isSelected = true
                        itemView.setBackgroundColor(Color.TRANSPARENT)
                        binding.checkBox.isChecked = true

                    } else {
                        capitulo.isSelected = false
                        itemView.setBackgroundColor(Color.WHITE)
                        binding.checkBox.isChecked = false

                    }

                    actions.itemHasClicked(capitulo)
                }
            }
            binding.tvCap.text = capitulo.nombre
            binding.buttonNoVisto.setOnClickListener {
                capitulo.visto = true
                actions.updateCapitulo(capitulo)
                binding.buttonNoVisto.visibility = View.GONE
                binding.buttonVisto.visibility = View.VISIBLE
            }

            binding.buttonVisto.setOnClickListener {
                capitulo.visto = false
                actions.updateCapitulo(capitulo)
                binding.buttonNoVisto.visibility = View.VISIBLE
                binding.buttonVisto.visibility = View.GONE

            }

            if (capitulo.visto) {
                binding.buttonNoVisto.visibility = View.GONE
                binding.buttonVisto.visibility = View.VISIBLE
                binding.checkBox.visibility = View.GONE
            } else {
                binding.buttonNoVisto.visibility = View.VISIBLE
                binding.buttonVisto.visibility = View.GONE

            }
            if (selectMode) {
                binding.checkBox.visibility = View.VISIBLE
            } else {
                binding.checkBox.visibility = View.GONE
            }

            if (actions.isItemSelected(capitulo)) {
                itemView.setBackgroundColor(Color.TRANSPARENT)
                binding.checkBox.isChecked = true
            } else {
                itemView.setBackgroundColor(Color.WHITE)
                binding.checkBox.isChecked = false
            }
        }
    }


}

class DiffCallBackCapsRoom : DiffUtil.ItemCallback<Capitulo>() {

    override fun areItemsTheSame(oldItem: Capitulo, newItem: Capitulo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Capitulo, newItem: Capitulo): Boolean {
        return oldItem == newItem
    }
}