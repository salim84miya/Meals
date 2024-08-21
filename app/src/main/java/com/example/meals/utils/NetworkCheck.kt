package com.example.meals.utils

import android.app.AlertDialog
import android.content.Context

object InternetConnectivity{

    fun createAlertDialog(context: Context):AlertDialog{
        val builder = AlertDialog.Builder(context).apply {
            setTitle("Network connectivity")
            setMessage("No internet connection available")
            setCancelable(false)
        }
        val alertDialog = builder.create()
        return alertDialog
    }
}