package com.ismael.mypetsapp.ui.home

import androidx.lifecycle.*
import com.ismael.mypetsapp.model.Database
import com.ismael.mypetsapp.model.Pet
import kotlinx.coroutines.launch

class HomeViewModel(currentUser: String) : ViewModel() {

    private lateinit var petList: List<Pet>

    private val _petListResult = MutableLiveData<List<Pet>>(emptyList())


    init {

    //INICIAMOS EL VIEWMODEL, LOS CAMBIOS SE PRODUCIRAN AQUÍ Y MIENTRAS TANTO EL FRAGMENT
        //SE MANTENDRA ESCUCHANDO PARA APLICAR ESOS CAMBIOS
        viewModelScope.launch {

            //RECOGEMOS LA LISTA DE MASCOTAS DEL USUARIO QUE TIENE LA SESIÓN INICIADA
            petList = Database.getPetsFromUser(currentUser)
            _petListResult.postValue(petList)
        }
    }

    fun getResultList(): LiveData<List<Pet>> = _petListResult
}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val currentUser: String): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(currentUser) as T
    }
}