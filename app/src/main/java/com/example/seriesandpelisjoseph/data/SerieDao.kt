package com.example.seriesandpelisjoseph.data

import androidx.room.*
import com.example.seriesandpelisjoseph.data.model.entity.CapituloEntity
import com.example.seriesandpelisjoseph.data.model.entity.SerieEntity
import com.example.seriesandpelisjoseph.data.model.entity.SeriesWithTemporadas
import com.example.seriesandpelisjoseph.data.model.entity.TemporadaEntity

@Dao
interface SerieDao {

    @Transaction
    @Query("SELECT * FROM series")
    suspend fun getSeries(): List<SeriesWithTemporadas>

    @Query("SELECT * FROM CAPITULOS where temporadaId = (select idTemporada from temporadas where idApi = :id) ")
    suspend fun getCapitulos(id: Int): List<CapituloEntity>

    @Query("SELECT count(*) from series where idApi = :id")
    suspend fun repetidosSeries(id: Int): Int

    @Transaction
    @Query("SELECT  * FROM series where idSerie = :id")
    suspend fun getSerie(id: Int): SeriesWithTemporadas

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSerie(serie: SerieEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTemporada(temporada: TemporadaEntity?): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertListCapitulos(temporadas: List<CapituloEntity>?)

    @Transaction
    suspend fun insertSerieWithTemporadasAndCapitulos(seriesWithTemporadas: SeriesWithTemporadas) {
        var temporadaId: Int
        val serieId: Int = insertSerie(seriesWithTemporadas.serie).toInt()

        seriesWithTemporadas.temporadas?.apply {
            forEach { it.temporada?.serieId = serieId }
            seriesWithTemporadas.temporadas.map { it.temporada }.let {
                forEach {
                    temporadaId = insertTemporada(it.temporada).toInt()
                    it.capitulos?.forEach { capituloEntity ->
                        capituloEntity.temporadaId = temporadaId
                    }
                    insertListCapitulos(it.capitulos)
                }
            }
        }


    }


    suspend fun updateCapitulos(listCapituloEntity: List<CapituloEntity>) {
        listCapituloEntity.map { updateCapitulo(it) }
    }

    @Update
    suspend fun updateCapitulo(capituloEntity: CapituloEntity)

    @Delete
    suspend fun deleteSerie(serie: SerieEntity)

    @Query("delete from temporadas where serieId = :id")
    suspend fun deleteTemporada(id: Int)

    @Query("delete from capitulos where temporadaId = :id")
    suspend fun deleteCapitulo(id: Int?)

    @Transaction
    suspend fun deleteSerieTodo(seriesWithTemporadas: SeriesWithTemporadas) {
        seriesWithTemporadas.temporadas?.map { deleteCapitulo(it.temporada?.idTemporada) }
        deleteTemporada(seriesWithTemporadas.serie.idSerie)
        deleteSerie(seriesWithTemporadas.serie)
    }

}