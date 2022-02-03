package com.example.seriesandpelisjoseph.framework.main.listarSeriesFav

import com.example.seriesandpelisjoseph.domain.MultiMedia
import com.example.seriesandpelisjoseph.domain.Serie

interface ListarSeriesFavContract {

    sealed class Event {
        object getSeries : Event()
        data class deleteSerie(val serie: Serie) : Event()
        data class getSerie(val id: Int?) : Event()
    }

    data class StateListarSeriesFav(
        val multimedia: List<MultiMedia> = emptyList(),
        val serie: Serie? = null,
    )
}