package com.example.seriesandpelisjoseph.data.sources.remote

import com.example.seriesandpelisjoseph.data.pruebaModelo.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SeriesService {

    @GET("movie/{id}")
    suspend fun getMovie(@Path("id") id:Int): Response<Movie>

}