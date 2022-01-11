package com.example.seriesandpelisjoseph.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Temporada(
    val id:Int,
    val idApi:Int,
    val numero:Int,
    val capitulos:List<Capitulos>? = null
):Parcelable
