package com.example.zventapp_tiendavirtual.Cliente

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.zventapp_tiendavirtual.Cliente.Bottom_Nav_Fragments_Cliente.FragmentMisOrdenesC
import com.example.zventapp_tiendavirtual.Cliente.Bottom_Nav_Fragments_Cliente.FragmentTiendaC
import com.example.zventapp_tiendavirtual.Cliente.Nav_Fragments_Cliente.FragmentInicioC
import com.example.zventapp_tiendavirtual.Cliente.Nav_Fragments_Cliente.FragmentMiPerfilC
import com.example.zventapp_tiendavirtual.R
import com.example.zventapp_tiendavirtual.SeleccionarTipoActivity
import com.example.zventapp_tiendavirtual.databinding.ActivityMainClienteBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivityCliente : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainClienteBinding
    private var firebaseAuth : FirebaseAuth ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        firebaseAuth = FirebaseAuth.getInstance()
        comprobarSesion()

        binding.navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        replaceFragment(FragmentInicioC())

    }

    private fun comprobarSesion() {
        if (firebaseAuth!!.currentUser == null){
            startActivity(Intent(applicationContext, SeleccionarTipoActivity::class.java))
            finishAffinity()
        } else {
            Toast.makeText(applicationContext, "Cliente en linea.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cerrarSesion() {
        firebaseAuth!!.signOut()
        startActivity(Intent(applicationContext, SeleccionarTipoActivity::class.java))
        Toast.makeText(applicationContext, "Cerrando Sesión", Toast.LENGTH_SHORT).show()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.navFragment, fragment)
            .commit()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.op_inicio_c ->{
                replaceFragment(FragmentInicioC())
            }
            R.id.op_mi_perfil_c ->{
                replaceFragment(FragmentMiPerfilC())
            }
            R.id.op_cerrar_sesion_c ->{
                cerrarSesion()
            }
            R.id.op_tienda_c ->{
                replaceFragment(FragmentTiendaC())
            }
            R.id.op_mis_ordenes_c ->{
                replaceFragment(FragmentMisOrdenesC())
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}