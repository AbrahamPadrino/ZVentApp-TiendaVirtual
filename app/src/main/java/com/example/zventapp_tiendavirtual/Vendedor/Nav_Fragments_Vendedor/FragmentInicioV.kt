package com.example.zventapp_tiendavirtual.Vendedor.Nav_Fragments_Vendedor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.zventapp_tiendavirtual.R
import com.example.zventapp_tiendavirtual.Vendedor.Bottom_Nav_Fragments_Vendedor.FragmentMisProductosV
import com.example.zventapp_tiendavirtual.Vendedor.Bottom_Nav_Fragments_Vendedor.FragmentOrdenesV
import com.example.zventapp_tiendavirtual.Vendedor.Productos.AgregarProductoActivity
import com.example.zventapp_tiendavirtual.databinding.FragmentInicioVBinding


class FragmentInicioV : Fragment() {

    private lateinit var binding: FragmentInicioVBinding
    private lateinit var mContext : Context

    // Initialize mContext
    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)

    } //

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInicioVBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment


        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.op_mis_productos_v ->{
                    replaceFragment(FragmentMisProductosV())
                }
                R.id.op_mis_ordenes_v ->{
                    replaceFragment(FragmentOrdenesV())
                }
            }
            true
        }

        replaceFragment(FragmentMisProductosV())
        binding.bottomNavigation.selectedItemId = R.id.op_mis_productos_v

        binding.addFab.setOnClickListener {
            startActivity(Intent(context, AgregarProductoActivity::class.java))
        }

        return binding.root
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.bottomFragment, fragment)
            .commit()
    }

}