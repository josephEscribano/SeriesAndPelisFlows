package com.example.seriesandpelisjoseph.framework.main.listarMostrarMoviesFav

import com.example.seriesandpelisjoseph.domain.Movie
import com.example.seriesandpelisjoseph.domain.MultiMedia

interface ListarMostrarMoviesContract {
    sealed class Event{
        object getMovies: Event()
        data class insertMovie(val movie: Movie) : Event()
        data class deleteMovie(val movie: Movie?) : Event()
        data class repetido(val id: Int) : Event()
    }

    data class StateLMMovies(
        val multimedia: List<MultiMedia> = emptyList(),
        val respetido: Int = -1,
        val isLoading: Boolean = false,
        val error: String? = null,
    )

}