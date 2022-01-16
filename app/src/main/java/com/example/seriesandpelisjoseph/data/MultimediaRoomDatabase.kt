package com.example.seriesandpelisjoseph.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.seriesandpelisjoseph.data.model.entity.*


@Database(
    entities = [ActorEntity::class, SerieEntity::class, MovieEntity::class,CapituloEntity::class,TemporadaEntity::class],
    version = 17,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class MultimediaRoomDatabase : RoomDatabase(){

    abstract fun serieDao() : MultimediaDao

    abstract fun movieDao() : MovieDao
}