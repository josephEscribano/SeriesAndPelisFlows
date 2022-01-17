package com.example.seriesandpelisjoseph.data.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "capitulos",
    foreignKeys = [
        ForeignKey(
            entity = TemporadaEntity::class,
            parentColumns = ["idTemporada"],
            childColumns = ["temporadaId"]
        )
    ]
)
data class CapituloEntity(
    @PrimaryKey(autoGenerate = true)
    val idCapitulo: Int,
    val idApi: Int,
    var temporadaId: Int?,
    val nombre: String,
    val visto: Boolean,
    val numero: Int,
)
