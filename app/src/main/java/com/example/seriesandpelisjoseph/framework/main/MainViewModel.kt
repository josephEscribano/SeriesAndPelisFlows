package com.example.seriesandpelisjoseph.framework.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesandpelisjoseph.R
import com.example.seriesandpelisjoseph.data.model.toActorMultimedia
import com.example.seriesandpelisjoseph.data.model.toMovieMultimedia
import com.example.seriesandpelisjoseph.data.model.toSerie
import com.example.seriesandpelisjoseph.data.model.toSerieMultimedia
import com.example.seriesandpelisjoseph.data.repositories.MovieRepository
import com.example.seriesandpelisjoseph.domain.Movie
import com.example.seriesandpelisjoseph.domain.MultiMedia
import com.example.seriesandpelisjoseph.domain.Serie
import com.example.seriesandpelisjoseph.utils.Constantes
import com.example.seriesandpelisjoseph.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val movieRepository: MovieRepository): ViewModel(){

    private val listaMovie = mutableListOf<MultiMedia>()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _multimedia = MutableLiveData<List<MultiMedia>>()
    val multiMedia: LiveData<List<MultiMedia>> get() = _multimedia

    private val _SerieData = MutableLiveData<Serie>()
    val SerieData : LiveData<Serie> get() = _SerieData

    fun getMovie(titulo:String) {
        viewModelScope.launch {
            val result = movieRepository.getMovie(titulo,1)
            if (result is NetworkResult.Succcess)
                if (listaMovie.isNotEmpty())
                    listaMovie.clear()


            when(result){
                is NetworkResult.Succcess -> result.data?.let { it.resultMultimediaPojos.map { resultPojo -> listaMovie.add(resultPojo.toMovieMultimedia()) }}
                is NetworkResult.Error -> _error.value = result.message ?: "Error"
            }
            if (result is NetworkResult.Succcess)
                _multimedia.value = listaMovie.toList()

        }

    }

    fun getSerie(tvId:Int) {
        viewModelScope.launch {
            val result = movieRepository.getSerie(tvId)

            when(result){
                is NetworkResult.Error -> _error.value = result.message ?: "Error"
                is NetworkResult.Succcess -> result.data?.let { _SerieData.value = it.toSerie() }
            }

        }
    }
    fun getPopularMovies(){
        viewModelScope.launch {
            val result = movieRepository.getPopularMovies()
            if (result is NetworkResult.Succcess)
                if (listaMovie.isNotEmpty())
                    listaMovie.clear()
            when(result){
                is NetworkResult.Succcess -> result.data?.let { it.resultPopularMoviePojos.map { resultPopularSerie  -> listaMovie.add(resultPopularSerie.toMovieMultimedia())} }
                is NetworkResult.Error -> _error.value = result.message ?: "Error"
            }
            if (result is NetworkResult.Succcess)
                _multimedia.value = listaMovie.toList()
        }
    }

    fun getPopularSeries(){
        viewModelScope.launch {
            val result = movieRepository.getPopularSeries()
            if (result is NetworkResult.Succcess)
                if (listaMovie.isNotEmpty())
                    listaMovie.clear()
            when(result){
                is NetworkResult.Succcess -> result.data?.let { it.resultPopularSeriePojos.map { resultMediaPojo -> listaMovie.add(resultMediaPojo.toSerieMultimedia())} }
                is NetworkResult.Error -> _error.value = result.message ?: "Error"
            }
            if (result is NetworkResult.Succcess)
                _multimedia.value = listaMovie.toList()
        }
    }
    fun getMultiSearch(titulo: String){
        viewModelScope.launch {
            val result = movieRepository.getAll(titulo,R.string.all.toString())
            if (result is NetworkResult.Succcess)
                if (listaMovie.isNotEmpty())
                    listaMovie.clear()

            when(result){
                is NetworkResult.Succcess -> result.data?.let { it.resultMultimediaPojos.map { resultMediaPojo ->
                if(resultMediaPojo.mediaType.equals(Constantes.MOVIE))
                    listaMovie.add(resultMediaPojo.toMovieMultimedia())
                else if (resultMediaPojo.mediaType.equals(Constantes.TV))
                    listaMovie.add(resultMediaPojo.toSerieMultimedia())
                else
                    listaMovie.add(resultMediaPojo.toActorMultimedia())
                } }
                is NetworkResult.Error -> _error.value = result.message ?: "Error"
            }

            _multimedia.value = listaMovie.toList()
        }
    }
}