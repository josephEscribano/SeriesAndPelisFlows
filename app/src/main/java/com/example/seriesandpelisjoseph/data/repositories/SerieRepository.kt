package com.example.seriesandpelisjoseph.data.repositories

import com.example.seriesandpelisjoseph.data.model.entity.CapituloEntity
import com.example.seriesandpelisjoseph.data.model.entity.SeriesWithTemporadas
import com.example.seriesandpelisjoseph.data.model.toCapitulo
import com.example.seriesandpelisjoseph.data.model.toMultimedia
import com.example.seriesandpelisjoseph.data.model.toSerie
import com.example.seriesandpelisjoseph.data.model.toSerieWithTemporadas
import com.example.seriesandpelisjoseph.data.sources.remote.LocalDataSource
import com.example.seriesandpelisjoseph.data.sources.remote.RemoteDataSource
import com.example.seriesandpelisjoseph.domain.Capitulo
import com.example.seriesandpelisjoseph.domain.MultiMedia
import com.example.seriesandpelisjoseph.domain.Serie
import com.example.seriesandpelisjoseph.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class SerieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    suspend fun getSerie(tvId: Int) : Flow<NetworkResult<Serie>>{
        return flow {
            emit(NetworkResult.Loading())

            emit(remoteDataSource.getSerie(tvId))
        }.flowOn(Dispatchers.IO)
    }


    fun getCapitulos(tvId: Int, seasonNumber: Int) : Flow<NetworkResult<List<Capitulo>>>{
        return flow {
            emit(NetworkResult.Loading())
            emit(remoteDataSource.getCapitulos(tvId, seasonNumber))
        }.flowOn(Dispatchers.IO)
    }


    fun getSeries(): Flow<List<MultiMedia>> {
        return localDataSource.getSeries().map { it.map { seriesWithTemporadas -> seriesWithTemporadas.toMultimedia()
        } }
    }

    fun getSerieRoom(id: Int): Flow<Serie>{
        return localDataSource.getSerie(id).map { it.toSerie() }
    }

    suspend fun insertSerie(serie: Serie) =
        withContext(Dispatchers.IO) {
            localDataSource.insertSerie(serie.toSerieWithTemporadas())
        }

    suspend fun deleteSerie(serie: Serie) =
        withContext(Dispatchers.IO) {
            localDataSource.deleteSerie(serie.toSerieWithTemporadas())
        }

    fun repetidoSerie(id: Int): Flow<Int> = localDataSource.repetidoSerie(id)



    suspend fun updateCapitulo(list: List<CapituloEntity>) = withContext(Dispatchers.IO) {
        localDataSource.updateCapitulo(list)
    }

    fun getCapitulosRoom(id: Int) :  Flow<List<Capitulo>> =
        localDataSource.getCapitulos(id).map { it.map { capituloEntity -> capituloEntity.toCapitulo() } }

}