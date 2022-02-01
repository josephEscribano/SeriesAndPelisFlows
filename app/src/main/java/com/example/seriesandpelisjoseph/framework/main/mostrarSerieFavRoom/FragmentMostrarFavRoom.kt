package com.example.seriesandpelisjoseph.framework.main.mostrarSerieFavRoom

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.loadAny
import com.example.seriesandpelisjoseph.R
import com.example.seriesandpelisjoseph.databinding.FragmentMostrarFavRoomBinding
import com.example.seriesandpelisjoseph.domain.Capitulo
import com.example.seriesandpelisjoseph.domain.Serie
import com.example.seriesandpelisjoseph.domain.Temporada
import com.example.seriesandpelisjoseph.framework.main.MainActivity
import com.example.seriesandpelisjoseph.framework.main.adapter.CapsAdapterRoom
import com.example.seriesandpelisjoseph.utils.Constantes
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragmentMostrarFavRoom : Fragment() {
    private var _binding: FragmentMostrarFavRoomBinding? = null
    private val binding get() = _binding!!
    private lateinit var capsAdapter: CapsAdapterRoom
    private lateinit var actionMode: ActionMode
    private lateinit var temporadaSeleccionada: Temporada
    private val viewModel: MostrarSeriesFavViewModel by viewModels()

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
        menu.findItem(R.id.favoritos).isVisible = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                    viewModel.updateCapitulo()
                    temporadaSeleccionada.idApi?.let { viewModel.getCapitulo(it) }
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

        })

        binding.rvCaps.adapter = capsAdapter
//        viewModel.getSerie(args.idSerie)
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
                                temporadaSeleccionada = temporada
                                viewModel.getCapitulo(
                                    temporada.idApi
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

//                addCaps(it)
            }
        })

        viewModel.capituloData.observe(this, {
            capsAdapter.submitList(it)
        })
        viewModel.error.observe(this, {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        })
    }

//    private fun addCaps(serie: Serie?) {
//        serie?.temporadas?.forEach { temporada ->
//            viewModel.getCapitulo(
//                temporada.idApi
//            )
//        }
//        viewModel.capituloData.observe(this, {
//            serie?.temporadas?.map { temporada ->
//                if (it != null) {
//                    temporada.capitulos = it
//                    temporada.capitulos?.map { it.idTemporada = temporada.id }
//                }
//            }
//        })
//
//
//    }


}