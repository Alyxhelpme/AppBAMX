package mx.itesm.bamx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.io.File
import java.util.jar.Manifest
import javax.xml.parsers.DocumentBuilderFactory

class RegisterAssociateActivity : AppCompatActivity() , OnMapReadyCallback {

    private lateinit var organizationName : String
    private lateinit var name : String
    private lateinit var emailAddress : String
    private lateinit var phoneNumber : String

    private lateinit var organizationNameField : EditText
    private lateinit var nameField : EditText
    private lateinit var emailField : EditText
    private lateinit var phoneNumberField : EditText


    private val geocodingAPI = "https://maps.googleapis.com/maps/api/geocode/json?key=${getString(R.string.google_maps_api_key)}&address="
    private lateinit var map : GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_associate)
        initiatePlaces()


    }

//region Map Methods
    private fun initiatePlaces(){
        if(!Places.isInitialized()){
            val key = getString(R.string.google_maps_api_key)
            Places.initialize(applicationContext, key)
        }
        val placesClient = Places.createClient(this)
        val autoCompleteFragment = supportFragmentManager.findFragmentById(R.id.autocompleteFragment) as AutocompleteSupportFragment
        autoCompleteFragment.setPlaceFields(listOf(Place.Field.ADDRESS, Place.Field.NAME))
        autoCompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Log.i("PLACES", "Place: ${place.name}, ${place.address}")
                val formattedAddress = place.address.replace(" ", "+")
            }
            override fun onError(status: Status) {
                Log.i("PLACES", "An error occurred: $status")
            }
        })

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapAddress) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
        val cameraPosition = LatLng(24.0220143,-101.3152273)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraPosition, 4f))
    }
//endregion


}