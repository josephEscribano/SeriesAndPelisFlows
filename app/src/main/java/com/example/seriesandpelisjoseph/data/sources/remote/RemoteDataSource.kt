package com.example.seriesandpelisjoseph.data.sources.remote


import com.example.seriesandpelisjoseph.data.model.*
import com.example.seriesandpelisjoseph.data.pojos.modeloActor.ActorPojo
import com.example.seriesandpelisjoseph.data.pojos.modeloSeries.SeriePojo
import com.example.seriesandpelisjoseph.data.pojos.modeloTemporadas.Episode
import com.example.seriesandpelisjoseph.data.sources.remote.ApiInterface.ActorService
import com.example.seriesandpelisjoseph.data.sources.remote.ApiInterface.MultiMediaService
import com.example.seriesandpelisjoseph.data.sources.remote.ApiInterface.SerieService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val multiMediaService: MultiMediaService,
                                private val serieService: SerieService,
                                private val actorService: ActorService) : BaseApiResponse() {


    suspend fun getAll(titulo:String,region:String)  = safeApiCall(apicall = {multiMediaService.getAll(titulo, region)}, transform = {
        it.resultMultimediaPojos.map { it.toMultimedia() }
    })


    suspend fun getPopularMovies() = safeApiCall (apicall = { multiMediaService.getPopularMovie() }, transform = {
        it.resultPopularMoviePojos.map { resultPopularMoviePojo ->  resultPopularMoviePojo.toMultimedia() }
    })

    suspend fun getPopularSeries() = safeApiCall ({ multiMediaService.getPopularSeries() }, transform = {
        it.resultPopularSeriePojos.map { resultPopularSeriePojo ->  resultPopularSeriePojo.toSerieMultimedia() }
    })

    suspend fun getSerie(tvId:Int) = safeApiCall (apicall = {  serieService.getSerie(tvId)}, transform = SeriePojo::toSerie)

    suspend fun getActor(actorId:Int) = safeApiCall (apicall = { actorService.getActor(actorId) }, transform = ActorPojo::toActor)

    suspend fun getCapitulos(tvId: Int,seasonNumber:Int) = safeApiCall (apicall = { serieService.getCapitulos(tvId, seasonNumber) },transform = {
        it.episodes.map { it.toCapitulo() }
    })
}