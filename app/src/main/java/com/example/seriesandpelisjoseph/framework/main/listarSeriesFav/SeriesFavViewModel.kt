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
                    event.id?.let {
                        serieRepository.getSerieRoom(it)
                            .catch(action = { cause ->
                                _error.send(cause.message ?: Constantes.ERROR)
                        }).collect {
                            _uiState.update { stateLMMovies -> stateLMMovies.copy(serie = it) }
                        }
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


}