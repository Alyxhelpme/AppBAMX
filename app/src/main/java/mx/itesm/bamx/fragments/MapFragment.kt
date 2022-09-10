package mx.itesm.bamx.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import mx.itesm.bamx.R
import mx.itesm.bamx.SearchCenterActivity
import java.util.jar.Manifest


class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {

    private lateinit var map : GoogleMap
    private lateinit var collection : CollectionReference
    private lateinit var searchButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collection = Firebase.firestore.collection("centers")
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

    // ======================================= MAP METHODS =============================================
    private fun createFragment(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
        if(ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            map.isMyLocationEnabled = true
            map.setOnMyLocationButtonClickListener(this)
        } else {
            val permisos = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(permisos, 0)
        }
        val cameraPosition = LatLng(20.737122,-103.454266)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraPosition, 15f))
        fetchCenters()
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

    override fun onMyLocationButtonClick(): Boolean {
        map.animateCamera(CameraUpdateFactory.zoomIn())
        return false
    }

    private fun goSearch(){
        val intent = Intent(requireActivity(), SearchCenterActivity::class.java)
        startActivity(intent)
    }
}