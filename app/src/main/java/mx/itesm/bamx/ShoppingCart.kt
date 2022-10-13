package mx.itesm.bamx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import supportClasses.DonationAdapter
import mx.itesm.bamx.carritoItems
class ShoppingCart : AppCompatActivity() {

    lateinit var recyclerView : RecyclerView
    lateinit var nombres : ArrayList<String>
    lateinit var precios : ArrayList<String>
    lateinit var cantidad : ArrayList<Int>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.activity_shopping_cart, container, false)

        // gui
        recyclerView = view.findViewById(R.id.ItemsCart) // this may not work

        //layout manager
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL

        //asignamos llm a GUI
        recyclerView.layoutManager = llm

        // Inflate the layout for this fragment

        // datos (WE JUST GOTTA MAKE IT WORK WITH FIREBASE, BUT ALL THE STRUCTURE IS DONE)
        //        we also gotta make them clickable but it ain't hard, found an indian dude
        //        that explains how to do it
        nombres = ArrayList()

        precios = ArrayList()
        cantidad = ArrayList()



        for (item in 0 until carritoItems.size){
            precios.add(carritoPrices[item].toString())
            nombres.add(carritoItems[item])
            cantidad.add(carritoQuantities[item])

            val adapter = DonationAdapter(nombres, precios, cantidad,this)
        }
        /*
        queryTask.addOnSuccessListener { result ->
            // recorrer datos
            Toast.makeText(
                this.context,
                "QUERY EXITOSO",
                Toast.LENGTH_SHORT
            ).show()

            precios = ArrayList()
            for (documentoActual in result) {
                /*Log.d(
                    "FIRESTORE", "${documentoActual.id}"
                )*/
                var precio = documentoActual.get("precio")

                /*
                Log.d("PRECIOS: ", precios.toString())
                Log.d("NOMBRES: ", nombres.toString())

                Log.d(
                    "FIRESTORE", "${documentoActual.get("precio")}"
                )
                Log.d(
                    "FIRESTORE",   "${documentoActual.getString("producto")}"
                )*/
                // datos -> gui
                // creador adaptador
                cantidad.add(0)
                carritoPrices.add(0)
                carritoItems.add("none")
                val adapter = DonationAdapter(nombres, precios, cantidad,this)

                recyclerView.adapter = adapter

            }
        }.addOnFailureListener{ error ->
            Log.e("FIRESTORE", "error in query: $error")
        }
        */

        return view
    }


}