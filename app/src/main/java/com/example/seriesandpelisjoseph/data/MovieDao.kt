package com.example.seriesandpelisjoseph.data

import androidx.room.*
import com.example.seriesandpelisjoseph.data.model.entity.*

@Dao
interface MovieDao {

    @Transaction
    @Query("SELECT * FROM movies")
    suspend fun getMovies() : List<MovieWithActores>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movieEntity: MovieEntity) : Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertListActores(actores: List<ActorEntity>)


    @Transaction
    suspend fun insertMovieWithActores(movieWithActores: MovieWithActores){
        movieWithActores.movie.idMovie = insertMovie(movieWithActores.movie).toInt()

        movieWithActores.listActores?.apply {
            forEach { it.idActuaSerie = movieWithActores.movie.idMovie }
            insertListActores(this)
        }
    }





}