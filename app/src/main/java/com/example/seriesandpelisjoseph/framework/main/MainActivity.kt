package com.example.seriesandpelisjoseph.framework.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.seriesandpelisjoseph.R
import com.example.seriesandpelisjoseph.data.sources.adapter.MovieAdapter
import com.example.seriesandpelisjoseph.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var moviesAdapter: MovieAdapter
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moviesAdapter = MovieAdapter()
        binding.rvMovies.adapter = moviesAdapter
        binding.button.setOnClickListener {
            viewModel.getMovie(601)
        }
        viewModel.movie.observe(this,{movies ->
            moviesAdapter.submitList(movies.map { it.title })
        })




    }
}