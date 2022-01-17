package com.example.seriesandpelisjoseph.usecases

import com.example.seriesandpelisjoseph.data.model.toMovieWithActores
import com.example.seriesandpelisjoseph.data.repositories.MovieRepository
import com.example.seriesandpelisjoseph.domain.Movie
import javax.inject.Inject

class InsertMovie @Inject constructor(private val movieRepository: MovieRepository) {

    suspend fun invoke(movie: Movie) = movieRepository.insertMovie(movie.toMovieWithActores())
}