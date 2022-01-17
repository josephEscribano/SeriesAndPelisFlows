package com.example.seriesandpelisjoseph.data.model

import com.example.seriesandpelisjoseph.data.model.entity.*
import com.example.seriesandpelisjoseph.domain.*
import com.example.seriesandpelisjoseph.utils.Constantes

//SERIES
fun Serie.toSerieWithTemporadas(): SeriesWithTemporadas {

    return SeriesWithTemporadas(
        this.toSerieEntity(),
        this.temporadas?.map { it.toTemporadasWithCapitulos() })

}

fun Temporada.toTemporadasWithCapitulos(): TemporadasWithCapitulos {
    return TemporadasWithCapitulos(
        this.toTemporadaEntity(),
        this.capitulos?.map { it.toCapituloEntity() })
}

fun Serie.toSerieEntity(): SerieEntity {
    return SerieEntity(id, idApi, imagen, tituloSerie, descripcion, fecha, puntuacion)
}

fun Temporada.toTemporadaEntity(): TemporadaEntity {
    return TemporadaEntity(id!!, idSerie, idApi, seasonNumber, nombre)
}

fun Capitulo.toCapituloEntity(): CapituloEntity {
    return CapituloEntity(id, idApi, idTemporada, nombre, visto, numero)
}

fun SeriesWithTemporadas.toMultimedia(): MultiMedia {
    return MultiMedia(
        this.serie.idSerie,
        this.serie.idApi,
        this.serie.imagen,
        this.serie.tituloSerie,
        this.serie.descripcion,
        this.serie.fecha,
        Constantes.TV
    )
}

fun SeriesWithTemporadas.toSerie(): Serie {
    return Serie(
        serie.idSerie,
        serie.idApi,
        serie.imagen,
        serie.tituloSerie,
        serie.descripcion,
        serie.fecha,
        serie.puntuacion,
        temporadas?.map { it.toTemporada() },
        null
    )
}

fun TemporadasWithCapitulos.toTemporada(): Temporada {
    return Temporada(
        temporada?.idTemporada,
        temporada?.idApi,
        temporada?.serieId,
        temporada?.seasonNumber,
        temporada?.nombre,
        capitulos?.map { it.toCapitulo() })
}

fun CapituloEntity.toCapitulo(): Capitulo {
    return Capitulo(idCapitulo, idApi, temporadaId, nombre, visto, numero)
}

//Peliculas
fun Movie.toMovieWithActores(): MovieWithActores {
    return MovieWithActores(this.toMovieEntity(), this.actores?.map { it.toActorEntity() })
}

fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(id, idApi, imagen, tituloPeli, visto, puntuacion, fechaEmision, descripcion)
}

fun Actor.toActorEntity(): ActorEntity {
    return ActorEntity(id, idApi, idActuaSerie, idActuaMovie, imagen, nombre, biografia, nacimiento)
}

fun MovieWithActores.toMultimedia(): MultiMedia {
    return MultiMedia(
        this.movie.idMovie,
        this.movie.idApi,
        this.movie.imagen,
        this.movie.tituloPeli,
        this.movie.descripcion,
        this.movie.fechaEmision,
        Constantes.MOVIE
    )
}