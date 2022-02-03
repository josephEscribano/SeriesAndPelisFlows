package com.example.seriesandpelisjoseph.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    var id: Int,
    val idApi: Int,
    val imagen: String?,
    val tituloPeli: String,
    var visto: Boolean?,
    val puntuacion: Int,
    val descripcion: String?,
    val fechaEmision: String?,
    val actores: List<Actor>?
) : Parcelable
