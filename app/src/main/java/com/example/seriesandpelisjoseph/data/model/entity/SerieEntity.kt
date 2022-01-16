package com.example.seriesandpelisjoseph.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.seriesandpelisjoseph.domain.Temporada

@Entity(tableName = "series")
data class SerieEntity(
    @PrimaryKey(autoGenerate = true)
    var idSerie: Int = 0,
    val idApi: Int,
    val imagen: String?,
    val tituloSerie: String,
    val descripcion: String,
    val fecha:String,
    val puntuacion: Int?,
)
