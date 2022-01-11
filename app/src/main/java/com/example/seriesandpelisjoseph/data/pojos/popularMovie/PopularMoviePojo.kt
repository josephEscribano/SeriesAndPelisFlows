package com.example.seriesandpelisjoseph.data.pojos.popularMovie


import com.google.gson.annotations.SerializedName

data class PopularMoviePojo(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val resultPopularMoviePojos: List<ResultPopularMoviePojo>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)