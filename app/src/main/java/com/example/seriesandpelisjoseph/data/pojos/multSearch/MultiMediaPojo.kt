package com.example.seriesandpelisjoseph.data.pojos.multSearch


import com.google.gson.annotations.SerializedName

data class MultiMediaPojo(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val resultMultimediaPojos: List<ResultMultimediaPojo>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)