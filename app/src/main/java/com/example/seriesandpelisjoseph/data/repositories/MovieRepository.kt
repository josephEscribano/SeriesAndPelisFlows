package com.example.seriesandpelisjoseph.data.repositories

import com.example.seriesandpelisjoseph.data.model.entity.MovieWithActores
import com.example.seriesandpelisjoseph.data.sources.remote.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepository@Inject constructor(
    private val localDataSource: LocalDataSource
) {
    suspend fun getMovies() : List<MovieWithActores> = withContext(Dispatchers.IO){
        localDataSource.getMovies()
    }

    suspend fun insertMovie(movieWithActores: MovieWithActores)  =  withContext(Dispatchers.IO){
        localDataSource.insertMovie(movieWithActores)
    }
}