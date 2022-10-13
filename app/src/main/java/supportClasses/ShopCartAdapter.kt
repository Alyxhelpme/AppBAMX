package supportClasses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.itesm.bamx.R

class ShopCartAdapter(var productos : ArrayList<String>,
                       var prices : ArrayList<String>,
                       var cantidad : ArrayList<Int>,
                       var listener : View.OnClickListener) :
    RecyclerView.Adapter<ShopCartAdapter.CartViewHolder>(){

        class CartViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

                var nombre: TextView
                var price: TextView
                var cantidades: TextView
                var imagen: ImageView

                init {

                    nombre = itemView.findViewById(R.id.idNombre)
                    price = itemView.findViewById(R.id.idPrice)
                    cantidades = itemView.findViewById(R.id.idQuantity)
                    imagen = itemView.findViewById(R.id.idImagen)

                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}
