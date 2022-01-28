package com.example.seriesandpelisjoseph.framework.main.mostrarSeriesFavRoom

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesandpelisjoseph.data.repositories.SerieRepository
import com.example.seriesandpelisjoseph.domain.Capitulo
import com.example.seriesandpelisjoseph.domain.Serie
import com.example.seriesandpelisjoseph.usecases.InsertSerie
import com.example.seriesandpelisjoseph.usecases.RepetidoSerie
import com.example.seriesandpelisjoseph.utils.Constantes
import com.example.seriesandpelisjoseph.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MostrarSeriesViewModel @Inject constructor(
    private val serieRepository: SerieRepository,
    private val insertSerie: InsertSerie,
    private val repetidoSerie: RepetidoSerie
) : ViewModel() {
    private val listaCapitulosSelected = mutableListOf<Capitulo>()

    private val _serieData = MutableLiveData<Serie?>()
    val serieData: LiveData<Serie?> get() = _serieData

    private val _capituloData = MutableLiveData<List<Capitulo>?>()
    val capituloData: LiveData<List<Capitulo>?> get() = _capituloData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _repetidoData = MutableLiveData<Int>()
    val repetidoData: LiveData<Int> get() = _repetidoData


    fun getSerie(tvId: Int) {
        viewModelScope.launch {
            val result = serieRepository.getSerie(tvId)

            when (result) {
                is NetworkResult.Error -> _error.value = result.message ?: Constantes.ERROR
                is NetworkResult.Succcess -> _serieData.value = result.data
            }

        }
    }

    fun getCapitulo(tvId: Int, seasonNumber: Int?) {
        viewModelScope.launch {
            val result = seasonNumber?.let { serieRepository.getCapitulos(tvId, it) }
            when (result) {
                is NetworkResult.Error -> _error.value = result.message ?: Constantes.ERROR
                is NetworkResult.Succcess -> _capituloData.value = result.data
            }
        }
    }

    fun insertSerie(serie: Serie) {
        viewModelScope.launch {

            try {
                insertSerie.invoke(serie)
            } catch (e: Exception) {
                Log.e(Constantes.ERROR_INSERTAR, e.message, e)
            }

        }
    }

    fun repetidoSerie(id: Int) {
        viewModelScope.launch {
            try {
                _repetidoData.value = repetidoSerie.invoke(id)
            } catch (e: Exception) {
                Log.e(Constantes.ERROR_OBTENER_FAV, e.message, e)
                _error.value = e.message
            }
        }
    }


    fun isSelected(capitulo: Capitulo): Boolean {
        return listaCapitulosSelected.contains(capitulo)
    }


}