package com.example.seriesandpelisjoseph.framework.main

import android.app.AlertDialog
import com.example.seriesandpelisjoseph.R

class LoadingDialog(val activity: FragmentBuscarPelis){

    private lateinit var isdialog: AlertDialog
    fun startLoading(){
        val infalter = activity.layoutInflater
        val dialogView = infalter.inflate(R.layout.progressbar,null)
        val bulider = AlertDialog.Builder(activity.context)
        bulider.setView(dialogView)
        bulider.setCancelable(false)
        isdialog = bulider.create()
        isdialog.show()
    }
    fun isDismiss(){
        isdialog.dismiss()
    }
}