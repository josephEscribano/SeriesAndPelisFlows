package com.example.seriesandpelisjoseph.data.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.seriesandpelisjoseph.data.model.toCachePelisEntity
import com.example.seriesandpelisjoseph.data.model.toMultimedia
import com.example.seriesandpelisjoseph.data.sources.remote.LocalDataSource
import com.example.seriesandpelisjoseph.data.sources.remote.RemoteDataSource
import com.example.seriesandpelisjoseph.domain.MultiMedia
import com.example.seriesandpelisjoseph.utils.InternetConnection
import com.example.seriesandpelisjoseph.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class MultimediaRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource) {

    suspend fun getAll(titulo: String, region: String) : Flow<NetworkResult<List<MultiMedia>>>{
        return flow {
            emit(NetworkResult.Loading())
            emit(remoteDataSource.getAll(titulo, region))
        }.flowOn(Dispatchers.IO)
    }


    suspend fun getPopularMovies() : Flow<NetworkResult<List<MultiMedia>>>{

        return flow {
            emit(NetworkResult.Loading())
            emit(remoteDataSource.getPopularMovies() )
        }.flowOn(Dispatchers.IO)
    }


    suspend fun getPopularSeries() : Flow<NetworkResult<List<MultiMedia>>>{

        return flow {
            emit(NetworkResult.Loading())
            emit(remoteDataSource.getPopularSeries() )
        }.flowOn(Dispatchers.IO)
    }


    //CACHE

    @RequiresApi(Build.VERSION_CODES.M)
    fun cachearPopulares(conexion:Boolean) : Flow<NetworkResult<List<MultiMedia>>>{
        return flow {
            emit(NetworkResult.Loading())
            emit(getCache())

            if (conexion){
                val populares = remoteDataSource.getPopularMovies()
                if (populares is NetworkResult.Succcess){
                    populares.data?.let {
                        localDataSource.setAllCache(it.map { multiMedia -> multiMedia.toCachePelisEntity() })
                    }
                }
                emit(populares)
            }

        }
    }
    suspend fun getCache(): NetworkResult<List<MultiMedia>>{
        return localDataSource.getCache().let {
            NetworkResult.Succcess(it.map { cachePelisEntity -> cachePelisEntity.toMultimedia() })
        }
    }

}