package com.example.seriesandpelisjoseph.framework.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import coil.loadAny
import com.example.seriesandpelisjoseph.R
import com.example.seriesandpelisjoseph.databinding.FragmentBuscarPelisBinding
import com.example.seriesandpelisjoseph.databinding.FragmentMostrarPelisBinding
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class FragmentMostrarPelis : Fragment() {
    private var _binding : FragmentMostrarPelisBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMostrarPelisBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: FragmentMostrarPelisArgs by navArgs()
        with(binding){
            imageView.loadAny( args.multimedia.imagen?.let { getString(R.string.pathImage) +  it} ?: run { this.root.context.getDrawable(R.drawable.img) })
            tvTitulo.setText(args.multimedia.titulo)
            tvDescripcion.setText(args.multimedia.descripcion)
            tvFecha.setText(args.multimedia.fechaEmision)
        }
    }
}