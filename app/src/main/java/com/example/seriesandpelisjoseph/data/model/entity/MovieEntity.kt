package com.example.seriesandpelisjoseph.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    var idMovie: Int = 0,
    val idApi: Int,
    val imagen: String?,
    val tituloPeli: String,
    val visto:Boolean,
    val puntuacion: Int,
    val fechaEmision: String?,
    val descripcion: String?,
)