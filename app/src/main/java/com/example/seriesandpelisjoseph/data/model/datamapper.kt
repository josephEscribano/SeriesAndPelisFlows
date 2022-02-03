package com.example.seriesandpelisjoseph.data.model


import com.example.seriesandpelisjoseph.data.pojos.modeloActor.ActorPojo
import com.example.seriesandpelisjoseph.data.pojos.modeloSeries.SeasonPojo
import com.example.seriesandpelisjoseph.data.pojos.modeloSeries.SeriePojo
import com.example.seriesandpelisjoseph.data.pojos.modeloTemporadas.Episode
import com.example.seriesandpelisjoseph.data.pojos.multSearch.ResultMultimediaPojo
import com.example.seriesandpelisjoseph.data.pojos.popularMovie.ResultPopularMoviePojo
import com.example.seriesandpelisjoseph.data.pojos.popularSeries.ResultPopularSeriePojo
import com.example.seriesandpelisjoseph.domain.*
import com.example.seriesandpelisjoseph.utils.Constantes


fun ResultMultimediaPojo.toMultimedia(): MultiMedia {
    var multiMedia: MultiMedia
    if (this.mediaType == Constantes.MOVIE)
        multiMedia = MultiMedia(
            0,
            this.id,
            this.posterPath,
            this.title,
            this.overview,
            this.firstAirDate,
            this.mediaType,
            null
        )
    else if (this.mediaType == Constantes.TV)
        multiMedia = MultiMedia(
            0,
            this.id,
            this.posterPath,
            this.name,
            this.overview,
            this.firstAirDate,
            this.mediaType,
            null
        )
    else
        multiMedia =
            MultiMedia(0, this.id, this.profilePath, this.name, null, null, this.mediaType, null)

    return multiMedia
}


fun ResultPopularMoviePojo.toMultimedia(): MultiMedia {

    return MultiMedia(
        0,
        this.id,
        this.posterPath,
        this.title,
        this.overview,
        this.releaseDate,
        Constantes.MOVIE,
        null
    )
}

fun ResultPopularSeriePojo.toSerieMultimedia(): MultiMedia {
    return MultiMedia(
        0,
        this.id,
        this.posterPath,
        this.name,
        this.overview,
        this.firstAirDate,
        Constantes.TV,
        null
    )
}

fun SeriePojo.toSerie(): Serie {
    return Serie(
        0,
        this.id,
        this.posterPath,
        this.name,
        this.overview,
        this.firstAirDate,
        null,
        null,
        this.seasonPojos.map { it.toTemporada() },
        null
    )
}

fun SeasonPojo.toTemporada(): Temporada {
    return Temporada(0, this.id, null, this.seasonNumber, this.name, null)
}

fun ActorPojo.toActor(): Actor {
    return Actor(0, this.id, this.profilePath, this.name, this.biography, this.birthday, null, null)
}

fun Episode.toCapitulo(): Capitulo {
    return Capitulo(0, this.id, null, this.name, false, this.episodeNumber)
}

fun MultiMedia.toMovie(): Movie {
    return Movie(id, idApi, imagen, titulo, visto, 0, descripcion, fechaEmision, null)
}

fun MultiMedia.toSerie(): Serie {
    return Serie(id, idApi, imagen, titulo, descripcion, fechaEmision, 0, visto, null, null)
}





