package com.example.seriesandpelisjoseph.usecases

import com.example.seriesandpelisjoseph.data.model.toMultimedia
import com.example.seriesandpelisjoseph.data.repositories.MovieRepository
import javax.inject.Inject

class GetMovies @Inject constructor(private val movieRepository: MovieRepository) {
    suspend fun invoke() = movieRepository.getMovies().map { it.toMultimedia() }

}