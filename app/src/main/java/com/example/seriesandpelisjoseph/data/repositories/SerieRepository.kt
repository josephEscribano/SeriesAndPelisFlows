package com.example.seriesandpelisjoseph.data.repositories

import com.example.seriesandpelisjoseph.data.model.entity.SeriesWithTemporadas
import com.example.seriesandpelisjoseph.data.sources.remote.LocalDataSource
import com.example.seriesandpelisjoseph.data.sources.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class SerieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    suspend fun getSerie(tvId: Int) =
        withContext(Dispatchers.IO) { remoteDataSource.getSerie(tvId) }

    suspend fun getCapitulos(tvId: Int, seasonNumber: Int) =
        withContext(Dispatchers.IO) { remoteDataSource.getCapitulos(tvId, seasonNumber) }

    suspend fun getSeries(): List<SeriesWithTemporadas> = withContext(Dispatchers.IO) {
        localDataSource.getSeries()
    }

    suspend fun getSerieRoom(id: Int): SeriesWithTemporadas = withContext(Dispatchers.IO) {
        localDataSource.getSerie(id)
    }

    suspend fun insertSerie(seriesWithTemporadas: SeriesWithTemporadas) =
        withContext(Dispatchers.IO) {
            localDataSource.insertSerie(seriesWithTemporadas)
        }

    suspend fun deleteSerie(seriesWithTemporadas: SeriesWithTemporadas) =
        withContext(Dispatchers.IO) {
            localDataSource.deleteSerie(seriesWithTemporadas)
        }

    suspend fun repetidoSerie(id:Int) : Int = withContext(Dispatchers.IO) {
        localDataSource.repetidoSerie(id)
    }
}