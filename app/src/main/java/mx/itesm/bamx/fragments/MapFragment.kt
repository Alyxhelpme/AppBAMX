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
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import mx.itesm.bamx.MainActivity
import mx.itesm.bamx.R
import mx.itesm.bamx.RegisterAssociateActivity
import mx.itesm.bamx.SearchCenterActivity


class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMarkerClickListener {

    private lateinit var map : GoogleMap
    private lateinit var collection : CollectionReference
    private lateinit var logOutButton : Button
    private lateinit var registerAssociateButton : Button
    private lateinit var infoButton: Button
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var centers : HashMap<String, QueryDocumentSnapshot>

    private lateinit var organizationName : TextView
    private lateinit var address : TextView
    private lateinit var email : TextView
    private lateinit var phoneNumber : TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        organizationName = view.findViewById(R.id.organizationName)
        address = view.findViewById(R.id.address)
        email = view.findViewById(R.id.email)
        phoneNumber = view.findViewById(R.id.phoneNumber)

        registerAssociateButton = view.findViewById(R.id.becomeAssociateButton)
        registerAssociateButton.setOnClickListener{becomeAssociate()}

        infoButton = view.findViewById(R.id.info)
        infoButton.setOnClickListener { showInfo() }

        logOutButton = view.findViewById(R.id.logout)
        logOutButton.setOnClickListener {
            firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signOut()
            startActivity(Intent(this.context,MainActivity::class.java))
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        createFragment()
    }

//region Map
    private fun createFragment(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapAddress) as SupportMapFragment
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
        val cameraPosition = LatLng(24.0220143,-101.3152273)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraPosition, 4f))
        map.setOnMarkerClickListener(this)
        fetchCenters()
    }

    fun fetchCenters(){
        collection = Firebase.firestore.collection("centers")
        val requestCenters = collection.get()
        centers = HashMap()
        requestCenters.addOnSuccessListener {
                result ->
            for(document in result){
                val lat = document.getDouble("lat")
                val lng = document.getDouble("lng")
                val approved = document.getBoolean("approved")
                val id = document.id
                val orgName = document.getString("organizationName")
                var coordinates : LatLng
                if(lat != null && lng != null && approved == true){
                    coordinates = LatLng(lat, lng)
                    val marker = map.addMarker(MarkerOptions().position(coordinates).title(orgName))
                    if (marker != null) {
                        marker.tag = id
                    }
                    centers[id] = document
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

    override fun onMarkerClick(p0: Marker): Boolean{
        val doc = centers[p0.tag]
        if (doc != null) {
            organizationName.text = doc.getString("organizationName")
            address.text = doc.getString("address")
            email.text = doc.getString("email")
            phoneNumber.text = doc.getString("phoneNumber")
        }
        map.animateCamera(CameraUpdateFactory.zoomIn())
        return false
    }
//endregion

    private fun becomeAssociate(){
        val intent = Intent(requireActivity(), RegisterAssociateActivity::class.java)
        startActivity(intent)
    }

    private fun showInfo(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Conoce acerca de nuestro programa de asociados")
        builder.setMessage("Nuestros asociados trabajan con nosotros para apoyarnos a captar alimentos para nuestros centros, asi como para colaborar en la planeacion de eventos")
        builder.setPositiveButton("OK"){
                dialog, which -> dialog.cancel()
        }
        builder.show()
    }
}