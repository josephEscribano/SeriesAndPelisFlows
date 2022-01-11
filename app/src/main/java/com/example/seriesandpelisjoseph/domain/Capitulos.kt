package com.example.seriesandpelisjoseph.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Capitulos(
    val id:Int,
    val idApi:Int,
    val nombre:String,
    val visto:Boolean,
    val numero:Int,
):Parcelable
