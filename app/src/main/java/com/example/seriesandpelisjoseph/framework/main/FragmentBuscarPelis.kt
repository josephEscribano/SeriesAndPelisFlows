package com.example.seriesandpelisjoseph.framework.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.seriesandpelisjoseph.data.sources.adapter.MovieAdapter
import com.example.seriesandpelisjoseph.databinding.FragmentBuscarPelisBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentBuscarPelis: Fragment() {

    private var _binding : FragmentBuscarPelisBinding? = null
    private val binding get() = _binding!!
    private lateinit var moviesAdapter: MovieAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesAdapter = MovieAdapter()
        binding.rvMovies.adapter = moviesAdapter
        binding.btBuscarPeli.setOnClickListener {
            viewModel.getMovie(binding.etName.text.toString())
        }
        viewModel.movie.observe(this,{movies ->
            moviesAdapter.submitList(movies)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuscarPelisBinding.inflate(inflater,container,false)
        return binding.root
    }


}