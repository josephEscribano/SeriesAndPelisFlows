package com.example.seriesandpelisjoseph.framework.main.mostrarSeriesRemoto

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesandpelisjoseph.data.repositories.SerieRepository
import com.example.seriesandpelisjoseph.framework.main.mostrarSeriesRemoto.MostrarSeriesRemotoContract.StateMostrarSeriesRemoto
import com.example.seriesandpelisjoseph.utils.Constantes
import com.example.seriesandpelisjoseph.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MostrarSeriesViewModel @Inject constructor(
    private val serieRepository: SerieRepository,
) : ViewModel() {


    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    private val _uiState: MutableStateFlow<StateMostrarSeriesRemoto> by lazy {
        MutableStateFlow(StateMostrarSeriesRemoto())
    }
    val uiState: StateFlow<StateMostrarSeriesRemoto> = _uiState


    fun handleEvent(event: MostrarSeriesRemotoContract.Event) {
        when (event) {
            is MostrarSeriesRemotoContract.Event.getCapitulos -> {
                viewModelScope.launch {
                    serieRepository.getCapitulos(event.tvId, event.seasonNumber!!)
                        .catch(action = { cause ->
                            _error.send(cause.message ?: Constantes.ERROR)
                        }).collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                _uiState.update { it.copy(error = result.message) }
                            }
                            is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                            is NetworkResult.Succcess -> _uiState.update {
                                it.copy(
                                    capitulos = result.data ?: emptyList(), isLoading = false
                                )
                            }
                        }
                    }
                }
            }
            is MostrarSeriesRemotoContract.Event.getSerie -> {
                viewModelScope.launch {
                    serieRepository.getSerie(event.tvId).catch(action = { cause ->
                        _error.send(cause.message ?: Constantes.ERROR)
                    }).collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                _uiState.update { it.copy(error = result.message) }
                            }
                            is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                            is NetworkResult.Succcess -> _uiState.update {
                                it.copy(
                                    serie = result.data, isLoading = false
                                )
                            }
                        }
                    }
                }
            }
            is MostrarSeriesRemotoContract.Event.insertSerie -> {
                viewModelScope.launch {
                    val serie = uiState.value.serie
                    if (serie != null) {
                        serie.temporadas?.map {
                            serieRepository.getCapitulos(event.idserie, it.seasonNumber!!)
                                .catch(action = { cause ->
                                    _error.send(cause.message ?: Constantes.ERROR)
                                }).collect { result ->
                                when (result) {
                                    is NetworkResult.Error -> {
                                        _uiState.update { it.copy(error = result.message) }
                                    }
                                    is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                                    is NetworkResult.Succcess -> it.capitulos = result.data
                                }
                            }
                        }
                    }
                    try {
                        if (serie != null) {
                            serieRepository.insertSerie(serie)
                        }
                    } catch (e: Exception) {
                        Log.e(Constantes.ERROR_INSERTAR, e.message, e)
                        _error.send(e.message ?: Constantes.ERROR)
                    }
                }


            }
            is MostrarSeriesRemotoContract.Event.repetidoSerie -> {
                viewModelScope.launch {

                    viewModelScope.launch {
                        serieRepository.repetidoSerie(event.id).catch { cause ->
                            _error.send(cause.message ?: Constantes.ERROR)
                        }.collect {
                            _uiState.update { stateLMMovies -> stateLMMovies.copy(repetido = it) }
                        }
                    }
                }
            }

        }
    }


}