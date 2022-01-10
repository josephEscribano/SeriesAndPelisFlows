package com.example.seriesandpelisjoseph.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Series(
    val id: Int = 0,
    val idApi: Int,
    val imagen: String?,
    val titulo: String,
    val descripcion: String
) : Parcelable