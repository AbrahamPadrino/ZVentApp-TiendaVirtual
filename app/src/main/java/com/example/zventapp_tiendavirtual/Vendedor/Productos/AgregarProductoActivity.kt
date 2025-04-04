package com.example.zventapp_tiendavirtual.Vendedor.Productos

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.zventapp_tiendavirtual.Adaptadores.AdaptadorImagenSeleccionada
import com.example.zventapp_tiendavirtual.Constantes
import com.example.zventapp_tiendavirtual.Modelos.ModeloCategoria
import com.example.zventapp_tiendavirtual.Modelos.ModeloImagenSeleccionada
import com.example.zventapp_tiendavirtual.databinding.ActivityAgregarProductoBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AgregarProductoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgregarProductoBinding
    private var imagenUri : Uri? = null  // Imagen Seleccionada desde Galeria o Camara

    private lateinit var imagenSelecArrayList : ArrayList<ModeloImagenSeleccionada>
    private lateinit var adaptadorImagenSel : AdaptadorImagenSeleccionada

    private lateinit var categoriasArrayList : ArrayList<ModeloCategoria>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cargar Categorias en el TextView
        cargarCategorias()

        imagenSelecArrayList = ArrayList()

        binding.imgAgregarProducto.setOnClickListener {
            seleccionarImg()
        }

        binding.etCategoria.setOnClickListener {
            selectCategoria()
        }

        cargarImagenes()

    }

    private fun cargarCategorias() {
        categoriasArrayList = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("Categorias").orderByChild("categoria")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               categoriasArrayList.clear()
                // Leer en Tiempo Real los registros de la Base de Datos en la Tabla "Categorias"
                for (ds in snapshot.children){
                    val modelo = ds.getValue(ModeloCategoria::class.java)
                    categoriasArrayList.add(modelo!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

   // Visualizar las Categorias  a través de un AlertDialog
    private var idCat = ""
    private var tituloCat = ""
    private fun selectCategoria() {
        val categoriasArray = arrayOfNulls<String>(categoriasArrayList.size)

        for (i in categoriasArray.indices){
            categoriasArray[i] = categoriasArrayList[i].categoria
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Seleccione la Categoría")
            .setItems(categoriasArray){dialog, witch ->

                idCat = categoriasArrayList[witch].id
                tituloCat = categoriasArrayList[witch].categoria
                binding.etCategoria.text = tituloCat
            }
            .show()
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