package com.example.seriesandpelisjoseph.framework.main.listarSeriesFav

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesandpelisjoseph.data.repositories.SerieRepository

import com.example.seriesandpelisjoseph.framework.main.listarSeriesFav.ListarSeriesFavContract.StateListarSeriesFav
import com.example.seriesandpelisjoseph.utils.Constantes
import com.example.seriesandpelisjoseph.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesFavViewModel @Inject constructor(
    private val  serieRepository: SerieRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<StateListarSeriesFav> by lazy {
        MutableStateFlow(StateListarSeriesFav())
    }
    val uiState: StateFlow<StateListarSeriesFav> = _uiState


    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()


    fun handleEvent(event: ListarSeriesFavContract.Event){
        when(event){
            is ListarSeriesFavContract.Event.deleteSerie -> {
                viewModelScope.launch {
                    try {
                        serieRepository.deleteSerie(event.serie)
                    } catch (e: Exception) {
                        Log.e(Constantes.ERROR_OBTENER_FAV, e.message, e)
                        _error.send(e.message ?: Constantes.ERROR)
                    }
                }
            }
            is ListarSeriesFavContract.Event.getSerie -> {
                viewModelScope.launch {
                    serieRepository.getSerieRoom(event.id!!).catch(action = {
                            cause -> _error.send(cause.message ?: Constantes.ERROR)
                    }).collect {
                        _uiState.update { stateLMMovies -> stateLMMovies.copy(serie = it) }
                    }

                }
            }
            ListarSeriesFavContract.Event.getSeries -> {
                viewModelScope.launch {
                    serieRepository.getSeries().catch(action = { cause ->
                        _error.send(cause.message ?: Constantes.ERROR)
                    }).collect {
                        _uiState.update { stateListarSeriesFav -> stateListarSeriesFav.copy(multimedia = it) }
                    }
                }
            }
        }
    }
//    private val _serieFavData = MutableLiveData<List<MultiMedia>?>()
//    val serieData: LiveData<List<MultiMedia>?> get() = _serieFavData
//
//
//    private val _serie = MutableLiveData<Serie>()
//    val serie: LiveData<Serie> get() = _serie
//
//    private val _error = MutableLiveData<String>()
//    val error: LiveData<String> get() = _error

//    fun getSeries() {
//        viewModelScope.launch {
//            try {
//                _serieFavData.value = getSeries.invoke()
//            } catch (e: Exception) {
//                Log.e(Constantes.ERROR_OBTENER_FAV, e.message, e)
//                _error.value = e.message
//            }
//        }
//
//    }
//
//    fun deleteSerie(serie: Serie) {
//        viewModelScope.launch {
//            try {
//                deleteSerie.invoke(serie)
//                getSeries()
//            } catch (e: Exception) {
//                Log.e(Constantes.ERROR_OBTENER_FAV, e.message, e)
//                _error.value = e.message
//            }
//        }
//    }
//
//    fun getSerie(id: Int?) {
//        viewModelScope.launch {
//            try {
//                _serie.value = id?.let { getSerie.invoke(it) }
//            } catch (e: Exception) {
//                Log.e(Constantes.ERROR_OBTENER_FAV, e.message, e)
//                _error.value = e.message
//            }
//
//        }
//    }


}