package com.example.seriesandpelisjoseph.data.repositories

import com.example.seriesandpelisjoseph.data.sources.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.Query
import javax.inject.Inject

@ActivityRetainedScoped
class MultimediaRepository @Inject constructor(private val remoteDataSource: RemoteDataSource){
    suspend fun getMovie(titulo:String,pagina:Int) = withContext(Dispatchers.IO){remoteDataSource.getMovie(titulo,pagina)}

    suspend fun getAll(titulo:String,region:String) = withContext(Dispatchers.IO){remoteDataSource.getAll(titulo,region)}

    suspend fun getPopularMovies() = withContext(Dispatchers.IO){remoteDataSource.getPopularMovies() }

    suspend fun getPopularSeries() = withContext(Dispatchers.IO){remoteDataSource.getPopularSeries()}


}