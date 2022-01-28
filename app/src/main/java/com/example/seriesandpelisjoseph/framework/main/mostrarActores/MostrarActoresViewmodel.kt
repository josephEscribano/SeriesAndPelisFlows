package com.example.seriesandpelisjoseph.framework.main.mostrarActores

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesandpelisjoseph.data.repositories.ActorRepository
import com.example.seriesandpelisjoseph.domain.Actor
import com.example.seriesandpelisjoseph.utils.Constantes
import com.example.seriesandpelisjoseph.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MostrarActoresViewmodel @Inject constructor(private val actorRepository: ActorRepository) :
    ViewModel() {
    private val _actorData = MutableLiveData<Actor?>()
    val actorData: LiveData<Actor?> get() = _actorData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getActor(actorId: Int) {
        viewModelScope.launch {
            val result = actorRepository.getActor(actorId)

            when (result) {
                is NetworkResult.Error -> _error.value = result.message ?: Constantes.ERROR
                is NetworkResult.Succcess -> _actorData.value = result.data
            }
        }
    }
}