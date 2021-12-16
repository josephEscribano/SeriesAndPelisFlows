package com.example.seriesandpelisjoseph.framework.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesandpelisjoseph.data.pruebaModelo.Movie
import com.example.seriesandpelisjoseph.data.repositories.SeriesRepository
import com.example.seriesandpelisjoseph.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val seriesRepository: SeriesRepository): ViewModel(){

    private val listaMovie = mutableListOf<Movie>()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _movies = MutableLiveData<List<Movie>>()
    val movie: LiveData<List<Movie>> get() = _movies

    fun getMovie(id:Int){
        viewModelScope.launch {
            val result = seriesRepository.getMovie(id)

            when(result){

                is NetworkResult.Succcess -> result.data?.let { listaMovie.add(it) }
                is NetworkResult.Error -> _error.value = result.message ?: ""
                is NetworkResult.Loading -> TODO()
            }

            _movies.value = listaMovie

        }
    }
}