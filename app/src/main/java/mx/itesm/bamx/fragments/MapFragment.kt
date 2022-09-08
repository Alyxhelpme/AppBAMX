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
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.DocumentChange
import mx.itesm.bamx.R
import mx.itesm.bamx.SearchCenterActivity


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map : GoogleMap
    private lateinit var collection : CollectionReference
    private lateinit var searchButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         collection = Firebase.firestore.collection("centers")
        fetchCenters()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        searchButton = view.findViewById(R.id.searchButton)
        searchButton.setOnClickListener{goSearch()}
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        createFragment()
    }


    private fun createFragment(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
        //Toast.makeText(this.context,"HOLA", Toast.LENGTH_SHORT).show()
    }

    private fun fetchCenters(){
        val requestCenters = collection.get()

        requestCenters.addOnSuccessListener {
            result ->
            for(document in result){
                val lat = document.getDouble("lat")
                val lng = document.getDouble("lng")
                var coordinates : LatLng
                if(lat != null && lng != null){
                    coordinates = LatLng(lat, lng)
                    map.addMarker(MarkerOptions().position(coordinates).title("Center"))
                }
            }
        }. addOnFailureListener{
            error ->
            Log.e("Firestore", "error: $error")
        }
    }

    fun goSearch(){
        val intent = Intent(requireActivity(), SearchCenterActivity::class.java)
        startActivity(intent)
    }
}