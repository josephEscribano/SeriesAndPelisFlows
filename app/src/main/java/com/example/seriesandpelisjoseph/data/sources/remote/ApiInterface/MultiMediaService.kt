package com.example.seriesandpelisjoseph.data.sources.remote.ApiInterface

import com.example.seriesandpelisjoseph.data.pojos.multSearch.MultiMediaPojo
import com.example.seriesandpelisjoseph.data.pojos.popularMovie.PopularMoviePojo
import com.example.seriesandpelisjoseph.data.pojos.popularSeries.PopularSeriePojo
import com.example.seriesandpelisjoseph.utils.Constantes

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MultiMediaService {


    @GET(Constantes.MULTI_PATH)
    suspend fun getAll(
        @Query(Constantes.QUERY) titulo: String,
        @Query(Constantes.REGION) region: String
    ): Response<MultiMediaPojo>

    @GET(Constantes.MOVIE_POPULAR_PATH)
    suspend fun getPopularMovie(): Response<PopularMoviePojo>

    @GET(Constantes.SERIE_POPULAR_PATH)
    suspend fun getPopularSeries(): Response<PopularSeriePojo>

}