package com.example.seriesandpelisjoseph.data.sources.remote.ApiInterface

import com.example.seriesandpelisjoseph.data.pojos.modeloSeries.SeriePojo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SerieService {

    @GET("tv/{id}")
    suspend fun getSerie(@Path("id") tvId:Int) : Response<SeriePojo>
}