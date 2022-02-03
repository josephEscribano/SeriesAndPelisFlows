package com.example.seriesandpelisjoseph.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cachePelis")
data class CachePelisEntity(
    @PrimaryKey(autoGenerate = true)
    var idCache: Int = 0,
    val idApi: Int,
    val imagen: String?,
    val tituloPeli: String,
)
