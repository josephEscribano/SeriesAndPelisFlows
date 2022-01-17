package com.example.seriesandpelisjoseph.data.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "temporadas",
    foreignKeys = [
        ForeignKey(
            entity = SerieEntity::class,
            parentColumns = ["idSerie"],
            childColumns = ["serieId"]
        )
    ]
)
data class TemporadaEntity(
    @PrimaryKey(autoGenerate = true)
    val idTemporada: Int,
    var serieId: Int?,
    val idApi: Int?,
    val seasonNumber: Int?,
    val nombre: String?,
)
