package com.example.seriesandpelisjoseph.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Capitulo(
    val id:Int,
    val idApi:Int,
    var idTemporada: Int?,
    val nombre:String,
    val visto:Boolean,
    val numero:Int,
    var isSelected:Boolean = false
):Parcelable
