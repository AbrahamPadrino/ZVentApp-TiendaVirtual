package com.example.zventapp_tiendavirtual.Vendedor

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.zventapp_tiendavirtual.R
import com.example.zventapp_tiendavirtual.SeleccionarTipoActivity
import com.example.zventapp_tiendavirtual.Vendedor.Bottom_Nav_Fragments_Vendedor.FragmentMisProductosV
import com.example.zventapp_tiendavirtual.Vendedor.Bottom_Nav_Fragments_Vendedor.FragmentOrdenesV
import com.example.zventapp_tiendavirtual.Vendedor.Nav_Fragments_Vendedor.FragmentCategoriasV
import com.example.zventapp_tiendavirtual.Vendedor.Nav_Fragments_Vendedor.FragmentInicioV
import com.example.zventapp_tiendavirtual.Vendedor.Nav_Fragments_Vendedor.FragmentMiTiendaV
import com.example.zventapp_tiendavirtual.Vendedor.Nav_Fragments_Vendedor.FragmentReseniasV
import com.example.zventapp_tiendavirtual.databinding.ActivityMainVendedorBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivityVendedor : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

     private lateinit var binding: ActivityMainVendedorBinding
     private var firebaseAuth : FirebaseAuth?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainVendedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        firebaseAuth = FirebaseAuth.getInstance()
        comprobarSesion()

        binding.navigationView.setNavigationItemSelectedListener(this)

        val toogle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        
        binding.drawerLayout.addDrawerListener(toogle)
        toogle.syncState()
        
        replaceFragment(FragmentInicioV())
        binding.navigationView.setCheckedItem(R.id.op_inicio_v)
        

    }

    private fun comprobarSesion() {
        if (firebaseAuth!!.currentUser == null){
            startActivity(Intent(applicationContext, SeleccionarTipoActivity::class.java))
            Toast.makeText(applicationContext, "Vendedor No Registrado.", Toast.LENGTH_SHORT).show()


        } else {
            Toast.makeText(applicationContext, "Vendedor en linea.", Toast.LENGTH_SHORT).show()
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
            R.id.op_inicio_v ->{
                replaceFragment(FragmentInicioV())
            }
            R.id.op_categorias_v ->{
                replaceFragment(FragmentCategoriasV())
            }
            R.id.op_mi_tienda_v ->{
                replaceFragment(FragmentMiTiendaV())
            }
            R.id.op_resenia_v ->{
                replaceFragment(FragmentReseniasV())
            }
            R.id.op_cerrar_sesion_v ->{
                cerrarSesion()
            }
            R.id.op_mis_productos_v ->{
                replaceFragment(FragmentMisProductosV())
            }
            R.id.op_mis_ordenes_v ->{
                replaceFragment(FragmentOrdenesV())
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true

    }
}