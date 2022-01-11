package com.example.seriesandpelisjoseph.framework.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.seriesandpelisjoseph.R
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class fragmentMostrarPelis : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mostrar_pelis, container, false)
    }

}