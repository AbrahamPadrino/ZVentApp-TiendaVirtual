package com.example.zventapp_tiendavirtual

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.zventapp_tiendavirtual.Cliente.LoginClienteActivity
import com.example.zventapp_tiendavirtual.Vendedor.LoginVendedorActivity
import com.example.zventapp_tiendavirtual.databinding.ActivitySeleccionarTipoBinding

class SeleccionarTipoActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySeleccionarTipoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeleccionarTipoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tipoVendedor.setOnClickListener {
            startActivity(Intent(applicationContext, LoginVendedorActivity::class.java))
        }

        binding.tipoCliente.setOnClickListener {
            startActivity(Intent(applicationContext, LoginClienteActivity::class.java))
        }
    }
}