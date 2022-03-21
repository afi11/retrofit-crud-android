package com.example.pertanian.components

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog

class AlertWarning(context: Context) {

    fun onAlertDialog(view: View, message: String) : AlertDialog.Builder {
        val builder = AlertDialog.Builder(view.context)
        builder.setTitle("Peringatan")

        builder.setMessage(message)

        return builder

    }

}