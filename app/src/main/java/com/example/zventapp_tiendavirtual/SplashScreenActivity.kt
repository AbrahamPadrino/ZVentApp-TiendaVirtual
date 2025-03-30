package com.example.zventapp_tiendavirtual


import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.example.zventapp_tiendavirtual.Cliente.MainActivityCliente
import com.example.zventapp_tiendavirtual.Vendedor.MainActivityVendedor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SplashScreenActivity : AppCompatActivity() {

    // Declaraciones
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        // Crear la instancia
        firebaseAuth = FirebaseAuth.getInstance()

        verBienvenida()

    }

    private fun verBienvenida() {
        object : CountDownTimer(3000, 1000) {

            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                comprobarTipoUsuario()
            }

        }.start()
    }

    // Comprobar el tipo de usuario
    private fun comprobarTipoUsuario() {

        val firebaseUser = firebaseAuth.currentUser

        if (firebaseUser == null) {
            startActivity(Intent(this, SeleccionarTipoActivity::class.java))
        } else {
            val reference = FirebaseDatabase.getInstance().getReference("Usuarios")
            reference.child(firebaseUser.uid)
                .addListenerForSingleValueEvent(object  : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val tipoUsuario = snapshot.child("tipoUsuario").value.toString()

                        if (tipoUsuario == "vendedor") {
                            startActivity(Intent(this@SplashScreenActivity, MainActivityVendedor::class.java))
                            finishAffinity()
                        } else if (tipoUsuario == "cliente") {
                            startActivity(Intent(this@SplashScreenActivity, MainActivityCliente::class.java))
                            finishAffinity()

                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                } )
        }
    }
}