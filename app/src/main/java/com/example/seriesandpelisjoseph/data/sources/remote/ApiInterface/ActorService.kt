package com.example.seriesandpelisjoseph.data.sources.remote.ApiInterface

import com.example.seriesandpelisjoseph.data.pojos.modeloActor.ActorPojo
import com.example.seriesandpelisjoseph.utils.Constantes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ActorService {

    @GET(Constantes.ACTOR_PATH)
    suspend fun getActor(@Path(Constantes.ID_PARAMETER) actorId: Int): Response<ActorPojo>

}