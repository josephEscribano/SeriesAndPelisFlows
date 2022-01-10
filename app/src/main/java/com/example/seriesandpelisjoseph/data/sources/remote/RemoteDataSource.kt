package com.example.seriesandpelisjoseph.data.sources.remote

import com.example.seriesandpelisjoseph.data.model.BaseApiResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val multiMediaService: MultiMediaService) : BaseApiResponse() {

    suspend fun getMovie(titulo:String,pagina:Int) = safeApiCall { multiMediaService.getMovie(titulo,pagina) }

    suspend fun getAll(titulo:String) = safeApiCall { multiMediaService.getAll(titulo) }

    suspend fun getPopularMovies() = safeApiCall { multiMediaService.getPopularMovie() }

    suspend fun getPopularSeries() = safeApiCall { multiMediaService.getPopularSeries() }
}