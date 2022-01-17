package com.example.seriesandpelisjoseph.data.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "actors",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["idMovie"],
            childColumns = ["idActuaMovie"]
        ), ForeignKey(
            entity = SerieEntity::class,
            parentColumns = ["idSerie"],
            childColumns = ["idActuaSerie"]
        )
    ]
)
data class ActorEntity(
    @PrimaryKey(autoGenerate = true)
    val idActor: Int,
    val idApi: Int,
    var idActuaSerie: Int?,
    val idActuaMovie: Int?,
    val imagen: String?,
    val nombre: String,
    val biografia: String,
    val nacimiento: String,
)
