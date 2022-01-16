package com.example.seriesandpelisjoseph.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Temporada(
    val id:Int,
    val idApi:Int,
    var idSerie:Int?,
    val seasonNumber:Int,
    val nombre:String,
    var capitulos: List<Capitulo>?
):Parcelable {
    override fun toString(): String {
        return nombre
    }
}
