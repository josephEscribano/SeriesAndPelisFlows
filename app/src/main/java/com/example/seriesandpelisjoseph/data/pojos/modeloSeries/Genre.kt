package com.example.seriesandpelisjoseph.data.pojos.modeloSeries


import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)