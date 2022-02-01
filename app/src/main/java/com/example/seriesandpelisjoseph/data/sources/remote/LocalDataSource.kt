package com.example.seriesandpelisjoseph.data.sources.remote

import com.example.seriesandpelisjoseph.data.MovieDao
import com.example.seriesandpelisjoseph.data.SerieDao
import com.example.seriesandpelisjoseph.data.model.entity.CapituloEntity
import com.example.seriesandpelisjoseph.data.model.entity.MovieEntity
import com.example.seriesandpelisjoseph.data.model.entity.MovieWithActores
import com.example.seriesandpelisjoseph.data.model.entity.SeriesWithTemporadas
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val serieDao: SerieDao, private val movieDao: MovieDao
) {
    //SERIE
    fun getSeries(): Flow<List<SeriesWithTemporadas>> = serieDao.getSeries()
    suspend fun insertSerie(seriesWithTemporadas: SeriesWithTemporadas) =
        serieDao.insertSerieWithTemporadasAndCapitulos(seriesWithTemporadas)

     fun getSerie(id: Int): Flow<SeriesWithTemporadas> = serieDao.getSerie(id)
    suspend fun deleteSerie(seriesWithTemporadas: SeriesWithTemporadas) =
        serieDao.deleteSerieTodo(seriesWithTemporadas)

    suspend fun repetidoSerie(id: Int): Int = serieDao.repetidosSeries(id)
    suspend fun updateCapitulo(list: List<CapituloEntity>) = serieDao.updateCapitulos(list)
    suspend fun getCapitulos(id: Int) = serieDao.getCapitulos(id)

    //MOVIE
    fun repetido(id: Int): Flow<Int> = movieDao.repetidos(id)

    fun getMovies(): Flow<List<MovieWithActores>> = movieDao.getMovies()

    suspend fun insertMovie(movieWithActores: MovieWithActores) =
        movieDao.insertMovieWithActores(movieWithActores)

    suspend fun deleteMovie(movieEntity: MovieEntity) = movieDao.deleteMovie(movieEntity)

}