package com.example.seriesandpelisjoseph.framework.main.mostrarSeriesRemoto

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import coil.loadAny
import com.example.seriesandpelisjoseph.R
import com.example.seriesandpelisjoseph.databinding.FragmentMostrarSeriesBinding
import com.example.seriesandpelisjoseph.domain.Serie
import com.example.seriesandpelisjoseph.framework.main.adapter.CapsAdapter
import com.example.seriesandpelisjoseph.utils.Constantes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentMostrarSeries : Fragment() {
    private var _binding: FragmentMostrarSeriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var capsAdapter: CapsAdapter
    private lateinit var serieFinal: Serie
    private var idSerie: Int = 0
    private val viewModel: MostrarSeriesViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.series).isVisible = false
        menu.findItem(R.id.pelis).isVisible = false
        menu.findItem(R.id.buscar).isVisible = false
        menu.findItem(R.id.vistoroom).isVisible = false
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
        var entrar = true
        capsAdapter = CapsAdapter()
        binding.rvCaps.adapter = capsAdapter
        idSerie = args.serieid


        viewModel.handleEvent(MostrarSeriesRemotoContract.Event.getSerie(idSerie))
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value ->
                    if (entrar) {
                        value.serie?.let {
                            with(binding) {
                                imageView.loadAny(it.imagen?.let { getString(R.string.pathImage) + it }
                                    ?: run { this.root.context.getDrawable(R.drawable.img) })
                                tvTitulo.text = it.tituloSerie
                                tvDescripcion.text = it.descripcion
                                tvFecha.text = it.fecha
                                it.temporadas?.map { temporada -> temporada.idSerie = it.id }
                                val adapter = it.temporadas?.let {
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
                                            it.temporadas?.get(p2)?.let { temporada ->
                                                viewModel.handleEvent(
                                                    MostrarSeriesRemotoContract.Event.getCapitulos(
                                                        it.idApi,
                                                        temporada.seasonNumber
                                                    )
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

                                serieFinal = it
                                entrar = false


                            }
                        }
                    }

                }
            }

        }




        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value ->
                    capsAdapter.submitList(value.capitulos)

                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collect {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }


    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.favoritos -> {
                viewModel.handleEvent(MostrarSeriesRemotoContract.Event.repetidoSerie(serieFinal.idApi))
                lifecycleScope.launch {
                    var entrar = true
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.uiState.collect {
                            if (it.repetido == 0 && entrar) {
                                viewModel.handleEvent(
                                    MostrarSeriesRemotoContract.Event.insertSerie(
                                        idSerie
                                    )
                                )
                                Toast.makeText(
                                    context,
                                    Constantes.SERIE_AÑADIDA,
                                    Toast.LENGTH_SHORT
                                ).show()
                                entrar = false
                            } else if (it.repetido > 0 && entrar) {

                                Toast.makeText(
                                    context,
                                    Constantes.SERIE_REPETIDA,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

                true
            }
            else -> false
        }
        return super.onOptionsItemSelected(menuItem)
    }
}