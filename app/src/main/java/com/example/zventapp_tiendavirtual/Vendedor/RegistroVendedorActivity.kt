package com.example.zventapp_tiendavirtual.Vendedor

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.zventapp_tiendavirtual.Constantes
import com.example.zventapp_tiendavirtual.databinding.ActivityRegistroVendedorBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistroVendedorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroVendedorBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroVendedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Crear la instancia
        firebaseAuth = FirebaseAuth.getInstance()

        // Configura el ProgressDialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor...")
        progressDialog.setCanceledOnTouchOutside(false)

        // Botón Registrar
        binding.btnRegistrarV.setOnClickListener {
            validarInformacion()
        }

    }

    private var nombres = ""
    private var email = ""
    private var password = ""
    private var cpassword = ""
    private fun validarInformacion() {
        nombres = binding.etNombresV.text.toString().trim()
        email = binding.etEmailV.text.toString().trim()
        password = binding.etPasswordV.text.toString().trim()
        cpassword = binding.etCPasswordV.text.toString().trim()
        // Validación
        if (nombres.isEmpty()) {
            binding.etNombresV.error = "Ingrese Nombres"
            binding.etNombresV.requestFocus()
        } else if (email.isEmpty()) {
            binding.etEmailV.error = "Ingrese Email"
            binding.etEmailV.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmailV.error = "Email No Es Válido"
            binding.etEmailV.requestFocus()
        } else if (password.isEmpty()) {
            binding.etPasswordV.error = "Ingrese Password"
            binding.etPasswordV.requestFocus()
        } else if (password.length < 6) {
            binding.etPasswordV.error = "Necesita 6 digitos"
            binding.etPasswordV.requestFocus()
        } else if (cpassword.isEmpty()) {
            binding.etCPasswordV.error = "Confirme Password"
            binding.etCPasswordV.requestFocus()
        } else if (password != cpassword) {
            binding.etPasswordV.error = "Password No Coincide"
            binding.etPasswordV.requestFocus()
        } else {
            registrarVendedor()
        }
    }

    private fun registrarVendedor() {
        progressDialog.setMessage("Creando Cuenta...")
        progressDialog.show()

        // En Firebase
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                insertarInfoBD()

            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this, "Falló el registro debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
                progressDialog.dismiss()
            }
    }

    private fun insertarInfoBD() {
        progressDialog.setMessage("Guardando información...")

        val uidBD = firebaseAuth.uid
        val nombreBD = nombres
        val emailBD = email
        val tiempoBD = Constantes().obtenerTiempo()

        val datosVendedor = HashMap<String, Any>()
        datosVendedor["uid"] = uidBD!!
        datosVendedor["nombres"] = nombreBD
        datosVendedor["email"] = emailBD
        datosVendedor["tipoUsuario"] = "vendedor"
        datosVendedor["tiempo_registro"] = tiempoBD

        val references = FirebaseDatabase.getInstance().getReference("Usuarios")
        references.child(uidBD)
            .setValue(datosVendedor)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivityVendedor::class.java))
                finish()

            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(
                    this, "Falló el registro debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

    }


}