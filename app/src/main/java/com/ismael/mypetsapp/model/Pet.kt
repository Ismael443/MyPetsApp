package com.ismael.mypetsapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Suppress("unused")
@Parcelize
class Pet(
    var nombre: String,
    var raza: String,
    var edad: Int,
    var color: String,
    var precioVacuna: Int,
    var photoPet :String): Parcelable {

    constructor(): this("", "", 0, "", 0, "")

}