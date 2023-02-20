package com.ismael.mypetsapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.ismael.mypetsapp.R
import com.ismael.mypetsapp.databinding.FragmentHomeBinding
import com.ismael.mypetsapp.model.Pet
import com.ismael.mypetsapp.model.PetAdapter
import com.ismael.mypetsapp.ui.detail.DetailPetFragment
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var app: AppCompatActivity

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        app = (requireActivity() as AppCompatActivity)

        //RECIBIMOS EL CAMPO EMAIL DESDE EL INTENT DEL MAINACTIVITY
        val currentUser = app.intent.extras!!.getString("email")

        //INICIALIZAMOS NUESTRO VIEWMODEL
        val viewModel: HomeViewModel by viewModels { HomeViewModelFactory(currentUser!!) }
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        //INICIAMOS EL ADAPTER Y EL RECYCLERVIEW
        val adapter = PetAdapter {pet -> navigateTo(pet)}
        binding.recycler.adapter = adapter

        //DEJAMOS OBSERVANDO LOS CAMBIOS QUE SE PRODUCEN EN EL VIEWMODEL
        viewModel.getResultList().observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    viewModel.getResultList().value!!.collect{
                        adapter.pets = it
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }

        return binding.root
    }

    //FUNCION POR LA CUAL NAVEGAMOS HACIA EL DETALLE, RECIBE UNA MASCOTA COMO PARAMETRO Y
    //EN EL DETAILPETFRAGMENT ESTABLECE ESTE PARAMETRO EN LA VARIABLE ESTÁTICA.
    fun navigateTo(pet: Pet) {
        findNavController().navigate(
            R.id.nav_homeFragment_to_detailFragment, bundleOf(DetailPetFragment.PET_SELECTED to pet)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}