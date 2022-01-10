package com.example.seriesandpelisjoseph.data.model



import com.example.seriesandpelisjoseph.data.pojos.multSearch.ResultMediaPojo
import com.example.seriesandpelisjoseph.domain.Movie


fun ResultMediaPojo.toMovie():Movie {
    return Movie(0,this.id,this.posterPath,this.title,this.overview)
}