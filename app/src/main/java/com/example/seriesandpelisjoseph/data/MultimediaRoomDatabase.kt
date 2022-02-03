package com.example.seriesandpelisjoseph.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.seriesandpelisjoseph.data.model.entity.*


@Database(
    entities = [ActorEntity::class, SerieEntity::class, MovieEntity::class, CapituloEntity::class, TemporadaEntity::class, CachePelisEntity::class],
    version = 32,
    exportSchema = true
)

abstract class MultimediaRoomDatabase : RoomDatabase() {

    abstract fun serieDao(): SerieDao

    abstract fun movieDao(): MovieDao
}