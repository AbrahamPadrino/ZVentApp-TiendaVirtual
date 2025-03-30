package com.example.zventapp_tiendavirtual.Vendedor.Productos

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.zventapp_tiendavirtual.Adaptadores.AdaptadorImagenSeleccionada
import com.example.zventapp_tiendavirtual.Constantes
import com.example.zventapp_tiendavirtual.Modelos.ModeloImagenSeleccionada
import com.example.zventapp_tiendavirtual.databinding.ActivityAgregarProductoBinding
import com.github.dhaval2404.imagepicker.ImagePicker

class AgregarProductoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgregarProductoBinding
    private var imagenUri : Uri? = null  // Imagen Seleccionada desde Galeria o Camara

    private lateinit var imagenSelecArrayList : ArrayList<ModeloImagenSeleccionada>
    private lateinit var adaptadorImagenSel : AdaptadorImagenSeleccionada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imagenSelecArrayList = ArrayList()

        binding.imgAgregarProducto.setOnClickListener {
            seleccionarImg()
        }

        cargarImagenes()

    }

    private fun cargarImagenes() {
        adaptadorImagenSel = AdaptadorImagenSeleccionada(this, imagenSelecArrayList)
        binding.RVImagenesProducto.adapter = adaptadorImagenSel
    }

    private fun seleccionarImg() {
        ImagePicker.with(this) // pasar el Contexto
            .crop() // Recortar Imagen
            .compress(1024) // Comprimir Imagen
            .maxResultSize(1080, 1080) // Resolución Imagen
            .createIntent { intent->
                //Función para Obtener la Imagen
                resultadoImg.launch(intent)
            }

    }

    private val resultadoImg =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){resultado->
            if (resultado.resultCode == Activity.RESULT_OK){
                val data = resultado.data
                imagenUri = data!!.data
                val tiempo = "${Constantes().obtenerTiempo()}"

                val modeloImgSel = ModeloImagenSeleccionada(tiempo, imagenUri, null, false)
                imagenSelecArrayList.add(modeloImgSel)
                cargarImagenes()

            } else {
                Toast.makeText(this, "Acción Cancelada", Toast.LENGTH_SHORT).show()
            }
        }
}