package com.example.seriesandpelisjoseph.framework.main.mostrarSerieFavRoom

import com.example.seriesandpelisjoseph.domain.Capitulo
import com.example.seriesandpelisjoseph.domain.Serie

interface MostrarSeriesFavContract {

    sealed class Event {
        data class getSerie(val id: Int?) : Event()
        data class getCapitulos(val idTemporada: Int?) : Event()
        object updateCapitulos : Event()
        data class updateCapitulo(val capitulo: Capitulo) : Event()
        data class updateSerie(val serie: Serie) : Event()

    }

    data class StateMostrarSerieFav(
        val capitulos: List<Capitulo> = emptyList(),
        val serie: Serie? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )

}