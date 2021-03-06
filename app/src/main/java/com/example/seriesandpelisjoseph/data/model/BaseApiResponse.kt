package com.example.seriesandpelisjoseph.data.model

import com.example.seriesandpelisjoseph.utils.NetworkResult
import retrofit2.Response

abstract class BaseApiResponse {

    suspend fun <T, R> safeApiCall(
        apicall: suspend () -> Response<R>,
        transform: (R) -> T
    ): NetworkResult<T> {
        try {
            val response = apicall()

            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Succcess(transform(body))
                }
            }
            return error("${response.code()} ${response.message()}")

        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    suspend fun <T> safeApiCall(apicall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apicall()

            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Succcess(body)
                }
            }
            return error("${response.code()} ${response.message()}")

        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Ha fallado la llamada $errorMessage")
}