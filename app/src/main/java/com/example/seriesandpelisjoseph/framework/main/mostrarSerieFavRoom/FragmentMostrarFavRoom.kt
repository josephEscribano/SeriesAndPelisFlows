package com.example.seriesandpelisjoseph.framework.main.mostrarSerieFavRoom

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import coil.loadAny
import com.example.seriesandpelisjoseph.R
import com.example.seriesandpelisjoseph.databinding.FragmentMostrarFavRoomBinding
import com.example.seriesandpelisjoseph.domain.Capitulo
import com.example.seriesandpelisjoseph.domain.Serie

import com.example.seriesandpelisjoseph.framework.main.MainActivity
import com.example.seriesandpelisjoseph.framework.main.adapter.CapsAdapterRoom
import com.example.seriesandpelisjoseph.utils.Constantes
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FragmentMostrarFavRoom : Fragment() {
    private var _binding: FragmentMostrarFavRoomBinding? = null
    private val binding get() = _binding!!
    private lateinit var capsAdapter: CapsAdapterRoom
    private lateinit var actionMode: ActionMode
    private lateinit var serieFinal: Serie

    private val viewModel: MostrarSeriesFavViewModel by viewModels()

    private val callBack by lazy {
        configContextBar()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        val args: FragmentMostrarFavRoomArgs by navArgs()
        serieFinal = args.serie

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.series).isVisible = false
        menu.findItem(R.id.pelis).isVisible = false
        menu.findItem(R.id.buscar).isVisible = false
        menu.findItem(R.id.favoritos).isVisible = false
        if (serieFinal.visto == true) {
            menu.findItem(R.id.vistoroom).setIcon(R.drawable.outline_visibility_off_24)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMostrarFavRoomBinding.inflate(inflater, container, false)
        return binding.root
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
                    viewModel.handleEvent(MostrarSeriesFavContract.Event.updateCapitulos)
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

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: FragmentMostrarFavRoomArgs by navArgs()

        capsAdapter = CapsAdapterRoom(object : CapsAdapterRoom.CapsActions {
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

            override fun updateCapitulo(capitulo: Capitulo) {
                viewModel.handleEvent(MostrarSeriesFavContract.Event.updateCapitulo(capitulo))
            }

        })



        binding.rvCaps.adapter = capsAdapter
        var entrar = true
        viewModel.handleEvent(MostrarSeriesFavContract.Event.getSerie(args.serie.id))
        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    it.serie?.let { serie ->
                        if (entrar) {
                            with(binding) {
                                imageView.loadAny(serie.imagen?.let { getString(R.string.pathImage) + it }
                                    ?: run { this.root.context.getDrawable(R.drawable.img) })
                                tvTitulo.text = serie.tituloSerie
                                tvDescripcion.text = serie.descripcion
                                tvFecha.text = serie.fecha
                                serie.temporadas?.map { temporada -> temporada.idSerie = serie.id }
                                val adapter = serie.temporadas?.let {
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
                                            serie.temporadas?.get(p2)?.let { temporada ->
                                                viewModel.handleEvent(
                                                    MostrarSeriesFavContract.Event.getCapitulos(
                                                        temporada.idApi
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
                            }
                            serieFinal = serie

                            entrar = false
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
            R.id.vistoroom -> {
                if (serieFinal.visto == true) {
                    serieFinal.visto = false
                    menuItem.setIcon(R.drawable.ic_baseline_remove_red_eye_24)
                } else {
                    serieFinal.visto = true
                    menuItem.setIcon(R.drawable.outline_visibility_off_24)
                }
                viewModel.handleEvent(MostrarSeriesFavContract.Event.updateSerie(serieFinal))

                true
            }
            else -> false
        }
        return super.onOptionsItemSelected(menuItem)
    }


}