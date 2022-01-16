package com.example.seriesandpelisjoseph.data

import androidx.room.*
import com.example.seriesandpelisjoseph.data.model.entity.CapituloEntity
import com.example.seriesandpelisjoseph.data.model.entity.SerieEntity
import com.example.seriesandpelisjoseph.data.model.entity.SeriesWithTemporadas
import com.example.seriesandpelisjoseph.data.model.entity.TemporadaEntity

@Dao
interface MultimediaDao {

    @Transaction
    @Query("SELECT * FROM series")
    suspend fun getSeries(): List<SeriesWithTemporadas>

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


}