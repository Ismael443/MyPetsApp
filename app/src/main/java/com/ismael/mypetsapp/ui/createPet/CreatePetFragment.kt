package com.ismael.mypetsapp.ui.createPet

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.ismael.mypetsapp.R
import com.ismael.mypetsapp.databinding.FragmentCreatePetBinding
import com.ismael.mypetsapp.ui.emptyToast
import com.ismael.mypetsapp.model.Pet

@Suppress("DEPRECATION")
class CreatePetFragment : Fragment() {

    private lateinit var viewModel: CreatePetVM
    private lateinit var app: AppCompatActivity

    private var _binding: FragmentCreatePetBinding? = null
    private val binding get() = _binding!!

    //VARIABLES Y MÉTODOS PARA LA IMAGEN//
    private lateinit var imageUri: Uri

    companion object {
        private const val PICK_IMAGE_REQUEST_CODE = 1
    }

    //FUNCIÓN QUE ABRE LA GALERIA Y MUESTRA LAS IMAGENES DISPONIBLES
    @Suppress("DEPRECATION")
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCreatePetBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[CreatePetVM::class.java]
        app = (requireActivity() as AppCompatActivity)

        //RECIBIMOS EL EMAIL DESDE EL INTENT DEL MAINACTIVITY
        val currentUser = app.intent.extras!!.getString("email")
        val root: View = binding.root

        //CUANDO SE PULSE EN LA IMAGEN, ACCEDEMOS A LA GALERIA
        binding.photoPetView.setOnClickListener {
            openGallery()
        }

        binding.btnRegisterPet.setOnClickListener {
            if (binding.namePet.text.isNotEmpty() && binding.breedPet.text.isNotEmpty()
                && binding.agePet.text.isNotEmpty() && binding.colorPet.text.isNotEmpty()
                && binding.vaccinePet.text.isNotEmpty()) {

                val namePet = binding.namePet.text.toString()
                val breedPet = binding.breedPet.text.toString()
                val agePet = binding.agePet.text.toString().toInt()
                val colorPet = binding.colorPet.text.toString()
                val vaccinePet = binding.vaccinePet.text.toString().toInt()

                //CREAMOS UNA REFERENCIA AL ARCHIVO EN FIRESTORE STORAGE
                val storageRef = Firebase.storage.reference.child("pets/${imageUri.lastPathSegment}")

                //SUBIMOS LA IMAGEN A STORAGE
                val uploadTask = storageRef.putFile(imageUri)
                uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let { throw it }
                    }
                    //DEVOLVEMOS LA URL DE LA IMAGEN
                    storageRef.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //GUARDAMOS EN UNA VARIABLE LA URL DESCARGADA
                        val urlObtenida = task.result.toString()

                val pet = Pet(namePet, breedPet, agePet, colorPet, vaccinePet, urlObtenida)

                //CREAMOS LA MASCOTA EN EL USUARIO ACTUAL
                viewModel.createPet(pet, currentUser!!, app)

                //VOLVEMOS A LA PANTALLA PRINCIPAL
                findNavController().navigate(R.id.nav_create_pet_to_homeFragment)
                    }
                }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.data != null) {
            imageUri = data.data!!
            binding.photoPetView.setImageURI(imageUri)
        }
    }
}