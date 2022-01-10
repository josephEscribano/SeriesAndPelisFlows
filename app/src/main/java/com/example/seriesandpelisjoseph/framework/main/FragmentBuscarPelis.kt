package com.example.seriesandpelisjoseph.framework.main

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.seriesandpelisjoseph.R
import com.example.seriesandpelisjoseph.data.sources.adapter.MovieAdapter
import com.example.seriesandpelisjoseph.databinding.FragmentBuscarPelisBinding
import com.google.android.material.snackbar.Snackbar
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
        val loading = LoadingDialog(this)
        moviesAdapter = MovieAdapter()
        binding.rvMovies.layoutManager = GridLayoutManager(this.context,2)
        binding.rvMovies.adapter = moviesAdapter
        viewModel.movie.observe(this,{movies ->
            moviesAdapter.submitList(movies)
        })

        viewModel.error.observe(this,{
            Snackbar.make(binding.root,it,Snackbar.LENGTH_SHORT).show()
        })

        viewModel.getPopularMovies()
//        viewModel.loading.observe(this,{
//            if (viewModel.loading.value == true)
//
//        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuscarPelisBinding.inflate(inflater,container,false)
        return binding.root
    }


}