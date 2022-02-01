package com.example.seriesandpelisjoseph.framework.main.mostrarSerieFavRoom

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesandpelisjoseph.domain.Capitulo
import com.example.seriesandpelisjoseph.domain.Serie
import com.example.seriesandpelisjoseph.usecases.GetCapitulos
import com.example.seriesandpelisjoseph.usecases.GetSerie
import com.example.seriesandpelisjoseph.usecases.UpdateCapitulo
import com.example.seriesandpelisjoseph.utils.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MostrarSeriesFavViewModel @Inject constructor(
    private val getCapitulos: GetCapitulos,
    private val getSerie: GetSerie,
    private val updateCapitulo: UpdateCapitulo,

    ) : ViewModel() {

    private val listaCapitulosSelected = mutableListOf<Capitulo>()

    private val _serieData = MutableLiveData<Serie?>()
    val serieData: LiveData<Serie?> get() = _serieData

    private val _capituloData = MutableLiveData<List<Capitulo>?>()
    val capituloData: LiveData<List<Capitulo>?> get() = _capituloData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

//    fun getSerie(id: Int?) {
//        viewModelScope.launch {
//            try {
//                _serieData.value = id?.let { getSerie.invoke(it) }
//            } catch (e: Exception) {
//                Log.e(Constantes.ERROR_OBTENER_FAV, e.message, e)
//                _error.value = e.message
//            }
//
//        }
//    }

    fun getCapitulo(idTemporada: Int?) {
        viewModelScope.launch {
            try {
                _capituloData.value = idTemporada?.let { getCapitulos.invoke(it) }
            } catch (e: Exception) {
                Log.e(Constantes.ERROR_OBTENER_FAV, e.message, e)
                _error.value = e.message
            }
        }
    }

    fun updateCapitulo() {
        viewModelScope.launch {
            try {
                listaCapitulosSelected.forEach { it.visto = true }
                updateCapitulo.invoke(listaCapitulosSelected.toList())

            } catch (e: Exception) {
                Log.e(Constantes.ERROR_OBTENER_FAV, e.message, e)
                _error.value = e.message
            }
        }
    }


    //CONTEXTBAR Y MULTISELECT
    fun seleccionaCapitulo(capitulo: Capitulo) {
        if (isSelected(capitulo)) {
            listaCapitulosSelected.remove(capitulo)
        } else {
            listaCapitulosSelected.add(capitulo)
        }
    }

    fun isSelected(capitulo: Capitulo): Boolean {
        return listaCapitulosSelected.contains(capitulo)
    }

    fun getLista() = listaCapitulosSelected.toList()

    fun clearList() = listaCapitulosSelected.clear()
}