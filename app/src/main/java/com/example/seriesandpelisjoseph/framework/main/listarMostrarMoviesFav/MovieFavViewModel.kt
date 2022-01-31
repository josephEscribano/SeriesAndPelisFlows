package com.example.seriesandpelisjoseph.framework.main.listarMostrarMoviesFav

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesandpelisjoseph.data.repositories.MovieRepository
import com.example.seriesandpelisjoseph.domain.Movie
import com.example.seriesandpelisjoseph.usecases.DeleteMovie
import com.example.seriesandpelisjoseph.usecases.GetMovies
import com.example.seriesandpelisjoseph.usecases.InsertMovie
import com.example.seriesandpelisjoseph.usecases.Repetido
import com.example.seriesandpelisjoseph.utils.Constantes
import com.example.seriesandpelisjoseph.framework.main.listarMostrarMoviesFav.ListarMostrarMoviesContract.StateLMMovies
import com.example.seriesandpelisjoseph.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieFavViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val InsertMovie: InsertMovie,
) : ViewModel() {

    private val _uiState : MutableStateFlow<StateLMMovies> by lazy {
        MutableStateFlow(StateLMMovies())
    }
    val uiState: StateFlow<StateLMMovies> = _uiState

//    private val _repetidoData : MutableStateFlow<StateLMMovies> by lazy {
//        MutableStateFlow(StateLMMovies())
//    }
//    val repetidoData: StateFlow<StateLMMovies> = _repetidoData

    private val _error = Channel<String>()
    val error= _error.receiveAsFlow()

    fun handleEvent(event: ListarMostrarMoviesContract.Event){
        when(event){
            is ListarMostrarMoviesContract.Event.deleteMovie -> TODO()
            ListarMostrarMoviesContract.Event.getMovies -> {
                viewModelScope.launch {
                    movieRepository.getMovies().catch(action = {
                            cause -> _error.send(cause.message ?: Constantes.ERROR)
                    }).collect {
                        try {
                            _uiState.update { stateLMMovies -> stateLMMovies.copy(multimedia = it) }
                        } catch (e: Exception) {
                            Log.e(Constantes.ERROR_OBTENER_MOVIE_FAV, e.message, e)
                        }
                    }
                }
            }
            is ListarMostrarMoviesContract.Event.insertMovie -> TODO()
            is ListarMostrarMoviesContract.Event.repetido -> TODO()
        }
    }
//    fun getMovies() {
//        viewModelScope.launch {
//            try {
//                _uiState.value = getMovies.invoke()
//            } catch (e: Exception) {
//                Log.e(Constantes.ERROR_OBTENER_MOVIE_FAV, e.message, e)
//                _error.value = e.message
//            }
//        }
//
//    }
//
    fun insertMovie(movie: Movie) {
        viewModelScope.launch {
            try {
                InsertMovie.invoke(movie)
            } catch (e: Exception) {
                Log.e(Constantes.ERROR_OBTENER_MOVIE_FAV, e.message, e)
            }
        }

    }
//
//    fun deleteMovie(movie: Movie?) {
//        viewModelScope.launch {
//            try {
//                if (movie != null) {
//                    DeleteMovie.invoke(movie)
//                    getMovies()
//                }
//            } catch (e: Exception) {
//                Log.e(Constantes.ERROR_OBTENER_MOVIE_FAV, e.message, e)
//                _error.value = e.message
//            }
//        }
//    }
//
//    fun repetido(id: Int) {
//        viewModelScope.launch {
//            try {
//                _repetidoData.value = repetido.invoke(id)
//            } catch (e: Exception) {
//                Log.e(Constantes.ERROR_OBTENER_MOVIE_FAV, e.message, e)
//                _error.value = e.message
//            }
//        }
//    }
}