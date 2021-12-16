package com.example.seriesandpelisjoseph.data.sources.remote

import com.example.seriesandpelisjoseph.data.model.BaseApiResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val seriesService: SeriesService) : BaseApiResponse() {

    suspend fun getMovie(id:Int) = safeApiCall { seriesService.getMovie(id) }
}