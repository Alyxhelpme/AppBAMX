package mx.itesm.bamx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.properties.Delegates


class RegisterAssociateActivity : AppCompatActivity() , OnMapReadyCallback {

    private lateinit var organizationName : String
    private lateinit var name : String
    private lateinit var emailAddress : String
    private lateinit var phoneNumber : String
    private lateinit var formattedAddress : String
    private var lat by Delegates.notNull<Double>()
    private var lng by Delegates.notNull<Double>()

    private lateinit var organizationNameField : EditText
    private lateinit var nameField : EditText
    private lateinit var emailField : EditText
    private lateinit var phoneNumberField : EditText

    private lateinit var db : FirebaseFirestore

    private lateinit var geocodingAPI : String
    private lateinit var map : GoogleMap
    private lateinit var geocodingQueue : RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_associate)
        findViewById<Button>(R.id.submitButton).setOnClickListener{submitApplication()}
        geocodingAPI = "https://maps.googleapis.com/maps/api/geocode/json?key=${getString(R.string.google_maps_api_key)}&address="
        geocodingQueue = Volley.newRequestQueue(this)
        initiatePlaces()
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapAddress) as SupportMapFragment
        mapFragment.getMapAsync(this)

        nameField = findViewById(R.id.nameField)
        organizationNameField = findViewById(R.id.organizationNameField)
        emailField = findViewById(R.id.emailField)
        phoneNumberField = findViewById(R.id.phoneNumberField)

        db = FirebaseFirestore.getInstance()
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
                val formattedAPIAddress = place.address.replace(" ", "+")
                val geocodingURL = geocodingAPI + formattedAPIAddress

                val request = JsonObjectRequest(geocodingURL,
                    { response ->
                        val results = response.getJSONArray("results")
                        val geo = results.getJSONObject(0).getJSONObject("geometry")
                        lat = geo.getJSONObject("location").getDouble("lat")
                        lng = geo.getJSONObject("location").getDouble("lng")
                        val coordinates = LatLng(lat, lng)
                        map.addMarker(MarkerOptions().position(coordinates))
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 14f))
                        formattedAddress = results.getJSONObject(0).getString("formatted_address")

                    },
                    {
                        Log.wtf("GEOCODING", it.toString())
                    }
                )
                geocodingQueue.add(request)
            }
            override fun onError(status: Status) {
                Log.i("PLACES", "An error occurred: $status")
            }
        })


    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
        val cameraPosition = LatLng(24.0220143,-101.3152273)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraPosition, 4f))
    }
//endregion


//region Submit input
    private fun submitApplication(){
        retrieveInputs()
        if(validateInputs()){
            uploadApplication()
        }
    }

    private fun validateInputs() : Boolean{
        val builder = AlertDialog.Builder(this)
        if(name.isEmpty() || organizationName.isEmpty() || emailAddress.isEmpty() || phoneNumber.isEmpty()){
            showAlert("Campos vacios", "Asegurese de haber llenado todos los campos")
            return false
        }
        if(formattedAddress.isEmpty() || lat.isNaN() || lng.isNaN()){
            showAlert("Ingrese su direccion de nuevo", "Su direccion no pudo ser procesada")
            return false
        }
        return true
    }

    private fun retrieveInputs(){
        name = nameField.text.toString()
        organizationName = organizationNameField.text.toString()
        phoneNumber = phoneNumberField.text.toString()
        emailAddress = emailField.text.toString()
    }

    private fun uploadApplication(){
        val data = hashMapOf(
            "lat" to lat,
            "lng" to lng,
            "name" to name,
            "organizationName" to organizationName,
            "address" to formattedAddress,
            "phoneNumber" to phoneNumber,
            "email" to emailAddress,
            "approved" to false
        )

        db.collection("centers").add(data).addOnSuccessListener { documentReference ->
            showAlertWithDismiss("Registro exitoso", "Pronto alguien de nuestra organizacion entrara en contacto con usted")
        }.addOnFailureListener {
            Log.wtf("ERROR", "NOT Uploaded")
        }
    }
//endregion


//region Alert
    fun showAlert(title : String, message : String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Te registraste con exito")
        builder.setMessage("Pronto alguien de la organizacion se comunicara contigo")
        builder.setPositiveButton("OK"){
                dialog, which -> dialog.cancel()
        }
        builder.show()
    }

    fun showAlertWithDismiss(title : String, message : String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Te registraste con exito")
        builder.setMessage("Pronto alguien de la organizacion se comunicara contigo")
        builder.setPositiveButton("OK"){ dialog, which ->
            dialog.cancel()
            finish()
        }
        builder.show()
    }
//endregion

}