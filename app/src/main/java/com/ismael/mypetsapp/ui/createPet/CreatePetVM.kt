package com.ismael.mypetsapp.ui.createPet

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.ismael.mypetsapp.model.Database
import com.ismael.mypetsapp.model.Pet

class CreatePetVM : ViewModel() {

    fun createPet(pet: Pet, currentUser: String, app: AppCompatActivity) {
        Database.createPet(pet, currentUser, app)
    }
}