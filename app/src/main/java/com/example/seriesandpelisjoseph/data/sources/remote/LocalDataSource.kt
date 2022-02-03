package com.example.seriesandpelisjoseph.data.sources.remote

import com.example.seriesandpelisjoseph.data.MovieDao
import com.example.seriesandpelisjoseph.data.SerieDao
import com.example.seriesandpelisjoseph.data.model.entity.*
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

    fun repetidoSerie(id: Int): Flow<Int> = serieDao.repetidosSeries(id)

    suspend fun updateCapitulos(list: List<CapituloEntity>) = serieDao.updateCapitulos(list)

    fun getCapitulos(id: Int): Flow<List<CapituloEntity>> = serieDao.getCapitulos(id)

    suspend fun updateCapitulo(capituloEntity: CapituloEntity) =
        serieDao.updateCapitulo(capituloEntity)

    suspend fun updateSerie(serieEntity: SerieEntity) = serieDao.updateSerie(serieEntity)

    //MOVIE
    fun repetido(id: Int): Flow<Int> = movieDao.repetidos(id)

    fun getMovies(): Flow<List<MovieWithActores>> = movieDao.getMovies()

    suspend fun insertMovie(movieWithActores: MovieWithActores) =
        movieDao.insertMovieWithActores(movieWithActores)

    suspend fun deleteMovie(movieEntity: MovieEntity) = movieDao.deleteMovie(movieEntity)

    suspend fun updateMovie(movieEntity: MovieEntity) = movieDao.updateMovie(movieEntity)

    //CACHE
    suspend fun getCache(): List<CachePelisEntity> = movieDao.getCache()

    suspend fun setAllCache(cachePelisEntity: List<CachePelisEntity>) =
        movieDao.setAllCache(cachePelisEntity)


}