package com.example.seriesandpelisjoseph.framework.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.loadAny
import com.example.seriesandpelisjoseph.R
import com.example.seriesandpelisjoseph.databinding.FragmentBuscarPelisBinding
import com.example.seriesandpelisjoseph.databinding.FragmentMostrarActoresBinding
import com.example.seriesandpelisjoseph.framework.viemodels.MostrarActoresViewmodel
import com.example.seriesandpelisjoseph.framework.viemodels.MostrarSeriesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentMostrarActores : Fragment() {

    private var _binding : FragmentMostrarActoresBinding? = null
    private val binding get() = _binding!!
    private val viewmodel: MostrarActoresViewmodel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.series).isVisible = false
        menu.findItem(R.id.pelis).isVisible = false
        menu.findItem(R.id.buscar).isVisible = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMostrarActoresBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args : FragmentMostrarActoresArgs by navArgs()
        viewmodel.getActor(args.idActor)

        viewmodel.actorData.observe(this,{
            with(binding){
                imageView.loadAny(it?.imagen?.let { getString(R.string.pathImage) +  it} ?: run { this.root.context.getDrawable(R.drawable.img) })
                tvNombre.setText(it?.nombre)
                tvBiografia.setText(it?.biografia)
                tvFecha.setText(it?.nacimiento)

        }

            }
        )

        viewmodel.error.observe(this,{
            Snackbar.make(binding.root,it, Snackbar.LENGTH_SHORT).show()
        })
    }
}