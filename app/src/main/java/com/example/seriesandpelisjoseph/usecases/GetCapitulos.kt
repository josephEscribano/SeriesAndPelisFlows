package com.example.seriesandpelisjoseph.usecases

import com.example.seriesandpelisjoseph.data.model.toCapitulo
import com.example.seriesandpelisjoseph.data.repositories.SerieRepository
import javax.inject.Inject

class GetCapitulos @Inject constructor(private val serieRepository: SerieRepository) {
//    suspend fun invoke(id: Int) = serieRepository.getCapitulosRoom(id).map { it.toCapitulo() }

}