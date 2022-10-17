package supportClasses

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import mx.itesm.bamx.R
import mx.itesm.bamx.carrito
import mx.itesm.bamx.fragments.DonationFragment
import kotlin.concurrent.fixedRateTimer

class DonationAdapter(var productos : ArrayList<String>,
                      var prices : ArrayList<String>,
                      var cantidad : ArrayList<Int>,
                      var listener : View.OnClickListener,
var donationListener: DonationListener) :
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
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DonationViewHolder, position: Int) {

        var quantity : Int = 0
        holder.nombre.text = productos[position]
        holder.price.text = "$" + prices[position] + " MXN"
        holder.addButton.setOnClickListener {
            quantity += 1
            cantidad[position] = quantity
            holder.cantidades.text = (quantity.toString())
            getTotal(position)
            donationListener.updateCount()

        }

        holder.deleteButton.setOnClickListener{
            if (quantity > 0){

                quantity -= 1
                cantidad[position] = quantity
                holder.cantidades.text = (quantity.toString())
                donationListener.updateCount()
            }
        }
    }

    // obtener total de elementos
    override fun getItemCount(): Int {
        return productos.size
    }

    fun getPrice(position: Int): Int {

        return prices[position].toInt()

    }

    fun getProductCount(): Int{
        return cantidad.size
    }

    fun getTotal(position: Int): Int {
        return prices[position].toInt() * cantidad[position].toInt()
    }

    public interface DonationListener {

        fun updateCount();
    }
}

