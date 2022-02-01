package com.example.seriesandpelisjoseph.data.repositories

import com.example.seriesandpelisjoseph.data.model.entity.MovieEntity
import com.example.seriesandpelisjoseph.data.model.entity.MovieWithActores
import com.example.seriesandpelisjoseph.data.model.toMovieEntity
import com.example.seriesandpelisjoseph.data.model.toMovieWithActores
import com.example.seriesandpelisjoseph.data.model.toMultimedia
import com.example.seriesandpelisjoseph.data.sources.remote.LocalDataSource
import com.example.seriesandpelisjoseph.domain.Movie
import com.example.seriesandpelisjoseph.domain.MultiMedia
import com.example.seriesandpelisjoseph.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val localDataSource: LocalDataSource
) {
    fun getMovies(): Flow<List<MultiMedia>> {
        return localDataSource.getMovies().map { it.map { movieWithActores -> movieWithActores.toMultimedia() } }
            .flowOn(Dispatchers.IO)
    }




    suspend fun insertMovie(movie:Movie) = withContext(Dispatchers.IO) {
        localDataSource.insertMovie(movie.toMovieWithActores())
    }

    suspend fun deleteMovie(movie: Movie) = withContext(Dispatchers.IO) {
        localDataSource.deleteMovie(movie.toMovieEntity())
    }

    fun repetido(id: Int): Flow<Int> {
        return localDataSource.repetido(id).flowOn(Dispatchers.IO)
    }
}