package com.example.seriesandpelisjoseph.data.model



import com.example.seriesandpelisjoseph.R
import com.example.seriesandpelisjoseph.data.pojos.multSearch.ResultMediaPojo
import com.example.seriesandpelisjoseph.data.pojos.popularMovie.ResultPopularMoviePojo
import com.example.seriesandpelisjoseph.data.pojos.popularSeries.ResultPopularSeriePojo
import com.example.seriesandpelisjoseph.domain.MultiMedia


//Mapeo MultiSearch
fun ResultMediaPojo.toMovieMultimedia():MultiMedia {
    return MultiMedia(0,this.id,this.posterPath,this.title ,this.overview,this.mediaType)
}

fun ResultMediaPojo.toSerieMultimedia():MultiMedia {
    return MultiMedia(0,this.id,this.posterPath,this.name ,this.overview,this.mediaType)
}

fun ResultMediaPojo.toActorMultimedia():MultiMedia{
    return MultiMedia(0,this.id,this.posterPath,this.name,null,this.mediaType)
}

fun ResultPopularMoviePojo.toMovieMultimedia():MultiMedia{

    return MultiMedia(0,this.id,this.posterPath,this.title ,this.overview, R.string.movie.toString())
}

fun ResultPopularSeriePojo.toSerieMultimedia():MultiMedia{
    return MultiMedia(0,this.id,this.posterPath,this.name ,this.overview,R.string.tv.toString())
}


