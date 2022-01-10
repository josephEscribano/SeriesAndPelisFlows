package com.example.seriesandpelisjoseph.framework.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesandpelisjoseph.data.model.toMovie
import com.example.seriesandpelisjoseph.data.repositories.MovieRepository
import com.example.seriesandpelisjoseph.domain.Movie
import com.example.seriesandpelisjoseph.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val movieRepository: MovieRepository): ViewModel(){

    private val listaMovie = mutableListOf<Movie>()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _movies = MutableLiveData<List<Movie>>()
    val movie: LiveData<List<Movie>> get() = _movies

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> get() = _loading

    fun getMovie(titulo:String){
        viewModelScope.launch {
            val result = movieRepository.getMovie(titulo,1)
            if (result is NetworkResult.Succcess)
                if (listaMovie.isNotEmpty())
                    listaMovie.clear()


            when(result){
                is NetworkResult.Succcess -> result.data?.let { it.resultMediaPojos.map { resultPojo -> listaMovie.add(resultPojo.toMovie()) }}
                is NetworkResult.Error -> _error.value = result.message ?: "Error"
            }
            if (result is NetworkResult.Succcess)
                _movies.value = listaMovie.toList()

        }


    }
    fun getPopularMovies(){
        viewModelScope.launch {
            val result = movieRepository.getPopularMovies()
            if (result is NetworkResult.Succcess)
                if (listaMovie.isNotEmpty())
                    listaMovie.clear()
            when(result){
                is NetworkResult.Succcess -> result.data?.let { it.resultMediaPojos.map { resultMediaPojo -> listaMovie.add(resultMediaPojo.toMovie())} }
                is NetworkResult.Error -> _error.value = result.message ?: "Error"
            }
            if (result is NetworkResult.Succcess)
                _movies.value = listaMovie.toList()
        }
    }

    fun getPopularSeries(){
        viewModelScope.launch {
            val result = movieRepository.getPopularSeries()
            if (result is NetworkResult.Succcess)
                if (listaMovie.isNotEmpty())
                    listaMovie.clear()
            when(result){
                is NetworkResult.Succcess -> result.data?.let { it.resultMediaPojos.map { resultMediaPojo -> listaMovie.add(resultMediaPojo.toMovie())} }
                is NetworkResult.Error -> _error.value = result.message ?: "Error"
            }
            if (result is NetworkResult.Succcess)
                _movies.value = listaMovie.toList()
        }
    }
    fun getMultiSearch(titulo: String){
        viewModelScope.launch {
            val result = movieRepository.getAll(titulo)
            if (result is NetworkResult.Succcess)
                if (listaMovie.isNotEmpty())
                    listaMovie.clear()

            when(result){
                is NetworkResult.Succcess -> result.data?.let { it.resultMediaPojos.map { resultMediaPojo -> _movies.value = listaMovie.filter { movie ->  movie.titulo.startsWith(titulo) }.toList() } }
                is NetworkResult.Error -> _error.value = result.message ?: "Error"
            }

        }
    }
}