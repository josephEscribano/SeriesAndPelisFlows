package com.example.seriesandpelisjoseph.framework.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.seriesandpelisjoseph.databinding.FragmentMostrarSeriesFavoritasBinding
import com.example.seriesandpelisjoseph.databinding.FragmentMoviesFavoritasBinding
import com.example.seriesandpelisjoseph.databinding.FragmentSeriesFavoritasBinding
import com.example.seriesandpelisjoseph.domain.Capitulo
import com.example.seriesandpelisjoseph.domain.MultiMedia
import com.example.seriesandpelisjoseph.framework.main.adapter.CapsAdapter
import com.example.seriesandpelisjoseph.framework.main.adapter.MultimediaAdapter
import com.example.seriesandpelisjoseph.framework.viemodels.SeriesFavViewModel
import com.example.seriesandpelisjoseph.utils.Constantes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MostrarSeriesFavoritas : Fragment() {
    private var _binding : FragmentMostrarSeriesFavoritasBinding? = null
    private val binding get() = _binding!!
    private lateinit var capsAdapter: CapsAdapter
    private lateinit var action: NavDirections
    private val viewmodel: SeriesFavViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMostrarSeriesFavoritasBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        capsAdapter = CapsAdapter(object : CapsAdapter.CapsActions{
            override fun onStarSelectMode() {
                (requireActivity() as MainActivity).startSupportActionMode(callBack)?.let {
                    actionMode = it
                    actionMode.title = Constantes.NUM_SELECTED
                }
            }

            override fun itemHasClicked(capitulo: Capitulo) {
                viewModel.seleccionaCapitulo(capitulo)
                actionMode.title = "${viewModel.getLista().size} ${Constantes.SELECTED}"
            }

            override fun isItemSelected(capitulo: Capitulo): Boolean  = viewModel.isSelected(capitulo)

        })

        binding.rvCaps.adapter = multimediaAdapter

        viewmodel.serieData.observe(this,{ multimedia ->
            multimediaAdapter.submitList(multimedia)

        })

        viewmodel.error.observe(this,{
            Toast.makeText(this.requireContext(), it , Toast.LENGTH_SHORT).show()
        })
        viewmodel.getSeries()

    }


}