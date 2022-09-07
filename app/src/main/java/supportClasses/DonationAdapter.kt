package supportClasses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.itesm.bamx.R

class DonationAdapter(var productos : ArrayList<String>, var prices : ArrayList<String>) :
    RecyclerView.Adapter<DonationAdapter.DonationViewHolder>(){

    // internal class that will work as view holder (like a binding stuff)

    class DonationViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

                var nombre : TextView
                var price : TextView
                var imagen : ImageView

                init {

                    nombre = itemView.findViewById(R.id.idNombre)
                    price = itemView.findViewById(R.id.idPrice)
                    imagen = itemView.findViewById(R.id.idImagen)

                }

    }

    // momento en que creamos la lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)

        return DonationViewHolder(view)

    }

    // asociamos una vista en particular con un elemento de nuestra fuente
    // de datos
    override fun onBindViewHolder(holder: DonationViewHolder, position: Int) {

        holder.nombre.text = productos[position]
        holder.price.text = prices[position]

    }

    // obtener total de elementos
    override fun getItemCount(): Int {
        return productos.size
    }

}