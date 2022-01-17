package com.example.seriesandpelisjoseph.framework.viemodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesandpelisjoseph.R
import com.example.seriesandpelisjoseph.data.repositories.MultimediaRepository
import com.example.seriesandpelisjoseph.domain.MultiMedia
import com.example.seriesandpelisjoseph.utils.Constantes
import com.example.seriesandpelisjoseph.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val multimediaRepository: MultimediaRepository) :
    ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _multimedia = MutableLiveData<List<MultiMedia>?>()
    val multiMedia: LiveData<List<MultiMedia>?> get() = _multimedia


    fun getPopularMovies() {
        viewModelScope.launch {
            val result = multimediaRepository.getPopularMovies()
            when (result) {
                is NetworkResult.Succcess -> _multimedia.value = result.data
                is NetworkResult.Error -> _error.value = result.message ?: Constantes.ERROR
            }

        }
    }

    fun getPopularSeries() {
        viewModelScope.launch {
            val result = multimediaRepository.getPopularSeries()
            when (result) {
                is NetworkResult.Succcess -> _multimedia.value = result.data
                is NetworkResult.Error -> _error.value = result.message ?: Constantes.ERROR
            }

        }
    }

    fun getMultiSearch(titulo: String) {
        viewModelScope.launch {
            val result = multimediaRepository.getAll(titulo, R.string.all.toString())

            when (result) {
                is NetworkResult.Succcess -> _multimedia.value = result.data
                is NetworkResult.Error -> _error.value = result.message ?: Constantes.ERROR
            }


        }
    }
}