package com.example.seriesandpelisjoseph.data.sources.remote

import com.example.seriesandpelisjoseph.data.model.BaseApiResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val pelisService: PelisService) : BaseApiResponse() {

    suspend fun getMovie(titulo:String,pagina:Int) = safeApiCall { pelisService.getMovie(titulo,pagina) }
}