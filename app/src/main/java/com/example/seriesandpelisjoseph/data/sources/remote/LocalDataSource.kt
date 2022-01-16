package com.example.seriesandpelisjoseph.data.sources.remote

import com.example.seriesandpelisjoseph.data.MovieDao
import com.example.seriesandpelisjoseph.data.MultimediaDao
import com.example.seriesandpelisjoseph.data.model.entity.MovieWithActores
import com.example.seriesandpelisjoseph.data.model.entity.SeriesWithTemporadas
import javax.inject.Inject

class LocalDataSource@Inject constructor(private val multimediaDao: MultimediaDao
                                        ,private val movieDao: MovieDao) {
    //SERIE
    suspend fun getSeries() : List<SeriesWithTemporadas> = multimediaDao.getSeries()
    suspend fun insertSerie(seriesWithTemporadas: SeriesWithTemporadas) = multimediaDao.insertSerieWithTemporadasAndCapitulos(seriesWithTemporadas)


    //MOVIE
    suspend fun getMovies() : List<MovieWithActores> = movieDao.getMovies()
    suspend fun insertMovie(movieWithActores: MovieWithActores)  = movieDao.insertMovieWithActores(movieWithActores)

}