package com.example.seriesandpelisjoseph.data.repositories

import com.example.seriesandpelisjoseph.data.sources.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class ActorRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {
    suspend fun getActor(actorId: Int) =
        withContext(Dispatchers.IO) { remoteDataSource.getActor(actorId) }
}