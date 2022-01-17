package com.example.seriesandpelisjoseph.framework.main

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.seriesandpelisjoseph.R
import com.example.seriesandpelisjoseph.databinding.FragmentBuscarPelisBinding
import com.example.seriesandpelisjoseph.domain.MultiMedia
import com.example.seriesandpelisjoseph.framework.main.adapter.MultimediaAdapter
import com.example.seriesandpelisjoseph.framework.viemodels.MainViewModel
import com.example.seriesandpelisjoseph.utils.Constantes
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentBuscarPelis : Fragment() {

    private var _binding: FragmentBuscarPelisBinding? = null
    private val binding get() = _binding!!
    private lateinit var action: NavDirections
    private lateinit var multimediaAdapter: MultimediaAdapter

    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.favoritos).isVisible = false

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        multimediaAdapter =
            MultimediaAdapter(binding.root.context, object : MultimediaAdapter.MultimediaActions {
                override fun navegar(multiMedia: MultiMedia) {

                    if (multiMedia.tipo.equals(Constantes.MOVIE)) {
                        action =
                            FragmentBuscarPelisDirections.actionFragmentBuscarPelisToFragmentMostrarPelis(
                                multiMedia
                            )
                    } else if (multiMedia.tipo.equals(Constantes.TV)) {
                        action =
                            FragmentBuscarPelisDirections.actionFragmentBuscarPelisToFragmentMostrarSeries(
                                multiMedia.idApi
                            )

                    } else {
                        action =
                            FragmentBuscarPelisDirections.actionFragmentBuscarPelisToFragmentMostrarActores(
                                multiMedia.idApi
                            )
                    }

                    findNavController().navigate(action)
                }

                override fun deleteItem(multiMedia: MultiMedia?) {
                    TODO("Not yet implemented")
                }

            })
        binding.rvMovies.layoutManager = GridLayoutManager(this.context, 2)
        binding.rvMovies.adapter = multimediaAdapter

        viewModel.multiMedia.observe(this, { multimedia ->
            multimediaAdapter.submitList(multimedia)
        })

        viewModel.error.observe(this, {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        })

        viewModel.getPopularMovies()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuscarPelisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val actionSearch = menu.findItem(R.id.buscar).actionView as SearchView

        actionSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.getMultiSearch(it)
                }

                return false
            }

        })
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.pelis -> {
                viewModel.getPopularMovies()
                true
            }

            R.id.series -> {
                viewModel.getPopularSeries()
                true
            }
            else -> false
        }

        return super.onOptionsItemSelected(menuItem)
    }

}