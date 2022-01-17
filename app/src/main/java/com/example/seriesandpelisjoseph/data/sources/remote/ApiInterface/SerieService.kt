package com.example.seriesandpelisjoseph.data.sources.remote.ApiInterface

import com.example.seriesandpelisjoseph.data.pojos.modeloSeries.SeriePojo
import com.example.seriesandpelisjoseph.data.pojos.modeloTemporadas.SeasonPojo
import com.example.seriesandpelisjoseph.utils.Constantes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SerieService {

    @GET(Constantes.SERIE_PATH)
    suspend fun getSerie(@Path(Constantes.ID_PARAMETER) tvId: Int): Response<SeriePojo>

    @GET(Constantes.SEASON_PATH)
    suspend fun getCapitulos(
        @Path(Constantes.TVID_PARAMETER) tvId: Int,
        @Path(Constantes.SEASONNUMBER_PARAMETER) seasonNumber: Int
    ): Response<SeasonPojo>
}