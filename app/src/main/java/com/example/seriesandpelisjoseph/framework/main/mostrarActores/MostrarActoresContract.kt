package com.example.seriesandpelisjoseph.framework.main.mostrarActores

import com.example.seriesandpelisjoseph.domain.Actor

interface MostrarActoresContract {

    sealed class Event {
        data class getActor(val actorId: Int) : Event()
    }

    data class StateMostrarActores(
        val actor: Actor? = null,
        val isLoading: Boolean = false,
        val error: String? = null,
    )
}