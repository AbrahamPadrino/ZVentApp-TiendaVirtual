package com.example.zventapp_tiendavirtual.Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.zventapp_tiendavirtual.Modelos.ModeloImagenSeleccionada
import com.example.zventapp_tiendavirtual.R
import com.example.zventapp_tiendavirtual.databinding.ItemImagenesSeleccionadasBinding

class AdaptadorImagenSeleccionada(
    private val context : Context,
    private val imagenSelecArrayList : ArrayList<ModeloImagenSeleccionada>
): Adapter<AdaptadorImagenSeleccionada.HolderImagenSeleccionada>() {

    private lateinit var binding: ItemImagenesSeleccionadasBinding

    // Mostrar en Pantalla Cada Elemento que Contiene una Lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderImagenSeleccionada {
        binding = ItemImagenesSeleccionadasBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return HolderImagenSeleccionada(binding.root)
    }
    // Pintar en Pantalla la Informacion de cada Elemento de una Lista.
    override fun onBindViewHolder(holder: HolderImagenSeleccionada, position: Int) {

        val modelo = imagenSelecArrayList[position]

        val imagenUri = modelo.imagenUri

        // Leyendo la imagen(es)
        try {
            Glide.with(context)
                .load(imagenUri) // Cargar la imagen desde la URI
                .placeholder(R.drawable.item_imagen) // Mostrar un placeholder mientras se carga la imagen
                .into(holder.imagenItem) // Mostrar la imagen en el ImageView del ViewHolder


        } catch (e: Exception) {

        }

        // Evento para eliminar una imagen
        holder.btn_borrar.setOnClickListener {
            imagenSelecArrayList.remove(modelo)
            notifyDataSetChanged()
        }
    }

    // Retorna el Tamaño de la Lista
    override fun getItemCount(): Int {
        return imagenSelecArrayList.size
    }

    // Establece la instancia de las vistas del item
    inner class HolderImagenSeleccionada(itemView : View) : ViewHolder(itemView){
        var imagenItem = binding.imagenItem
        var btn_borrar = binding.borrarItem

    }



}