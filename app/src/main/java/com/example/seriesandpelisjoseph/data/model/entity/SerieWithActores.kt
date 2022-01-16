package com.example.seriesandpelisjoseph.data.model.entity

import androidx.room.Embedded
import androidx.room.Relation

data class SerieWithActores(
    @Embedded val serie:SerieEntity,
    @Relation(
        parentColumn = "idSerie",
        entityColumn = "idActuaSerie"
    )

    val listActores: List<ActorEntity>
)
