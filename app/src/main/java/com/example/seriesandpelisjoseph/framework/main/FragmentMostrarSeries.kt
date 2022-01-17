package com.example.seriesandpelisjoseph.framework.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.loadAny
import com.example.seriesandpelisjoseph.R
import com.example.seriesandpelisjoseph.databinding.FragmentMostrarSeriesBinding
import com.example.seriesandpelisjoseph.domain.Capitulo
import com.example.seriesandpelisjoseph.domain.Serie
import com.example.seriesandpelisjoseph.framework.main.adapter.CapsAdapter
import com.example.seriesandpelisjoseph.framework.viemodels.MostrarSeriesViewModel
import com.example.seriesandpelisjoseph.utils.Constantes
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentMostrarSeries : Fragment() {
    private var _binding: FragmentMostrarSeriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var capsAdapter: CapsAdapter
    private lateinit var actionMode: ActionMode
    private lateinit var serieFinal: Serie
    private val viewModel: MostrarSeriesViewModel by viewModels()

    private val callBack by lazy {
        configContextBar()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.series).isVisible = false
        menu.findItem(R.id.pelis).isVisible = false
        menu.findItem(R.id.buscar).isVisible = false
    }

    private fun configContextBar() = object : ActionMode.Callback {
        override fun onCreateActionMode(p0: ActionMode?, menu: Menu?): Boolean {
            requireActivity().menuInflater.inflate(R.menu.context_bar, menu)
            return true
        }

        override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(p0: ActionMode?, item: MenuItem?): Boolean {
            return when (item?.itemId) {
                R.id.visto -> {
                    Snackbar.make(binding.root, Constantes.VISTO, Snackbar.LENGTH_LONG)
                    return true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(p0: ActionMode?) {
            capsAdapter.resetSelectMode()
            viewModel.clearList()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMostrarSeriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: FragmentMostrarSeriesArgs by navArgs()
        capsAdapter = CapsAdapter(object : CapsAdapter.CapsActions {
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

            override fun isItemSelected(capitulo: Capitulo): Boolean =
                viewModel.isSelected(capitulo)

        })
        binding.rvCaps.adapter = capsAdapter
        viewModel.getSerie(args.serieid)
        viewModel.serieData.observe(this, {
            with(binding) {
                imageView.loadAny(it?.imagen?.let { getString(R.string.pathImage) + it }
                    ?: run { this.root.context.getDrawable(R.drawable.img) })
                tvTitulo.text = it?.tituloSerie
                tvDescripcion.text = it?.descripcion
                tvFecha.text = it?.fecha
                it?.temporadas?.map { temporada -> temporada.idSerie = it.id }
                val adapter = it?.temporadas?.let {
                    ArrayAdapter(
                        this.root.context, android.R.layout.simple_spinner_item,
                        it.toList()
                    )
                }

                binding.seasonSpinner.adapter = adapter

                binding.seasonSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener,
                        AdapterView.OnItemClickListener {
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
                            it?.temporadas?.get(p2)?.let { temporada ->
                                viewModel.getCapitulo(
                                    it.idApi,
                                    temporada.seasonNumber
                                )
                            }
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {

                        }

                        override fun onItemClick(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {

                        }

                    }

                addCaps(it)
            }
        })


        viewModel.capituloData.observe(this, {
            capsAdapter.submitList(it)
        })
        viewModel.error.observe(this, {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        })

    }

    private fun addCaps(serie: Serie?) {
        serie?.temporadas?.forEach { temporada ->
            viewModel.getCapitulo(
                serie.idApi,
                temporada.seasonNumber
            )
        }
        viewModel.capituloData.observe(this, {
            serie?.temporadas?.map { temporada ->
                if (it != null) {
                    temporada.capitulos = it
                    temporada.capitulos?.map { it.idTemporada = temporada.id }
                }
            }
        })

        if (serie != null) {
            serieFinal = serie
        }

    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.favoritos -> {
                viewModel.insertSerie(serieFinal)
                Toast.makeText(this.requireContext(), Constantes.SERIE_AÑADIDA, Toast.LENGTH_SHORT).show()
                true
            }
            else -> false
        }
        return super.onOptionsItemSelected(menuItem)
    }
}