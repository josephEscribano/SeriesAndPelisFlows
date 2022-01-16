package com.example.seriesandpelisjoseph.data.model.entity

import androidx.room.Embedded
import androidx.room.Relation

data class MovieWithActores(
    @Embedded val movie: MovieEntity,
    @Relation(
        parentColumn = "idMovie",
        entityColumn = "idActuaMovie"
    )

    val listActores: List<ActorEntity>?
)
