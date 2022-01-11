package com.example.seriesandpelisjoseph.data.pojos.popularSeries


import com.google.gson.annotations.SerializedName

data class PopularSeriePojo(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val resultPopularSeriePojos: List<ResultPopularSeriePojo>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)