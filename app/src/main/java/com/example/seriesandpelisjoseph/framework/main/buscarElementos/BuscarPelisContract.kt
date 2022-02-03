package com.example.seriesandpelisjoseph.framework.main.buscarElementos

import com.example.seriesandpelisjoseph.domain.MultiMedia

interface BuscarPelisContract {

    sealed class Event {
        object getPopularMovies : Event()
        object getPopularSeries : Event()
        data class getMultiSearch(val titulo: String, val region: String) : Event()
        data class cachearPopulares(val conexion: Boolean) : Event()
    }

    data class StateBuscarPelis(
        val multimedia: List<MultiMedia> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )
}