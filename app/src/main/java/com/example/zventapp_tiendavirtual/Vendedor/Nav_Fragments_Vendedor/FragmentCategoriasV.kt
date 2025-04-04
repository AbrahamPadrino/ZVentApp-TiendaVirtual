package com.example.zventapp_tiendavirtual.Vendedor.Nav_Fragments_Vendedor

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.zventapp_tiendavirtual.databinding.FragmentCategoriasVBinding
import com.google.firebase.database.FirebaseDatabase


class FragmentCategoriasV : Fragment() {

    private lateinit var binding: FragmentCategoriasVBinding
    private lateinit var mContext: Context

    private lateinit var progressDialog: ProgressDialog


    // Initialize mContext
    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoriasVBinding.inflate(inflater, container, false)

        // Configura el ProgressDialog
        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Espere por favor...")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnAgregarCategoria.setOnClickListener {
            validarInfo()
        }


        return binding.root


    }

    private var categoria = ""
    private fun validarInfo() {
        categoria = binding.etCategoria.text.toString().trim()
        if (categoria.isEmpty()) {
            binding.etCategoria.error = "Ingrese categoria"
            binding.etCategoria.requestFocus()
        } else {
            agregarCategoriaBD()
        }
    }

    private fun agregarCategoriaBD() {

        progressDialog.setMessage("Agregando Categoria...")
        progressDialog.show()

        val ref = FirebaseDatabase.getInstance().getReference("Categorias")
        val keyId = ref.push().key

        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "${keyId}"
        hashMap["categoria"] = "${categoria}"

        ref.child(keyId!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(context, "Categoria Agregada", Toast.LENGTH_SHORT).show()
                binding.etCategoria.setText("")

            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(
                    context,
                    "Falló el registro debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

}
