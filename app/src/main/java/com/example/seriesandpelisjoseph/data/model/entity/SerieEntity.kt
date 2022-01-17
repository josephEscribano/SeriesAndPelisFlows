package com.example.seriesandpelisjoseph.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "series")
data class SerieEntity(
    @PrimaryKey(autoGenerate = true)
    var idSerie: Int = 0,
    val idApi: Int,
    val imagen: String?,
    val tituloSerie: String,
    val descripcion: String,
    val fecha: String,
    val puntuacion: Int?,
)
