package supportClasses

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import mx.itesm.bamx.R
import mx.itesm.bamx.carrito
import mx.itesm.bamx.carritoItems
import mx.itesm.bamx.carritoPrices
import mx.itesm.bamx.carritoQuantities
import mx.itesm.bamx.fragments.DonationFragment
import kotlin.concurrent.fixedRateTimer

class DonationAdapter(var productos : ArrayList<String>,
                      var prices : ArrayList<String>,
                      var cantidad : ArrayList<Int>,
                      var listener : View.OnClickListener) :
    RecyclerView.Adapter<DonationAdapter.DonationViewHolder>(){

    // internal class that will work as view holder (like a binding stuff)

    class DonationViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
            var deleteButton = itemView.findViewById<Button>(R.id.deleteItem)
            var addButton = itemView.findViewById<Button>(R.id.addItem)
                var nombre : TextView
                var price : TextView
                var cantidades : TextView
                var imagen : ImageView

                init {

                    nombre = itemView.findViewById(R.id.idNombre)
                    price = itemView.findViewById(R.id.idPrice)
                    cantidades= itemView.findViewById(R.id.idQuantity)
                    imagen = itemView.findViewById(R.id.idImagen)

                }

    }

    // momento en que creamos la lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)

        view.setOnClickListener(listener)

        return DonationViewHolder(view)

    }

    // asociamos una vista en particular con un elemento de nuestra fuente
    // de datos
    override fun onBindViewHolder(holder: DonationViewHolder, position: Int) {

        var quantity : Int = 0
        holder.nombre.text = productos[position]
        holder.price.text = "$" + prices[position] + " MXN"
        holder.addButton.setOnClickListener{
            quantity += 1
            cantidad[position] = quantity
            holder.cantidades.text = (quantity.toString())
            getCarItem(position)
        }

        holder.deleteButton.setOnClickListener{
            if (quantity > 0){

                quantity -= 1
                cantidad[position] = quantity
                holder.cantidades.text = (quantity.toString())
                getCarItem(position)
            }
        }

    }

    // obtener total de elementos
    override fun getItemCount(): Int {
        return productos.size
    }

    fun getCarItem(position: Int): Int {
        carritoPrices[position] =prices[position].toInt() * cantidad[position]
        /*for (item in 0 until carritoItems.size){

            Log.d("ITEM:", carritoQuantities.toString())

        }*/
        if (carritoPrices[position] == 0){
            carritoItems[position] == ""
        }
        else {
            carritoItems[position] = productos[position]
        }
        return carritoPrices[position]
    }

    fun getProductCount(): Int{
        return cantidad.size
    }

}