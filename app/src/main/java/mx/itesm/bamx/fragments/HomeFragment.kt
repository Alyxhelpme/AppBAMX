package mx.itesm.bamx.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.itesm.bamx.R
import supportClasses.DonationAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
// comment
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var progressBar1 : ProgressBar
    lateinit var progressBar2 : ProgressBar
    lateinit var progressBar3: ProgressBar
    private lateinit var twitterWebView : WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var aceite = 0
        var arroz = 0
        var garbanzo = 0
        var papel = 0
        var frijol = 0
        var canasta = 0
        var limpieza = 0
        var azucar = 0

        val currentProgress1 = arroz
        val currentProgress2 = 50
        val currentProgress3 = 50

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        progressBar1 = view.findViewById(R.id.progressBar1)
        progressBar1.max = 100
        progressBar1.min = 0

        progressBar1.progress = currentProgress1

        progressBar2 = view.findViewById(R.id.progressBar2)
        progressBar2.max = 200
        progressBar2.min = 0

        progressBar2.progress = currentProgress2

        progressBar3 = view.findViewById(R.id.progressBar3)
        progressBar3.max = 50
        progressBar3.min = 0

        progressBar3.progress = currentProgress3

        val coleccion = Firebase.firestore.collection("TotalDonations")


        val queryTask = coleccion.get()
        queryTask.addOnSuccessListener { result ->
            // recorrer datos
            Toast.makeText(
                this.context,
                "QUERY EXITOSO",
                Toast.LENGTH_SHORT
            ).show()
            for (documentoActual in result) {
                Log.d(
                    "FIRESTORE", "${documentoActual.id}"
                )
                var aceite = documentoActual.get("Aceite")
                var arroz = documentoActual.get("Arroz").toString().toInt()
                progressBar1.progress = arroz
                var garbanzo = documentoActual.get("Garbanzo").toString().toInt()
                //progressBar2.progress = garbanzo
                var papel = documentoActual.get("PapelHigienico").toString().toInt()
                progressBar2.progress = papel

                var frijol = documentoActual.get("Frijol")
                var canasta = documentoActual.get("CanastaBasica")
                var limpieza = documentoActual.get("ProductosDeLimpieza")
                var azucar = documentoActual.get("Azucar").toString().toInt()
                progressBar3.progress = azucar
            }
        }.addOnFailureListener{ error ->
            Log.e("FIRESTORE", "error in query: $error")
        }

        val twitterCollection = Firebase.firestore.collection("newsTweet")
        val twitterTask = twitterCollection.get()

        twitterWebView = view.findViewById(R.id.webView)
        twitterWebView.settings.javaScriptEnabled = true

        twitterTask.addOnSuccessListener { result ->
            for (document in result){
                var tweetUrl = document.get("twitterUrl")
                twitterWebView.loadUrl(tweetUrl.toString())
            }
        }.addOnFailureListener { error ->
            Log.e("FIRESTORE", "error in query: $error")
        }


        // Inflate the layout for this fragment
        return view



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}