package com.example.seriesandpelisjoseph.usecases

import com.example.seriesandpelisjoseph.data.repositories.MultimediaRepository
import javax.inject.Inject

class SearchMovies@Inject constructor(private val multimediaRepository: MultimediaRepository) {
    suspend fun invoke(titulo: String,pagina:Int) = multimediaRepository.getMovie(titulo,pagina)
}