package com.example.seriesandpelisjoseph.data.modeloPeliculas


import com.google.gson.annotations.SerializedName

data class MoviePojo(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val resultPojos: List<ResultPojo>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)