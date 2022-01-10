package com.example.seriesandpelisjoseph.data.sources.remote

import com.example.seriesandpelisjoseph.data.pojos.modeloPeliculas.MoviePojo
import com.example.seriesandpelisjoseph.data.pojos.multSearch.MultiMediaPojo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MultiMediaService {

    @GET("search/movie")
    suspend fun getMovie(@Query("query") titulo:String,@Query("page") pagina:Int): Response<MultiMediaPojo>

    @GET("search/multi")
    suspend fun getAll(@Query("query") titulo:String) : Response<MultiMediaPojo>

    @GET("movie/popular")
    suspend fun getPopularMovie() : Response<MultiMediaPojo>

    @GET("movie/popular")
    suspend fun getPopularSeries() : Response<MultiMediaPojo>

}