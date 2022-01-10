package com.example.seriesandpelisjoseph.data.pojos.multSearch


import com.google.gson.annotations.SerializedName

data class MultiMediaPojo(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val resultMediaPojos: List<ResultMediaPojo>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)