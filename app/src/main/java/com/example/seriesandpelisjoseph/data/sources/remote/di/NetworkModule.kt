package com.example.seriesandpelisjoseph.data.sources.remote.di

import com.example.seriesandpelisjoseph.data.sources.remote.ApiInterface.ActorService
import com.example.seriesandpelisjoseph.data.sources.remote.ApiInterface.MultiMediaService
import com.example.seriesandpelisjoseph.data.sources.remote.ApiInterface.SerieService
import com.example.seriesandpelisjoseph.data.sources.remote.ServiceInterceptor
import com.example.seriesandpelisjoseph.utils.Constantes.BASE_URL
import com.google.gson.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.time.LocalDate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import com.google.gson.JsonPrimitive

import com.google.gson.JsonSerializer

import com.google.gson.JsonDeserializer

import com.google.gson.GsonBuilder

import com.google.gson.Gson





@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideServiceInterceptor(): ServiceInterceptor = ServiceInterceptor()


    @Singleton
    @Provides
    fun provideHttpClient(serviceInterceptor: ServiceInterceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(serviceInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun getGson(): Gson {
        return GsonBuilder().registerTypeAdapter(
            LocalDate::class.java,
            JsonDeserializer { jsonElement: JsonElement, type: Type?, jsonDeserializationContext: JsonDeserializationContext? ->
                LocalDate.parse(
                    jsonElement.asJsonPrimitive.asString
                )
            } as JsonDeserializer<LocalDate>)
            .registerTypeAdapter(
                LocalDate::class.java,
                JsonSerializer { localDate: LocalDate, type: Type?, jsonSerializationContext: JsonSerializationContext? ->
                    JsonPrimitive(
                        localDate.toString()
                    )
                } as JsonSerializer<LocalDate>).create()
    }
    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(getGson()))
            .build()
    }

    @Singleton
    @Provides
    fun multimediaService(retrofit: Retrofit): MultiMediaService =
        retrofit.create(MultiMediaService::class.java)

    @Singleton
    @Provides
    fun serieService(retrofit: Retrofit): SerieService = retrofit.create(SerieService::class.java)

    @Singleton
    @Provides
    fun actorService(retrofit: Retrofit): ActorService = retrofit.create(ActorService::class.java)
}