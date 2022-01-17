package com.example.seriesandpelisjoseph.framework.viemodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesandpelisjoseph.domain.Movie
import com.example.seriesandpelisjoseph.domain.MultiMedia
import com.example.seriesandpelisjoseph.usecases.DeleteMovie
import com.example.seriesandpelisjoseph.usecases.GetMovies
import com.example.seriesandpelisjoseph.usecases.InsertMovie
import com.example.seriesandpelisjoseph.usecases.Repetido
import com.example.seriesandpelisjoseph.utils.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieFavViewModel @Inject constructor(
    private val getMovies: GetMovies,
    private val InsertMovie: InsertMovie,
    private val DeleteMovie: DeleteMovie,
    private val repetido: Repetido
) : ViewModel() {

    private val _movieFavData = MutableLiveData<List<MultiMedia>?>()
    val movieData: LiveData<List<MultiMedia>?> get() = _movieFavData

    private val _repetidoData = MutableLiveData<Int>()
    val repetidoData: LiveData<Int> get() = _repetidoData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getMovie() {
        viewModelScope.launch {
            try {
                _movieFavData.value = getMovies.invoke()
            } catch (e: Exception) {
                Log.e(Constantes.ERROR_OBTENER_MOVIE_FAV, e.message, e)
                _error.value = e.message
            }
        }

    }

    fun insertMovie(movie: Movie) {
        viewModelScope.launch {
            try {
                InsertMovie.invoke(movie)
            } catch (e: Exception) {
                Log.e(Constantes.ERROR_OBTENER_MOVIE_FAV, e.message, e)
                _error.value = e.message
            }
        }

    }

    fun deleteMovie(movie: Movie?) {
        viewModelScope.launch {
            try {
                if (movie != null) {
                    DeleteMovie.invoke(movie)
                }
            } catch (e: Exception) {
                Log.e(Constantes.ERROR_OBTENER_MOVIE_FAV, e.message, e)
                _error.value = e.message
            }
        }
    }

    fun repetido(id:Int){
        viewModelScope.launch {
            try {
                _repetidoData.value = repetido.invoke(id)
            }catch (e: Exception) {
                Log.e(Constantes.ERROR_OBTENER_MOVIE_FAV, e.message, e)
                _error.value = e.message
            }
        }
    }
}