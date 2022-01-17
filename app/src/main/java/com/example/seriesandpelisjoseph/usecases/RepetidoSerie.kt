package com.example.seriesandpelisjoseph.usecases

import com.example.seriesandpelisjoseph.data.repositories.SerieRepository
import javax.inject.Inject

class RepetidoSerie @Inject constructor(private val serieRepository: SerieRepository){

    suspend fun invoke(id:Int) : Int = serieRepository.repetidoSerie(id)

}