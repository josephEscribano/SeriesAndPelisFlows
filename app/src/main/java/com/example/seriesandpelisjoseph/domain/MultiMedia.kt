package com.example.seriesandpelisjoseph.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MultiMedia(
    val id: Int,
    val idApi: Int,
    val imagen: String?,
    val titulo: String,
    val descripcion: String?,
    val fechaEmision: String?,
    val tipo: String,
    val visto: Boolean?
) : Parcelable
