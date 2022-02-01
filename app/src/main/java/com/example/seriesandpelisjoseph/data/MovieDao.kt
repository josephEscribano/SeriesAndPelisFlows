package com.example.seriesandpelisjoseph.data

import androidx.room.*
import com.example.seriesandpelisjoseph.data.model.entity.ActorEntity
import com.example.seriesandpelisjoseph.data.model.entity.MovieEntity
import com.example.seriesandpelisjoseph.data.model.entity.MovieWithActores
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Transaction
    @Query("SELECT * FROM movies")
    fun getMovies(): Flow<List<MovieWithActores>>

    @Query("SELECT count(*) from movies where idApi = :id")
    fun repetidos(id: Int): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movieEntity: MovieEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertListActores(actores: List<ActorEntity>)


    @Transaction
    suspend fun insertMovieWithActores(movieWithActores: MovieWithActores) {
        movieWithActores.movie.idMovie = insertMovie(movieWithActores.movie).toInt()

        movieWithActores.listActores?.apply {
            forEach { it.idActuaSerie = movieWithActores.movie.idMovie }
            insertListActores(this)
        }
    }

    @Delete
    suspend fun deleteMovie(movieEntity: MovieEntity)


}