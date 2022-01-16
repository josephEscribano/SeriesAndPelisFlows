package com.example.seriesandpelisjoseph.data.model.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TemporadasWithCapitulos(
    @Embedded val temporada: TemporadaEntity?,
    @Relation(
        parentColumn = "idTemporada",
        entityColumn = "temporadaId"
    )
    val capitulos: List<CapituloEntity>?

)
