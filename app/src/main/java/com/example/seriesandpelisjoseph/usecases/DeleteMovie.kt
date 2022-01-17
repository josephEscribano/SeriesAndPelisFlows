package com.example.seriesandpelisjoseph.usecases

import com.example.seriesandpelisjoseph.data.model.toMovieEntity
import com.example.seriesandpelisjoseph.data.repositories.MovieRepository
import com.example.seriesandpelisjoseph.domain.Movie
import javax.inject.Inject

class DeleteMovie @Inject constructor(private val movieRepository: MovieRepository) {

    suspend fun invoke(movie: Movie) = movieRepository.deleteMovie(movie.toMovieEntity())
}