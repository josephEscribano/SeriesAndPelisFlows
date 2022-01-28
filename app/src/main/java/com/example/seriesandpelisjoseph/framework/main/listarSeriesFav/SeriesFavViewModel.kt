package com.example.seriesandpelisjoseph.framework.main.listarSeriesFav

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesandpelisjoseph.domain.MultiMedia
import com.example.seriesandpelisjoseph.domain.Serie
import com.example.seriesandpelisjoseph.usecases.DeleteSerie
import com.example.seriesandpelisjoseph.usecases.GetSerie
import com.example.seriesandpelisjoseph.usecases.GetSeries
import com.example.seriesandpelisjoseph.utils.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesFavViewModel @Inject constructor(
    private val getSeries: GetSeries,
    private val deleteSerie: DeleteSerie,
    private val getSerie: GetSerie
) : ViewModel() {


    private val _serieFavData = MutableLiveData<List<MultiMedia>?>()
    val serieData: LiveData<List<MultiMedia>?> get() = _serieFavData


    private val _serie = MutableLiveData<Serie>()
    val serie: LiveData<Serie> get() = _serie

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getSeries() {
        viewModelScope.launch {
            try {
                _serieFavData.value = getSeries.invoke()
            } catch (e: Exception) {
                Log.e(Constantes.ERROR_OBTENER_FAV, e.message, e)
                _error.value = e.message
            }
        }

    }

    fun deleteSerie(serie: Serie) {
        viewModelScope.launch {
            try {
                deleteSerie.invoke(serie)
                getSeries()
            } catch (e: Exception) {
                Log.e(Constantes.ERROR_OBTENER_FAV, e.message, e)
                _error.value = e.message
            }
        }
    }

    fun getSerie(id: Int?) {
        viewModelScope.launch {
            try {
                _serie.value = id?.let { getSerie.invoke(it) }
            } catch (e: Exception) {
                Log.e(Constantes.ERROR_OBTENER_FAV, e.message, e)
                _error.value = e.message
            }

        }
    }


}