package com.example.seriesandpelisjoseph.utils

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null,
) {
    class Succcess<T>(data: T) : NetworkResult<T>(data)

    class Error<T>(message: String, data: T? = null) : NetworkResult<T>(data, message)

    fun <R> map(transform: (data: T?) -> R): NetworkResult<R> =
        when (this) {
            is Error -> Error(message!!, transform(data))
            is Succcess -> Succcess(transform(data))
        }
}
