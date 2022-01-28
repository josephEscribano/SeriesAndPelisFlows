package com.example.seriesandpelisjoseph.usecases

import com.example.seriesandpelisjoseph.data.model.toCapituloEntity
import com.example.seriesandpelisjoseph.data.repositories.SerieRepository
import com.example.seriesandpelisjoseph.domain.Capitulo
import javax.inject.Inject

class UpdateCapitulo @Inject constructor(private val serieRepository: SerieRepository) {
    suspend fun invoke(list: List<Capitulo>) =
        serieRepository.updateCapitulo(list.map { it.toCapituloEntity() })

}