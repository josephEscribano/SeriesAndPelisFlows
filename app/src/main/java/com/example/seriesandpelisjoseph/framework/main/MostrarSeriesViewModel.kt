package com.example.seriesandpelisjoseph.framework.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesandpelisjoseph.data.model.toSerie
import com.example.seriesandpelisjoseph.data.repositories.MultimediaRepository
import com.example.seriesandpelisjoseph.data.repositories.SerieRepository
import com.example.seriesandpelisjoseph.domain.Serie
import com.example.seriesandpelisjoseph.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MostrarSeriesViewModel @Inject constructor(private val serieRepository: SerieRepository) : ViewModel() {

    private val _SerieData = MutableLiveData<Serie>()
    val SerieData : LiveData<Serie> get() = _SerieData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getSerie(tvId:Int) {
        viewModelScope.launch {
            val result = serieRepository.getSerie(tvId)

            when(result){
                is NetworkResult.Error -> _error.value = result.message ?: "Error"
                is NetworkResult.Succcess -> result.data?.let { _SerieData.value = it.toSerie() }
            }

        }
    }
}