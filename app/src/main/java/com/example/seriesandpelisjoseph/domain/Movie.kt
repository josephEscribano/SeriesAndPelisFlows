package com.example.seriesandpelisjoseph.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val imagen: String,
    val titulo: String,
    val descripcion: String
): Parcelable
