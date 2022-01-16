package com.example.seriesandpelisjoseph.data.model.entity

import androidx.room.Embedded
import androidx.room.Relation

data class SeriesWithTemporadas(
    @Embedded val serie: SerieEntity,
    @Relation(
        entity = TemporadaEntity::class,
        parentColumn = "idSerie",
        entityColumn = "serieId"
    )
    val temporadas: List<TemporadasWithCapitulos>?
)
