package com.example.seriesandpelisjoseph.framework.viemodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesandpelisjoseph.data.repositories.MovieRepository
import com.example.seriesandpelisjoseph.data.repositories.SerieRepository
import com.example.seriesandpelisjoseph.domain.Movie
import com.example.seriesandpelisjoseph.domain.MultiMedia
import com.example.seriesandpelisjoseph.usecases.GetMovies
import com.example.seriesandpelisjoseph.usecases.InsertSerie
import com.example.seriesandpelisjoseph.usecases.insertMovie
import com.example.seriesandpelisjoseph.utils.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MovieFavViewModel @Inject constructor(private val getMovies: GetMovies,
                                            private val insertMovie: insertMovie
): ViewModel() {

    private val _movieFavData = MutableLiveData<List<MultiMedia>?>()
    val movieData: LiveData<List<MultiMedia>?> get() = _movieFavData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getMovie(){
        viewModelScope.launch {
            try {
                _movieFavData.value = getMovies.invoke()
            }catch (e:Exception){
                Log.e(Constantes.ERROR_OBTENER_MOVIE_FAV, e.message, e)
                _error.value = e.message
            }
        }

    }

    fun insertMovie(movie: Movie){
        viewModelScope.launch {
            try {
                insertMovie.invoke(movie)
            }catch (e:Exception){
                Log.e(Constantes.ERROR_OBTENER_MOVIE_FAV, e.message, e)
                _error.value = e.message
            }
        }

    }
}