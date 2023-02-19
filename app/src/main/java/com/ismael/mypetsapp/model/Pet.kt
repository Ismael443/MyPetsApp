package com.ismael.mypetsapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Pet(
    var nombre: String,
    var raza: String,
    var edad: Int,
    var color: String,
    var precioVacuna: Double): Parcelable {

    constructor(): this("", "", 0, "", 0.0)

}