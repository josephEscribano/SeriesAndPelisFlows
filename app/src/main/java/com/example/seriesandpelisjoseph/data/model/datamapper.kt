package com.example.seriesandpelisjoseph.data.model


import com.example.seriesandpelisjoseph.data.modeloPeliculas.ResultPojo
import com.example.seriesandpelisjoseph.domain.Movie


fun ResultPojo.toMovie():Movie {
    return Movie(this.id,this.posterPath,this.title,this.overview)
}