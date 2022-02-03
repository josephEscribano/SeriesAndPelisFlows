package com.example.seriesandpelisjoseph.data.repositories

import com.example.seriesandpelisjoseph.data.sources.remote.RemoteDataSource
import com.example.seriesandpelisjoseph.domain.Actor
import com.example.seriesandpelisjoseph.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class ActorRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {
    fun getActor(actorId: Int): Flow<NetworkResult<Actor>> {
        return flow {
            emit(NetworkResult.Loading())
            emit(remoteDataSource.getActor(actorId))
        }.flowOn(Dispatchers.IO)
    }

}