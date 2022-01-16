package com.example.seriesandpelisjoseph.data.diRoom

import android.content.Context
import androidx.room.Room
import com.example.seriesandpelisjoseph.R
import com.example.seriesandpelisjoseph.data.MultimediaRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            MultimediaRoomDatabase::class.java,
            context.getString(R.string.db)
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    fun serieDao(multimediaRoomDatabase: MultimediaRoomDatabase) = multimediaRoomDatabase.serieDao()

    @Provides
    fun movieDao(multimediaRoomDatabase: MultimediaRoomDatabase) = multimediaRoomDatabase.movieDao()
}