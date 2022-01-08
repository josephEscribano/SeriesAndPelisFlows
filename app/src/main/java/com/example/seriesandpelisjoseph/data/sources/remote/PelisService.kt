package com.example.seriesandpelisjoseph.data.sources.remote

import com.example.seriesandpelisjoseph.data.modeloPeliculas.MoviePojo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PelisService {

    @GET("search/movie")
    suspend fun getMovie(@Query("query") titulo:String,@Query("page") pagina:Int): Response<MoviePojo>

}