package com.ismael.mypetsapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ismael.mypetsapp.model.Database
import com.ismael.mypetsapp.model.Pet

class DetailPetVM(val pet: Pet): ViewModel() {
    private val _pet = MutableLiveData(pet)

    fun getPet(): LiveData<Pet> = _pet

    fun deletePet(pet: Pet, currentUser:String, app:AppCompatActivity){
        Database.deletePet(pet, currentUser, app)
    }

}

@Suppress("UNCHECKED_CAST")
class DetailFragmentViewModelFactory(private val pet: Pet): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailPetVM(pet) as T
    }
}