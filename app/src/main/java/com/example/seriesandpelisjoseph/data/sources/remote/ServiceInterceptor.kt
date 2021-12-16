package com.example.seriesandpelisjoseph.data.sources.remote

import okhttp3.Interceptor
import okhttp3.Response

class ServiceInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url.newBuilder()
            .addQueryParameter("api_key","568506b42ec6945fb6dc3819148be864")
            .build()
        val request = chain.request().newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }

}