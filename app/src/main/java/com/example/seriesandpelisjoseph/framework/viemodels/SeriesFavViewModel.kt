package com.example.seriesandpelisjoseph.framework.viemodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesandpelisjoseph.domain.MultiMedia
import com.example.seriesandpelisjoseph.usecases.GetSeries
import com.example.seriesandpelisjoseph.utils.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SeriesFavViewModel @Inject constructor(private val getSeries:GetSeries): ViewModel(){


    private val _serieFavData = MutableLiveData<List<MultiMedia>?>()
    val serieData: LiveData<List<MultiMedia>?> get() = _serieFavData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getSeries(){
        viewModelScope.launch {
            try {
                _serieFavData.value = getSeries.invoke()
            }catch (e:Exception){
                Log.e(Constantes.ERROR_OBTENER_FAV, e.message, e)
                _error.value = e.message
            }
        }

    }
}