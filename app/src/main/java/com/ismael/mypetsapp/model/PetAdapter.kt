package com.ismael.mypetsapp.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ismael.mypetsapp.R


//RECIBIMOS UNA MASCOTA LA CUAL ES EL LISTENER DE CADA UNA DE LAS MASCOTAS DEL USUARIO
class PetAdapter(val listener: (Pet) -> Unit): RecyclerView.Adapter<PetAdapter.ViewHolder>() {

    //INSTANCIAMOS UNA LISTA DE MASCOTAS VACIA.
    var pets = emptyList<Pet>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pet_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = pets.size

    override fun onBindViewHolder(holder: ViewHolder, posicion: Int) {
        val pet = pets[posicion]

        holder.namePet.text = pet.nombre
        holder.breedPet.text = pet.raza
        holder.agePet.text = pet.edad.toString()
        holder.colorPet.text = pet.color

        //EVENTO POR EL CUAL SI PULSAMOS EN UNA MASCOTA, MANDARA AL HOMEFRAGMENT LA MASCOTA
        //SELECCIONADA Y MOSTRARA EL DETAIL DE ESA MASCOTA
        holder.itemView.setOnClickListener {
            listener(pet)
        }

    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val namePet: TextView = view.findViewById(R.id.textNamePet)
        val breedPet: TextView = view.findViewById(R.id.textBreedPet)
        val agePet: TextView = view.findViewById(R.id.textAgePet)
        val colorPet: TextView = view.findViewById(R.id.textColorPet)
    }
}
