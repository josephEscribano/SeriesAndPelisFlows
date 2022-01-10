package com.example.seriesandpelisjoseph.data.sources.remote.di

import com.example.seriesandpelisjoseph.data.sources.remote.MultiMediaService
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
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()
//    fun provideConverterFactory(): Gson? = GsonBuilder().registerTypeAdapter(
//        LocalDate::class.java,
//        JsonDeserializer<LocalDate> { jsonElement: JsonElement, type: Type?, jsonDeserializationContext: JsonDeserializationContext? ->
//            LocalDate.parse(
//                jsonElement.asJsonPrimitive.asString
//            )
//        } as JsonDeserializer<LocalDate>?)
//        .registerTypeAdapter(
//            LocalDate::class.java,
//            JsonSerializer<LocalDate> { localDate: LocalDate, type: Type?, jsonSerializationContext: JsonSerializationContext? ->
//                JsonPrimitive(
//                    localDate.toString()
//                )
//            } as JsonSerializer<LocalDate>?).create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun seriesService(retrofit: Retrofit): MultiMediaService =
        retrofit.create(MultiMediaService::class.java)
}