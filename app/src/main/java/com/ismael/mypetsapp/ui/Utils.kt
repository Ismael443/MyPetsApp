package com.ismael.mypetsapp.ui

import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

//METODOS PARA MOSTRAR MENSAJES DE ALERTAS QUE USAREMOS EN LAS DIFERENTES CLASES

fun AppCompatActivity.emptyToast() {
    Toast.makeText(this, "DEBE COMPLETAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.showMsg(title: String, textMsg: String) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)
    builder.setMessage(textMsg)
    builder.setPositiveButton("ACEPTAR", null)

    val dialog = builder.create()
    dialog.show()
}

fun ImageView.loadUrl(url: String){
    Glide.with(this)
        .load(url)
        .into(this)
}
