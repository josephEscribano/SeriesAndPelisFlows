package com.example.seriesandpelisjoseph.framework.main.listarMostrarMoviesFav

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesandpelisjoseph.data.repositories.MovieRepository
import com.example.seriesandpelisjoseph.framework.main.listarMostrarMoviesFav.ListarMostrarMoviesContract.StateLMMovies
import com.example.seriesandpelisjoseph.utils.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieFavViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<StateLMMovies> by lazy {
        MutableStateFlow(StateLMMovies())
    }
    val uiState: StateFlow<StateLMMovies> = _uiState


    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    fun handleEvent(event: ListarMostrarMoviesContract.Event) {
        when (event) {
            is ListarMostrarMoviesContract.Event.deleteMovie -> {
                viewModelScope.launch {
                    try {
                        event.movie?.let { movieRepository.deleteMovie(it) }
                    } catch (e: Exception) {
                        Log.e(Constantes.ERROR_OBTENER_MOVIE_FAV, e.message, e)
                        _error.send(e.message ?: Constantes.ERROR)
                    }
                }
            }
            ListarMostrarMoviesContract.Event.getMovies -> {
                viewModelScope.launch {
                    movieRepository.getMovies().catch(action = { cause ->
                        _error.send(cause.message ?: Constantes.ERROR)
                    }).collect {
                        _uiState.update { stateLMMovies -> stateLMMovies.copy(multimedia = it) }
                    }
                }
            }
            is ListarMostrarMoviesContract.Event.insertMovie -> {
                viewModelScope.launch {
                    try {
                        movieRepository.insertMovie(event.movie)

                    } catch (e: Exception) {
                        Log.e(Constantes.ERROR_OBTENER_MOVIE_FAV, e.message, e)
                        _error.send(e.message ?: Constantes.ERROR)
                    }
                }
            }
            is ListarMostrarMoviesContract.Event.repetido -> {
                viewModelScope.launch {
                    movieRepository.repetido(event.id).catch { cause ->
                        _error.send(cause.message ?: Constantes.ERROR)
                    }.collect {
                        _uiState.update { stateLMMovies ->  stateLMMovies.copy(repetido = it) }
                    }
                }
            }
        }
    }
}