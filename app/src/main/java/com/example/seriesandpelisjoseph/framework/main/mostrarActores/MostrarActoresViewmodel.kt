package com.example.seriesandpelisjoseph.framework.main.mostrarActores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesandpelisjoseph.data.repositories.ActorRepository
import com.example.seriesandpelisjoseph.framework.main.mostrarActores.MostrarActoresContract.StateMostrarActores
import com.example.seriesandpelisjoseph.utils.Constantes
import com.example.seriesandpelisjoseph.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MostrarActoresViewmodel @Inject constructor(private val actorRepository: ActorRepository) :
    ViewModel() {

    private val _actorState: MutableStateFlow<StateMostrarActores> by lazy {
        MutableStateFlow(StateMostrarActores())
    }
    val actorData: StateFlow<StateMostrarActores> = _actorState

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    fun handleEvent(event: MostrarActoresContract.Event) {
        when (event) {
            is MostrarActoresContract.Event.getActor -> {
                viewModelScope.launch {
                    actorRepository.getActor(event.actorId).catch(action = { cause ->
                        _error.send(cause.message ?: Constantes.ERROR)
                    }).collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                _actorState.update { it.copy(error = result.message) }
                            }
                            is NetworkResult.Loading -> _actorState.update { it.copy(isLoading = true) }
                            is NetworkResult.Succcess -> _actorState.update {
                                it.copy(
                                    actor = result.data, isLoading = false
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}