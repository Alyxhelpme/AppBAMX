package mx.itesm.bamx.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.itesm.bamx.*
import supportClasses.DonationAdapter
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DonationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DonationFragment : Fragment(), View.OnClickListener, DonationAdapter.DonationListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var recyclerView : RecyclerView

    lateinit var nombres : ArrayList<String>
    lateinit var precios : ArrayList<String>

    lateinit var pagarButton : Button
    lateinit var carButton : Button
    lateinit var totalTV : TextView
    lateinit var cantidad : ArrayList<Int>
    private val items= arrayOf("1kg de arroz + 1kg de frijoles", "3kg de tomates", "Garrafón de agua", "3 latas de atún")
    //private val prices= arrayOf("$70", "$120", "$80", "$30")

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onClick(item_list: View) {

        val position = recyclerView.getChildLayoutPosition(item_list)
        Toast.makeText(activity, "${(precios[position].toInt()*cantidad[position])}", Toast.LENGTH_SHORT).show()
        //Toast.makeText(activity, "${(total)}", Toast.LENGTH_SHORT).show()
        //carrito = totalPrice()
        recyclerView.adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_donation, container, false)
        pagarButton = view.findViewById(R.id.becomeAssociateButton)
        pagarButton.setOnClickListener { (goPay()) }

        carButton = view.findViewById(R.id.carbtn)
        carButton.setOnClickListener{(goCar())}

        // gui
        recyclerView = view.findViewById(R.id.itemsRV) // this may not work
        totalTV = view.findViewById(R.id.totalTV)



        //layout manager
        val llm = LinearLayoutManager(activity)
        llm.orientation = LinearLayoutManager.VERTICAL

        //asignamos llm a GUI
        recyclerView.layoutManager = llm

        // Inflate the layout for this fragment

        // datos (WE JUST GOTTA MAKE IT WORK WITH FIREBASE, BUT ALL THE STRUCTURE IS DONE)
        //        we also gotta make them clickable but it ain't hard, found an indian dude
        //        that explains how to do it
        nombres = ArrayList()

        //nombres.add(items[0])
        //nombres.add(items[1])
        //nombres.add(items[2])
        //nombres.add(items[3])

        precios = ArrayList()
        cantidad = ArrayList()
        // query to solicite data and obtain information
        val coleccion = Firebase.firestore.collection("ProductList")

        val queryTask = coleccion.get()

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
                precios.add(precio.toString())
                nombres.add(documentoActual.get("producto").toString())
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
                val adapter = DonationAdapter(nombres, precios, cantidad,this, this)

                recyclerView.adapter = adapter

            }
        }.addOnFailureListener{ error ->
            Log.e("FIRESTORE", "error in query: $error")
        }


        return view
    }

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {

        Timer("fun").schedule(5 * 1000) {
            //totalPrice()
        }

        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DonationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DonationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }

    private fun goPay() {
        carrito = totalPrice()
        val date = Date()

        val localDate: LocalDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val email = user
        donation = hashMapOf(
            "Id" to UUID.randomUUID().toString(),
            "Email" to email,
            "Fecha" to localDate.toString(),
            "Canasta básica" to (if(cantidad[0] == 1){
                ((cantidad[0]).toString() + " Unidad")}
                else {
                    ((cantidad[0]).toString() + " Unidades")
                }),
            "Arroz" to ((cantidad[1]* 100).toString() + " KG"),
            "Frijol" to ((cantidad[2]*100).toString() + " KG"),
            "Garbanzo" to ((cantidad[3]*100).toString() + " KG"),
            "Azucar" to ((cantidad[4]* 100).toString() + " KG"),
            "Aceite" to ((cantidad[5]*100).toString() + " LT"),
            "Papel higiénico" to (if(cantidad[6] == 1){
                ((cantidad[6]).toString() + " Unidad")}
                else {
                    ((cantidad[6]).toString() + " Unidades")
                })
            ,
            "Productos de limpieza" to (if(cantidad[7] == 1){
                ((cantidad[7]).toString() + " Unidad")}
                else {
                ((cantidad[7]).toString() + " Unidades")
                }),
            "Precio total" to (carrito.toString() + " MXN")
        )

        if (carrito > 0){
            val intent = Intent(requireActivity(), PagoActivity::class.java)
            startActivity(intent)
        }
        else {
            Toast.makeText(this.context,"Agrega items para donar", Toast.LENGTH_SHORT)
                .show()
        }

    }

    private fun goCar() {

        carrito = totalPrice()
        carItems()
        val intent = Intent(requireActivity(), CarActivity::class.java)
        startActivity(intent)
    }

    fun totalPrice(): Int {
        var total : Int = 0
        for (item in 0 until cantidad.size){
            total += precios[item].toInt() * cantidad[item]
        }
        totalTV.text = "Total: $" + total.toString() + " MXN"
        return total
    }

    fun carItems() {
        cantidadC = ArrayList()
        preciosC = ArrayList()
        nombresC = ArrayList()

        for (item in 0 until cantidad.size) {
            if (cantidad[item] == 0) {
                continue
            }
            nombresC.add(nombres[item])
            preciosC.add(precios[item])
            cantidadC.add(cantidad[item])
        }
    }

    override fun updateCount() {
        totalPrice();
    }

}