package com.example.seriesandpelisjoseph.framework.main.listarMostrarMoviesFav

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import coil.loadAny
import com.example.seriesandpelisjoseph.R
import com.example.seriesandpelisjoseph.data.model.toMovie
import com.example.seriesandpelisjoseph.databinding.FragmentMostrarPelisBinding
import com.example.seriesandpelisjoseph.domain.Movie
import com.example.seriesandpelisjoseph.utils.Constantes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FragmentMostrarPelis : Fragment() {
    private var _binding: FragmentMostrarPelisBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieFinal: Movie
    private val viewmodel: MovieFavViewModel by viewModels()

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMostrarPelisBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: FragmentMostrarPelisArgs by navArgs()
        with(binding) {
            imageView.loadAny(args.multimedia.imagen?.let { getString(R.string.pathImage) + it }
                ?: run { this.root.context.getDrawable(R.drawable.img) })
            tvTitulo.text = args.multimedia.titulo
            tvDescripcion.text = args.multimedia.descripcion
            tvFecha.text = args.multimedia.fechaEmision
        }
        movieFinal = args.multimedia.toMovie()
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.favoritos -> {

                viewmodel.handleEvent(ListarMostrarMoviesContract.Event.repetido(movieFinal.idApi))
                lifecycleScope.launch {
                    var entrar = true
                    repeatOnLifecycle(Lifecycle.State.STARTED){
                        viewmodel.uiState.collect {
                            if (it.repetido == 0) {
                                viewmodel.handleEvent(ListarMostrarMoviesContract.Event.insertMovie(movieFinal))
                                Toast.makeText(
                                    context,
                                    Constantes.PELICULA_AÑADIDA,
                                    Toast.LENGTH_SHORT
                                ).show()
                                entrar = false
                            } else if (it.repetido > 0 && entrar){

                                Toast.makeText(
                                    context,
                                    Constantes.PELICULA_REPETIDA,
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