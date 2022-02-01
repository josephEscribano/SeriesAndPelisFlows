package com.example.seriesandpelisjoseph.usecases

import com.example.seriesandpelisjoseph.data.model.toMultimedia
import com.example.seriesandpelisjoseph.data.repositories.SerieRepository
import javax.inject.Inject

class GetSeries @Inject constructor(private val serieRepository: SerieRepository) {
//    suspend fun invoke() = serieRepository.getSeries().map { it.toMultimedia() }
}