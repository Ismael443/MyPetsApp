package com.ismael.mypetsapp.model

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.snapshots
import com.ismael.mypetsapp.showMsg
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

object Database {

    private const val COLLECTION_USERS = "usuarios"
    private const val COLLECTION_PETS = "mascotas"


    private fun database(): FirebaseFirestore = FirebaseFirestore.getInstance()

    //OBTENEMOS LAS MASCOTAS DEL USUARIO ACTUAL
    suspend fun getPetsFromUser(email: String): List<Pet> {
        val db = database()
        val instance = db.collection(COLLECTION_USERS).document(email).collection(COLLECTION_PETS).get().await()
        val pets = mutableListOf<Pet>()

        for (document in instance) {
            val pet = document.toObject(Pet::class.java)
            pets.add(pet)
        }

        return pets
    }

    //FUNCIÓN PARA CREAR UN USUARIO Y PODER AÑADIRLO A LA BASE DE DATOS
    fun createUser(user: User, app: AppCompatActivity) {
        val db = database()
        val userData = hashMapOf(
            "dni" to user.dni,
            "nombre" to user.nombre,
            "direccion" to user.direccion,
            "email" to user.email,
            "password" to user.password
        )

        db.collection(COLLECTION_USERS).document(user.email).set(userData).addOnCompleteListener{

            //SI SE CREO EL USUARIO DE FORMA CORRECTA:
            if (it.isSuccessful)
                app.showMsg("INFORMACION", "USUARIO CREADO CORRECTAMENTE")
            else
                app.showMsg("ERROR", "ERROR AL AÑADIR EL USUARIO A LA BASE DE DATOS")
        }
    }

    //CREACION DE UN OBJETO MASCOTA
    fun createPet(pet: Pet, currentUser: String, app: AppCompatActivity) {
        val db = database()
        val petData = hashMapOf(
            "nombre" to pet.nombre,
            "raza" to pet.raza,
            "edad" to pet.edad,
            "color" to pet.color,
            "precio_vacuna" to pet.precioVacuna)

        db.collection(COLLECTION_USERS).document(currentUser).collection(COLLECTION_PETS).document(pet.nombre)
            .set(petData).addOnCompleteListener {

                //SI LA MASCOTA SE CREO CORRECTAMENTE
                if (it.isSuccessful)
                    app.showMsg("INFORMACION", "MASCOTA REGISTRADA AL USUARIO: $currentUser")
                else
                    app.showMsg("ERROR", "NO HA SIDO POSIBLE REGISTRAR LA MASCOTA")
            }
    }

    fun deletePet(pet: Pet, currentUser: String, app: AppCompatActivity) {
        val db = database()

        db.collection(COLLECTION_USERS).document(currentUser).collection(COLLECTION_PETS).document(pet.nombre)
            .delete().addOnCompleteListener {

            if (it.isSuccessful)
                app.showMsg("INFORMACION", "MASCOTA ELIMINADA CORRECTAMENTE")
            else
                app.showMsg("ERROR", "NO ES POSIBLE ELIMINAR ESTA MASCOTA")
        }
    }

    fun getFlow(currentUser: String): Flow<List<Pet>> {
        return FirebaseFirestore.getInstance()
            .collection(COLLECTION_USERS).document(currentUser).collection(COLLECTION_PETS)
            .orderBy("nombre", Query.Direction.DESCENDING).snapshots().map {
                it.toObjects(Pet::class.java)
            }
    }
}