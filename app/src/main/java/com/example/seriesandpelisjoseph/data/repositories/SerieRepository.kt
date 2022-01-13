package com.example.seriesandpelisjoseph.data.repositories

import com.example.seriesandpelisjoseph.data.sources.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class SerieRepository @Inject constructor(private val remoteDataSource: RemoteDataSource){

    suspend fun getSerie(tvId:Int) = withContext(Dispatchers.IO){remoteDataSource.getSerie(tvId)}
}