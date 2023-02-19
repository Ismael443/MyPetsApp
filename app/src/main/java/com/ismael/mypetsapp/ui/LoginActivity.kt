package com.ismael.mypetsapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.ismael.mypetsapp.databinding.ActivityLoginBinding
import com.ismael.mypetsapp.emptyToast
import com.ismael.mypetsapp.showMsg

class LoginActivity : AppCompatActivity() {

    private lateinit var emailUser: String
    private lateinit var passwordUser: String

    private val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        login(binding)
    }

    private fun login(binding: ActivityLoginBinding) {

        binding.btnIniciarSesion.setOnClickListener {

            //CUANDO PULSEMOS INICIAR SESION, COMPROBAMOS SI LOS CAMPOS EMAIL Y CONTRASEÑA NO ESTAN VACIOS
            if (binding.emailUser.text.isNotEmpty() && binding.passwordUser.text.isNotEmpty()) {

                emailUser = binding.emailUser.text.toString()
                passwordUser = binding.passwordUser.text.toString()

                //SI NO ESTAN VACIOS, RECOGEMOS EL TEXTO ESCRITO EN LOS CAMPOS Y COMPROBAMOS SI LOS DATOS SON CORRECTOS

                auth.signInWithEmailAndPassword(emailUser, passwordUser).addOnCompleteListener {

                    //SI LOS DATOS SON CORRECTOS ENTRAMOS EN LA APLICACIÓN
                    if (it.isSuccessful)
                        navigateToMain(emailUser)
                    else
                        showMsg("ERROR", "EMAIL O CONTRASEÑA INCORRECTOS")

                }

                //SI LOS CAMPOS ESTAN VACIOS, MOSTRAMOS UNA ADVERTENCIA DE CAMPO VACIO
            } else {
                emptyToast()
            }
        }

        //SI EL USUARIO QUE ENTRE EN LA APLICACION NO TIENE UNA CUENTA, PULSARA EN EL BOTON
        //DE REGISTRO E IRA AL SIGNUPACTIVITY PARA COMPLETAR EL REGISTRO
        binding.btnAccesoRegistro.setOnClickListener {
            navigateToSignUp()
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

    private fun navigateToSignUp() {
        val intent = Intent(this, SignUpActivity::class.java).apply {}
        startActivity(intent)
    }
}