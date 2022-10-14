package supportClasses
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.itesm.bamx.R
import mx.itesm.bamx.cantidadC

class CarAdapter(
    var nombresC : ArrayList<String>,
    var preciosC : ArrayList<String>,
    var CantidadC : ArrayList<Int>,
    var listener : View.OnClickListener,
    var carListener : CarListener) :
    RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

        class CarViewHolder(itemView: View) :
                RecyclerView.ViewHolder(itemView) {

                    var nombre : TextView
                    var price : TextView
                    var cantidad : TextView

                    init {
                        nombre = itemView.findViewById(R.id.idNombre)
                        price = itemView.findViewById(R.id.idPrice)
                        cantidad = itemView.findViewById(R.id.idQuantity)
                    }

                }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_car, parent, false)
            view.setOnClickListener(listener)
            return CarViewHolder(view)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
            holder.nombre.text = nombresC[position]
            holder.price.text =  "$" +(preciosC[position].toInt() * cantidadC[position]).toString() + " MXN"
            holder.cantidad.text = cantidadC[position].toString()
        }

    override fun getItemCount(): Int {
        return nombresC.size
    }

    public interface CarListener {
        fun updateCount();
    }
}

