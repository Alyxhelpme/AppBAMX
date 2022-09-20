package mx.itesm.bamx.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.itesm.bamx.PagoActivity
import supportClasses.DonationAdapter
import mx.itesm.bamx.R
import mx.itesm.bamx.SearchCenterActivity
import mx.itesm.bamx.carrito


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DonationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DonationFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var recyclerView : RecyclerView

    lateinit var nombres : ArrayList<String>
    lateinit var precios : ArrayList<String>

    lateinit var pagarButton : Button

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
        Toast.makeText(activity,
                        precios[position],
                        Toast.LENGTH_SHORT).show()

        carrito = precios[position].toInt()
        Log.d("CARRITO", carrito.toString())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_donation, container, false)
        pagarButton = view.findViewById(R.id.button)
        pagarButton.setOnClickListener { (goPay()) }

        // gui
        recyclerView = view.findViewById(R.id.itemsRV) // this may not work

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
                Log.d(
                    "FIRESTORE", "${documentoActual.id}"
                )
                var precio = documentoActual.get("precio")
                precios.add(precio.toString())
                nombres.add(documentoActual.get("producto").toString())
                Log.d("PRECIOS: ", precios.toString())
                Log.d("NOMBRES: ", nombres.toString())






                Log.d(
                    "FIRESTORE", "${documentoActual.get("precio")}"
                )
                Log.d(
                    "FIRESTORE",   "${documentoActual.getString("producto")}"
                )

                // datos -> gui
                // creador adaptador
                val adapter = DonationAdapter(nombres, precios, this)

                recyclerView.adapter = adapter

            }
        }.addOnFailureListener{ error ->
            Log.e("FIRESTORE", "error in query: $error")
        }


        return view
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
        val intent = Intent(requireActivity(), PagoActivity::class.java)
        startActivity(intent)
    }

}