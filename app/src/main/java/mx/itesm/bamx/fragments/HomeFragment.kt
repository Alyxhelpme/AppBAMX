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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.itesm.bamx.R
import supportClasses.DonationAdapter
import supportClasses.TweetAdapter

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
    lateinit var tweetsRecyclerView : RecyclerView
    lateinit var tweetUserNamesArray : ArrayList<String>
    lateinit var tweetTextsArray : ArrayList<String>
    lateinit var tweetUrlsArray : ArrayList<String>

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


        tweetUserNamesArray = ArrayList()
        tweetTextsArray = ArrayList()
        tweetUrlsArray = ArrayList()
        tweetsRecyclerView = view.findViewById(R.id.tweetsRecyclerView)

        val lLM = LinearLayoutManager(activity)
        lLM.orientation = LinearLayoutManager.VERTICAL
        tweetsRecyclerView.layoutManager = lLM


        twitterTask.addOnSuccessListener { result ->

            var count = 0
            tweetUserNamesArray = ArrayList()
            tweetTextsArray = ArrayList()
            tweetUrlsArray = ArrayList()

            for (document in result){

                /*Log.d("FIRESTORE", document.data.toString())*/
                val name = document.get("tweetUserName")
                /*Log.d("FIRESTORE", "Al fin encontre el nombreee: $name")*/
                val url = document.get("tweetUrl")
                /*Log.d("FIRESTORE", "Al fin encontre el url: $url")*/
                val text = document.get("tweetText")
                /*Log.d("FIRESTORE", "Al fin encontre el textooo: $text")*/
                val picAddress = document.get("profilePicAddress")
                /*Log.d("FIRESTORE", "Al fin encontre la imageeen: $picAddress")*/
                Log.d("TYPE", "$name, Tipo de name: ${name?.javaClass?.name}")
                Log.d("TYPE", "$url, Tipo de url: ${url?.javaClass?.name}")
                Log.d("TYPE", "$text, Tipo de texto: ${text?.javaClass?.name}")

                tweetUserNamesArray.add(document.get("tweetUserName").toString())
                tweetTextsArray.add(document.get("tweetText").toString())
                tweetUrlsArray.add(document.get("tweetUrl").toString())
                count += 1
                val tweetAdapter = TweetAdapter(tweetUserNamesArray, tweetTextsArray, tweetUrlsArray)
                tweetsRecyclerView.adapter = tweetAdapter




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