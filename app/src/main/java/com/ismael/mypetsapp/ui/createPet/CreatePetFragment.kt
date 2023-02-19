package com.ismael.mypetsapp.ui.createPet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ismael.mypetsapp.R
import com.ismael.mypetsapp.databinding.FragmentCreatePetBinding
import com.ismael.mypetsapp.emptyToast
import com.ismael.mypetsapp.model.Pet

class CreatePetFragment : Fragment() {

    private lateinit var viewModel: CreatePetVM
    private lateinit var app: AppCompatActivity

    private var _binding: FragmentCreatePetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCreatePetBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[CreatePetVM::class.java]
        app = (requireActivity() as AppCompatActivity)

        //RECIBIMOS EL EMAIL DESDE EL INTENT DEL MAINACTIVITY
        val currentUser = app.intent.extras!!.getString("email")
        val root: View = binding.root

        binding.btnRegisterPet.setOnClickListener {
            if (binding.namePet.text.isNotEmpty() && binding.breedPet.text.isNotEmpty()
                && binding.agePet.text.isNotEmpty() && binding.colorPet.text.isNotEmpty()
                && binding.vaccinePet.text.isNotEmpty()) {

                val namePet = binding.namePet.text.toString()
                val breedPet = binding.breedPet.text.toString()
                val agePet = binding.agePet.text.toString().toInt()
                val colorPet = binding.colorPet.text.toString()
                val vaccinePet = binding.vaccinePet.text.toString().toDouble()

                val pet = Pet(namePet, breedPet, agePet, colorPet, vaccinePet)

                //CREAMOS LA MASCOTA EN EL USUARIO ACTUAL
                viewModel.createPet(pet, currentUser!!, app)

                //VOLVEMOS A LA PANTALLA PRINCIPAL
                findNavController().navigate(R.id.nav_create_pet_to_homeFragment)

            } else
                //MOSTRAMOS UN AVISO SI ALGUN CAMPO ESTA VACIO
                app.emptyToast()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}