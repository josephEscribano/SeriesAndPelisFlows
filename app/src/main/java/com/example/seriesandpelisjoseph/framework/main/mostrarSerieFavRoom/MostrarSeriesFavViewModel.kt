package com.example.seriesandpelisjoseph.framework.main.mostrarSerieFavRoom

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesandpelisjoseph.data.repositories.SerieRepository
import com.example.seriesandpelisjoseph.domain.Capitulo
import com.example.seriesandpelisjoseph.framework.main.mostrarSerieFavRoom.MostrarSeriesFavContract.StateMostrarSerieFav
import com.example.seriesandpelisjoseph.utils.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MostrarSeriesFavViewModel @Inject constructor(
    private val serieRepository: SerieRepository

) : ViewModel() {
    private val listaCapitulosSelected = mutableListOf<Capitulo>()

    private val _uiState: MutableStateFlow<StateMostrarSerieFav> by lazy {
        MutableStateFlow(StateMostrarSerieFav())
    }
    val uiState: StateFlow<StateMostrarSerieFav> = _uiState


    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()


    fun handleEvent(event: MostrarSeriesFavContract.Event) {
        when (event) {
            is MostrarSeriesFavContract.Event.getCapitulos -> {
                //He tenido que meter booleans para controlar cuando pasar por el collect, ya que sino era incontrolable
                viewModelScope.launch {
                    var entrar = true
                    event.idTemporada?.let {
                        serieRepository.getCapitulosRoom(it).catch(action = { cause ->
                            _error.send(cause.message ?: Constantes.ERROR)
                        }).collect {
                            if (entrar) {
                                _uiState.update { stateLMMovies -> stateLMMovies.copy(capitulos = it) }
                            }
                            entrar = false

                        }
                    }

                }
            }
            is MostrarSeriesFavContract.Event.getSerie -> {
                viewModelScope.launch {
                    serieRepository.getSerieRoom(event.id!!).catch(action = { cause ->
                        _error.send(cause.message ?: Constantes.ERROR)
                    }).collect {
                        _uiState.update { stateLMMovies -> stateLMMovies.copy(serie = it) }
                    }

                }
            }
            MostrarSeriesFavContract.Event.updateCapitulos -> {
                viewModelScope.launch {
                    try {
                        listaCapitulosSelected.forEach { it.visto = true }
                        serieRepository.updateCapitulos(listaCapitulosSelected.toList())

                    } catch (e: Exception) {
                        Log.e(Constantes.ERROR_OBTENER_FAV, e.message, e)
                        _error.send(e.message ?: Constantes.ERROR)
                    }
                }
            }
            is MostrarSeriesFavContract.Event.updateCapitulo -> {
                viewModelScope.launch {
                    try {
                        serieRepository.updateCapitulo(event.capitulo)

                    } catch (e: Exception) {
                        Log.e(Constantes.ERROR_OBTENER_FAV, e.message, e)
                        _error.send(e.message ?: Constantes.ERROR)
                    }
                }

            }
            is MostrarSeriesFavContract.Event.updateSerie -> {
                viewModelScope.launch {
                    try {
                        serieRepository.updateSerie(event.serie)
                    } catch (e: Exception) {
                        Log.e(Constantes.ERROR_OBTENER_FAV, e.message, e)
                        _error.send(e.message ?: Constantes.ERROR)
                    }
                }
            }
        }
    }


    //CONTEXTBAR Y MULTISELECT
    fun seleccionaCapitulo(capitulo: Capitulo) {
        if (isSelected(capitulo)) {
            listaCapitulosSelected.remove(capitulo)
        } else {
            listaCapitulosSelected.add(capitulo)
        }
    }

    fun isSelected(capitulo: Capitulo): Boolean {
        return listaCapitulosSelected.contains(capitulo)
    }

    fun getLista() = listaCapitulosSelected.toList()

    fun clearList() = listaCapitulosSelected.clear()
}