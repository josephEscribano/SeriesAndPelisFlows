package com.example.seriesandpelisjoseph.framework.main.buscarElementos

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesandpelisjoseph.data.repositories.MultimediaRepository
import com.example.seriesandpelisjoseph.framework.main.buscarElementos.BuscarPelisContract.StateBuscarPelis
import com.example.seriesandpelisjoseph.utils.Constantes
import com.example.seriesandpelisjoseph.utils.InternetConnection
import com.example.seriesandpelisjoseph.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.sql.Connection
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val multimediaRepository: MultimediaRepository) :
    ViewModel() {

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    private val _multimedia: MutableStateFlow<StateBuscarPelis> by lazy {
        MutableStateFlow(StateBuscarPelis())
    }
    val multiMedia: StateFlow<StateBuscarPelis> = _multimedia


    @RequiresApi(Build.VERSION_CODES.M)
    fun handleEvent(event: BuscarPelisContract.Event) {
        when (event) {

            is BuscarPelisContract.Event.getMultiSearch -> {
                viewModelScope.launch {
                    multimediaRepository.getAll(event.titulo, event.region)
                        .catch(action = { cause ->
                            _error.send(cause.message ?: Constantes.ERROR)
                        }).collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                _multimedia.update { it.copy(error = result.message) }
                            }
                            is NetworkResult.Loading -> _multimedia.update { it.copy(isLoading = true) }
                            is NetworkResult.Succcess -> _multimedia.update {
                                it.copy(
                                    multimedia = result.data ?: emptyList(), isLoading = false
                                )
                            }
                        }
                    }
                }
            }
            is BuscarPelisContract.Event.getPopularMovies -> {
                viewModelScope.launch {
                    multimediaRepository.getPopularMovies().catch(action = { cause ->
                        _error.send(cause.message ?: Constantes.ERROR)
                    }).collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                _multimedia.update { it.copy(error = result.message) }
                            }
                            is NetworkResult.Loading -> _multimedia.update { it.copy(isLoading = true) }
                            is NetworkResult.Succcess -> _multimedia.update {
                                it.copy(
                                    multimedia = result.data ?: emptyList(), isLoading = false
                                )
                            }
                        }
                    }
                }
            }
            is BuscarPelisContract.Event.getPopularSeries -> {
                viewModelScope.launch {
                    multimediaRepository.getPopularSeries().catch(action = { cause ->
                        _error.send(cause.message ?: Constantes.ERROR)
                    }).collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                _multimedia.update { it.copy(error = result.message) }
                            }
                            is NetworkResult.Loading -> _multimedia.update { it.copy(isLoading = true) }
                            is NetworkResult.Succcess -> _multimedia.update {
                                it.copy(
                                    multimedia = result.data ?: emptyList(), isLoading = false
                                )
                            }
                        }
                    }
                }
            }
            is BuscarPelisContract.Event.cachearPopulares -> {
                viewModelScope.launch {
                    multimediaRepository.cachearPopulares(event.conexion).catch(action = { cause ->
                        _error.send(cause.message ?: Constantes.ERROR)
                    }).collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                _multimedia.update { it.copy(error = result.message) }
                            }
                            is NetworkResult.Loading -> _multimedia.update { it.copy(isLoading = true) }
                            is NetworkResult.Succcess -> _multimedia.update {
                                it.copy(
                                    multimedia = result.data ?: emptyList(), isLoading = false
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}