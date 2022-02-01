package com.example.seriesandpelisjoseph.usecases

import com.example.seriesandpelisjoseph.data.repositories.MovieRepository
import javax.inject.Inject

class Repetido @Inject constructor(private val movieRepository: MovieRepository) {

//    suspend fun invoke(id: Int): Int = movieRepository.repetido(id)
}