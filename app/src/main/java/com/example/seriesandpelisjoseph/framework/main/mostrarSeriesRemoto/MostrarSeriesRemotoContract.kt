package com.example.seriesandpelisjoseph.framework.main.mostrarSeriesRemoto

import com.example.seriesandpelisjoseph.domain.Capitulo
import com.example.seriesandpelisjoseph.domain.Serie

interface MostrarSeriesRemotoContract {
    sealed class Event {
        data class getSerie(val tvId: Int) : Event()
        data class getCapitulos(val tvId: Int, val seasonNumber: Int?) : Event()
        data class insertSerie(val idserie: Int) : Event()
        data class repetidoSerie(val id: Int) : Event()

    }

    data class StateMostrarSeriesRemoto(
        val capitulos: List<Capitulo> = emptyList(),
        val serie: Serie? = null,
        val repetido: Int = -1,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}