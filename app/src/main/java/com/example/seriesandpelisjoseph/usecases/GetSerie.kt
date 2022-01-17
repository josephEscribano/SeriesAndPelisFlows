package com.example.seriesandpelisjoseph.usecases

import com.example.seriesandpelisjoseph.data.model.toSerie
import com.example.seriesandpelisjoseph.data.repositories.SerieRepository
import com.example.seriesandpelisjoseph.domain.Serie
import javax.inject.Inject

class GetSerie @Inject constructor(private val serieRepository: SerieRepository) {
    suspend fun invoke(id: Int): Serie = serieRepository.getSerieRoom(id).toSerie()
}