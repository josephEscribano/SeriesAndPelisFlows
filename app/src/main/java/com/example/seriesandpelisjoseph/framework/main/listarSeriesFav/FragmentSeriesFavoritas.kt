package com.example.seriesandpelisjoseph.framework.main.listarSeriesFav

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
import com.example.seriesandpelisjoseph.databinding.FragmentSeriesFavoritasBinding
import com.example.seriesandpelisjoseph.domain.MultiMedia
import com.example.seriesandpelisjoseph.domain.Serie
import com.example.seriesandpelisjoseph.framework.main.adapter.MultimediaAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentSeriesFavoritas : Fragment() {
    private var _binding: FragmentSeriesFavoritasBinding? = null
    private val binding get() = _binding!!
    private lateinit var multimediaAdapter: MultimediaAdapter
    private lateinit var action: NavDirections
    private val viewmodel: SeriesFavViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.series).isVisible = false
        menu.findItem(R.id.pelis).isVisible = false
        menu.findItem(R.id.buscar).isVisible = false
        menu.findItem(R.id.favoritos).isVisible = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSeriesFavoritasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        multimediaAdapter =
            MultimediaAdapter(object : MultimediaAdapter.MultimediaActions {
                override fun navegar(multiMedia: MultiMedia) {
                    action =
                        FragmentSeriesFavoritasDirections.actionFragmentSeriesFavoritasToFragmentMostrarFavRoom(
                            multiMedia.id
                        )
                    findNavController().navigate(action)
                }

                override fun deleteItem(multiMedia: MultiMedia?) {
                    var entrar = true
                    viewmodel.handleEvent(ListarSeriesFavContract.Event.getSerie(multiMedia?.id))
                    lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.STARTED){
                            viewmodel.uiState.collect {
                                if (entrar){
                                    it.serie?.let { serie ->
                                        viewmodel.handleEvent(ListarSeriesFavContract.Event.deleteSerie(serie))
                                        entrar = false
                                    }
                                }

                            }
                        }
                    }


                }

            })

        binding.rvSeriesFav.adapter = multimediaAdapter
        val touchHelper = ItemTouchHelper(multimediaAdapter.gesto)
        touchHelper.attachToRecyclerView(binding.rvSeriesFav)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewmodel.uiState.collect{ values ->
                    multimediaAdapter.submitList(values.multimedia)
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
        viewmodel.handleEvent(ListarSeriesFavContract.Event.getSeries)

    }


}