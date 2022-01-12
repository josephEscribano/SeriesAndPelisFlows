package com.example.seriesandpelisjoseph.data.sources.remote

import com.example.seriesandpelisjoseph.data.model.BaseApiResponse
import com.example.seriesandpelisjoseph.data.sources.remote.ApiInterface.MultiMediaService
import com.example.seriesandpelisjoseph.data.sources.remote.ApiInterface.SerieService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val multiMediaService: MultiMediaService,
                                private val serieService: SerieService) : BaseApiResponse() {

    suspend fun getMovie(titulo:String,pagina:Int) = safeApiCall { multiMediaService.getMovie(titulo,pagina) }

    suspend fun getAll(titulo:String,region:String) = safeApiCall { multiMediaService.getAll(titulo,region) }

    suspend fun getPopularMovies() = safeApiCall { multiMediaService.getPopularMovie() }

    suspend fun getPopularSeries() = safeApiCall { multiMediaService.getPopularSeries() }

    suspend fun getSerie(tvId:Int) = safeApiCall {  serieService.getSerie(tvId)}
}