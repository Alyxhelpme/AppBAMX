package mx.itesm.bamx.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ProgressBar
import android.widget.TextView
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
    lateinit var progressText1: TextView
    lateinit var progressText2: TextView
    lateinit var progressText3: TextView
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

        val currentProgress1 = 0
        val currentProgress2 = 0
        val currentProgress3 = 0

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        progressText1 = view.findViewById(R.id.goal1)
        progressBar1 = view.findViewById(R.id.progressBar1)
        progressBar1.max = 100
        progressBar1.min = 0

        progressBar1.progress = currentProgress1

        progressText2 = view.findViewById(R.id.goal2)
        progressBar2 = view.findViewById(R.id.progressBar2)
        progressBar2.max = 200
        progressBar2.min = 0

        progressBar2.progress = currentProgress2

        progressText3 = view.findViewById(R.id.goal3)
        progressBar3 = view.findViewById(R.id.progressBar3)
        progressBar3.max = 50
        progressBar3.min = 0

        progressBar3.progress = currentProgress3

        val coleccion = Firebase.firestore.collection("Metas")
        val coleccion2 = Firebase.firestore.collection("LimitesMetas")

        val queryTask2 = coleccion2.get()
        queryTask2.addOnSuccessListener { result2 ->
            // recorrer datos
            for (documentoActual in result2) {
                Log.d(
                    "FIRESTORE PPI", "${documentoActual.id}"
                )
                var arrozMeta = documentoActual.get("Arroz").toString().toInt()
                progressBar1.max = arrozMeta
                progressText1.text = arrozMeta.toString() + "kg de arroz"

                var rollosMeta = documentoActual.get("Rollos").toString().toInt()
                progressBar2.max = rollosMeta
                progressText2.text = rollosMeta.toString() + " rollos de papel higiénico"

                var aceiteMeta = documentoActual.get("Aceite").toString().toInt()

                progressBar3.max = aceiteMeta
                progressText3.text = aceiteMeta.toString() + " lt de aceite"
            }
        }


        val queryTask = coleccion.get()
        queryTask.addOnSuccessListener { result ->
            // recorrer datos
            for (documentoActual in result) {
                Log.d(
                    "FIRESTORE", "${documentoActual.id}"
                )
                var arroz = documentoActual.get("Arroz").toString().toInt()
                progressBar1.progress = arroz
                var rollos = documentoActual.get("Rollos").toString().toInt()
                progressBar2.progress = rollos
                var aceite = documentoActual.get("Aceite").toString().toInt()
                progressBar3.progress = aceite
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

            for (document in result) {

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
                val tweetAdapter =
                    TweetAdapter(tweetUserNamesArray, tweetTextsArray, tweetUrlsArray)
                tweetsRecyclerView.adapter = tweetAdapter
            }
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