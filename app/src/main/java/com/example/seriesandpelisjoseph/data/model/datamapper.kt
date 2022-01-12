package com.example.seriesandpelisjoseph.data.model



import com.example.seriesandpelisjoseph.R
import com.example.seriesandpelisjoseph.data.pojos.modeloSeries.Season
import com.example.seriesandpelisjoseph.data.pojos.modeloSeries.SeriePojo
import com.example.seriesandpelisjoseph.data.pojos.multSearch.ResultMultimediaPojo
import com.example.seriesandpelisjoseph.data.pojos.popularMovie.ResultPopularMoviePojo
import com.example.seriesandpelisjoseph.data.pojos.popularSeries.ResultPopularSeriePojo
import com.example.seriesandpelisjoseph.domain.MultiMedia
import com.example.seriesandpelisjoseph.domain.Serie
import com.example.seriesandpelisjoseph.domain.Temporada
import com.example.seriesandpelisjoseph.utils.Constantes


//Mapeo MultiSearch
fun ResultMultimediaPojo.toMovieMultimedia():MultiMedia {
    return MultiMedia(0,this.id,this.posterPath,this.title ,this.overview,this.releaseDate,this.mediaType)
}

fun ResultMultimediaPojo.toSerieMultimedia():MultiMedia {
    return MultiMedia(0,this.id,this.posterPath,this.name ,this.overview,this.firstAirDate,this.mediaType)
}

fun ResultMultimediaPojo.toActorMultimedia():MultiMedia{
    return MultiMedia(0,this.id,this.profilePath,this.name,null,null,this.mediaType)
}

fun ResultPopularMoviePojo.toMovieMultimedia():MultiMedia{

    return MultiMedia(0,this.id,this.posterPath,this.title ,this.overview, this.releaseDate,Constantes.MOVIE)
}

fun ResultPopularSeriePojo.toSerieMultimedia():MultiMedia{
    return MultiMedia(0,this.id,this.posterPath,this.name ,this.overview,this.firstAirDate,Constantes.TV)
}

fun SeriePojo.toSerie() : Serie{
    return Serie(0,this.id,this.posterPath,this.name,this.overview,this.firstAirDate,null,this.seasons.map { it.toTemporada() })
}

fun Season.toTemporada() : Temporada{
    return Temporada(0,this.id,this.name,null)
}


