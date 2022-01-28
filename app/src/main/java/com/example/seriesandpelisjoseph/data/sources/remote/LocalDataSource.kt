package com.example.seriesandpelisjoseph.data.sources.remote

import com.example.seriesandpelisjoseph.data.MovieDao
import com.example.seriesandpelisjoseph.data.SerieDao
import com.example.seriesandpelisjoseph.data.model.entity.CapituloEntity
import com.example.seriesandpelisjoseph.data.model.entity.MovieEntity
import com.example.seriesandpelisjoseph.data.model.entity.MovieWithActores
import com.example.seriesandpelisjoseph.data.model.entity.SeriesWithTemporadas
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val serieDao: SerieDao, private val movieDao: MovieDao
) {
    //SERIE
    suspend fun getSeries(): List<SeriesWithTemporadas> = serieDao.getSeries()
    suspend fun insertSerie(seriesWithTemporadas: SeriesWithTemporadas) =
        serieDao.insertSerieWithTemporadasAndCapitulos(seriesWithTemporadas)

    suspend fun getSerie(id: Int): SeriesWithTemporadas = serieDao.getSerie(id)
    suspend fun deleteSerie(seriesWithTemporadas: SeriesWithTemporadas) =
        serieDao.deleteSerieTodo(seriesWithTemporadas)

    suspend fun repetidoSerie(id: Int): Int = serieDao.repetidosSeries(id)
    suspend fun updateCapitulo(list: List<CapituloEntity>) = serieDao.updateCapitulos(list)
    suspend fun getCapitulos(id: Int) = serieDao.getCapitulos(id)

    //MOVIE
    suspend fun repetido(id: Int): Int = movieDao.repetidos(id)
    suspend fun getMovies(): List<MovieWithActores> = movieDao.getMovies()
    suspend fun insertMovie(movieWithActores: MovieWithActores) =
        movieDao.insertMovieWithActores(movieWithActores)

    suspend fun deleteMovie(movieEntity: MovieEntity) = movieDao.deleteMovie(movieEntity)

}