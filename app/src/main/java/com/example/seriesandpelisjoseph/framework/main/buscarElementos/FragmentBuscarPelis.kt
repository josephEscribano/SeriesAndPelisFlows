package com.example.seriesandpelisjoseph.framework.main.buscarElementos

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.seriesandpelisjoseph.R
import com.example.seriesandpelisjoseph.databinding.FragmentBuscarPelisBinding
import com.example.seriesandpelisjoseph.domain.MultiMedia
import com.example.seriesandpelisjoseph.framework.main.adapter.MultimediaAdapter
import com.example.seriesandpelisjoseph.utils.Constantes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentBuscarPelis : Fragment() {

    private var _binding: FragmentBuscarPelisBinding? = null
    private val binding get() = _binding!!
    private lateinit var action: NavDirections
    private lateinit var multimediaAdapter: MultimediaAdapter

    private val viewmodel: MainViewModel by viewModels()


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
            MultimediaAdapter(object : MultimediaAdapter.MultimediaActions {
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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewmodel.multiMedia.collect { value ->
                    binding.loading.visibility =
                    if (value.isLoading){
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                    multimediaAdapter.submitList(value.multimedia)
//                    value.error.let{
//                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
//                    }

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

        viewmodel.handleEvent(BuscarPelisContract.Event.getPopularMovies)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
                    viewmodel.handleEvent(BuscarPelisContract.Event.getMultiSearch(it,getString(R.string.all)))
                }

                return false
            }

        })
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.pelis -> {
                viewmodel.handleEvent(BuscarPelisContract.Event.getPopularMovies)
                true
            }

            R.id.series -> {
                viewmodel.handleEvent(BuscarPelisContract.Event.getPopularSeries)
                true
            }
            else -> false
        }

        return super.onOptionsItemSelected(menuItem)
    }

}