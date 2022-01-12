package com.example.seriesandpelisjoseph.data.sources.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.loadAny
import com.example.seriesandpelisjoseph.R

import com.example.seriesandpelisjoseph.databinding.RecyclerstylepelisBinding
import com.example.seriesandpelisjoseph.domain.MultiMedia

class MovieAdapter(
    val actions: MultimediaActions
    ):ListAdapter<MultiMedia,MovieAdapter.ItemViewHolder>(DiffCallBack()) {

    interface MultimediaActions{
        fun navegar(multiMedia: MultiMedia)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerstylepelis, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int)  = with(holder){
        val movie = getItem(position)
        bind(movie)
    }


    inner class ItemViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        private val binding = RecyclerstylepelisBinding.bind(itemView)
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(multiMedia: MultiMedia) {

            itemView.setOnClickListener {
                actions.navegar(multiMedia)
            }
            with(binding){
                image.loadAny( multiMedia.imagen?.let { binding.root.context.getString(R.string.pathImage) + it } ?: run { binding.root.context.getDrawable(R.drawable.img) } )
                titulo.setText(multiMedia.titulo)
            }
        }
    }
}

class DiffCallBack : DiffUtil.ItemCallback<MultiMedia>(){
    override fun areItemsTheSame(oldItem: MultiMedia, newItem: MultiMedia): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MultiMedia, newItem: MultiMedia): Boolean {
        return oldItem == newItem
    }

}
