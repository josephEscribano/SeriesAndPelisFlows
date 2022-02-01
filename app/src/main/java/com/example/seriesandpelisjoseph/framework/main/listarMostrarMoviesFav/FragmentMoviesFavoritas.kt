package com.example.seriesandpelisjoseph.framework.main.listarMostrarMoviesFav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.seriesandpelisjoseph.R
import com.example.seriesandpelisjoseph.data.model.toMovie
import com.example.seriesandpelisjoseph.databinding.FragmentMoviesFavoritasBinding
import com.example.seriesandpelisjoseph.domain.MultiMedia
import com.example.seriesandpelisjoseph.framework.main.adapter.MultimediaAdapter
import com.example.seriesandpelisjoseph.framework.main.buscarElementos.BuscarPelisContract
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentMoviesFavoritas : Fragment() {
    private var _binding: FragmentMoviesFavoritasBinding? = null
    private val binding get() = _binding!!
    private lateinit var multimediaAdapter: MultimediaAdapter
    private lateinit var action: NavDirections
    private val viewmodel: MovieFavViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesFavoritasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.series).isVisible = false
        menu.findItem(R.id.pelis).isVisible = false
        menu.findItem(R.id.buscar).isVisible = false
        menu.findItem(R.id.favoritos).isVisible = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        multimediaAdapter =
            MultimediaAdapter(object : MultimediaAdapter.MultimediaActions {
                override fun navegar(multiMedia: MultiMedia) {
                    action =
                        FragmentMoviesFavoritasDirections.actionFragmentMoviesFavoritasToFragmentMostrarPelis(
                            multiMedia
                        )
                    findNavController().navigate(action)
                }

                override fun deleteItem(multiMedia: MultiMedia?) {
                    viewmodel.handleEvent(ListarMostrarMoviesContract.Event.deleteMovie(multiMedia?.toMovie()))
                }

            })

        binding.rvMoviesFav.adapter = multimediaAdapter
        val touchHelper = ItemTouchHelper(multimediaAdapter.gesto)
        touchHelper.attachToRecyclerView(binding.rvMoviesFav)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewmodel.uiState.collect { value ->
                    multimediaAdapter.submitList(value.multimedia)

                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewmodel.error.collect {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewmodel.handleEvent(ListarMostrarMoviesContract.Event.getMovies)

    }

}