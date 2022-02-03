package com.example.seriesandpelisjoseph.framework.main.mostrarSerieFavRoom

import com.example.seriesandpelisjoseph.domain.Capitulo
import com.example.seriesandpelisjoseph.domain.Serie

interface MostrarSeriesFavContract {

    sealed class Event{
        data class getSerie(val id: Int?) : Event()
        data class getCapitulo(val idTemporada: Int?) : Event()
        object updateCapitulo : Event()
    }

    data class StateMostrarSerieFav(
        val capitulos : List<Capitulo> = emptyList(),
        val serie: Serie? = null,
        val isLoading : Boolean = false,
        val error: String? = null
    )

}