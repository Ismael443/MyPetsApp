package com.ismael.mypetsapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.ismael.mypetsapp.databinding.ActivitySignupBinding
import com.ismael.mypetsapp.model.Database
import com.ismael.mypetsapp.model.User

class SignUpActivity : AppCompatActivity() {

    private lateinit var dni: String
    private lateinit var name: String
    private lateinit var address: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var user: User

    private val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerUser(binding)
    }

    private fun registerUser(binding: ActivitySignupBinding) {

        binding.btnRegisterUser.setOnClickListener {

            if (binding.dniUser.text.isNotEmpty() && binding.nameUser.text.isNotEmpty()
                && binding.addressUser.text.isNotEmpty() && binding.emailUser.text.isNotEmpty()
                && binding.passwordUser.text.isNotEmpty()) {

                dni = binding.dniUser.text.toString()
                name = binding.nameUser.text.toString()
                address = binding.addressUser.text.toString()
                email = binding.emailUser.text.toString()
                password = binding.passwordUser.text.toString()

                user = User(dni, name, address, email, password)

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

                    //SI EL EMAIL Y LA CONTRASEÑA DEL USUARIO NO EXISTEN EN LA BASE DE DATOS LO
                    //REGISTRAMOS
                    if (it.isSuccessful) {
                        Database.createUser(user, this)
                        navigateToMain(email)

                    } else

                        //SI EL USUARIO YA EXISTE EN LA BASE DE DATOS MOSTRAMOS UN MENSAJE DE ERROR
                        showMsg("ERROR", "EL EMAIL DE USUARIO NO ES VALIDO, YA EXISTE EN LA BASE DE DATOS")
                }

                //SI ALGUN CAMPO SE QUEDA VACIO EN EL REGISTRO, MOSTRAREMOS UN AVISO AL USUARIO
                //PARA QUE RELLENE TODOS LOS CAMPOS
            } else
                emptyToast()

        }
    }

    private fun navigateToMain(emailUser: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            //ENVIAMOS EL EMAIL DEL USUARIO DEBIDO A QUE CADA USUARIO TENDRA
            //SUS PROPIAS MASCOTAS
            putExtra("email", emailUser)
        }
        startActivity(intent)
    }
}