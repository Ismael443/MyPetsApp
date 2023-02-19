package com.ismael.mypetsapp.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ismael.mypetsapp.R
import com.ismael.mypetsapp.databinding.DetailPetBinding
import com.ismael.mypetsapp.model.Pet

class DetailPetFragment: Fragment(R.layout.detail_pet) {

    companion object { const val PET_SELECTED = "petSelected" }

    private lateinit var pet: Pet
    private lateinit var app: AppCompatActivity
    private val viewModel: DetailPetVM by viewModels {
        DetailFragmentViewModelFactory(arguments?.getParcelable(PET_SELECTED)!!) }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = DetailPetBinding.bind(view)


        app = (requireActivity() as AppCompatActivity)

        val currentUser = app.intent.extras!!.getString("email")


        //OBSERVAMOS SI LA MASCOTA SE MODIFICA
        viewModel.getPet().observe(viewLifecycleOwner) {
            pet = it

            app.supportActionBar!!.title = "MASCOTA"

            binding.textNamePet.text = "NOMBRE : " + pet.nombre
            binding.textBreedPet.text = "RAZA : " + pet.raza
            binding.textAgePet.text = "AÑOS : " + pet.edad.toString()
            binding.textColorPet.text = "COLOR : " + pet.color
            binding.textVaccinePet.text = "PRECIO VACUNA : " + pet.precioVacuna.toString()


            binding.btnDeletePet.setOnClickListener {
                viewModel.deletePet(pet, currentUser!!, app)
                findNavController().navigate(R.id.nav_detail_on_click_delete_to_homeFragment)
            }

        }
    }
}