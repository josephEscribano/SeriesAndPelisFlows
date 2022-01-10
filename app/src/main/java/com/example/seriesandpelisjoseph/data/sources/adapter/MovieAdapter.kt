package com.example.seriesandpelisjoseph.data.sources.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.loadAny
import com.example.seriesandpelisjoseph.R

import com.example.seriesandpelisjoseph.databinding.RecyclerstylepelisBinding
import com.example.seriesandpelisjoseph.domain.Movie

class MovieAdapter():ListAdapter<Movie,MovieAdapter.ItemViewHolder>(DiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerstylepelis, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int)  = with(holder){
        val movie = getItem(position)
        bind(movie)
    }


    class ItemViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        private val binding = RecyclerstylepelisBinding.bind(itemView)

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(movie: Movie) {
            with(binding){
                image.loadAny( movie.imagen?.let { binding.root.context.getString(R.string.pathImage) + movie.imagen } ?: run { binding.root.context.getDrawable(R.drawable.img) } )
                titulo.setText(movie.titulo)
            }
        }
    }
}

class DiffCallBack : DiffUtil.ItemCallback<Movie>(){
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}
