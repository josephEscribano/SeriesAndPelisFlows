package com.example.seriesandpelisjoseph.data.repositories

import com.example.seriesandpelisjoseph.data.sources.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class MovieRepository @Inject constructor(private val remoteDataSource: RemoteDataSource){
    suspend fun getMovie(titulo:String,pagina:Int) = withContext(Dispatchers.IO){remoteDataSource.getMovie(titulo,pagina)}
}