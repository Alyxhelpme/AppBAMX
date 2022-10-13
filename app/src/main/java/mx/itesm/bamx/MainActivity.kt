package mx.itesm.bamx

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mx.itesm.bamx.RootTabActivity
import android.provider.DocumentsContract
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.lang.Exception

var carrito = 0
var carritoQuantities = ArrayList<Int>()
var carritoItems = ArrayList<String>()
var carritoPrices = ArrayList<Int>()

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var gsc : GoogleSignInClient
    private lateinit var gso : GoogleSignInOptions
    lateinit var googleButton : Button
    lateinit var emailButton : Button

    private companion object{
        private const val RC_SIGN_IN = 100
        private const val TAG = "GOOGLE_SIGN_IN_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        googleButton = findViewById(R.id.googleLoginButton)
        emailButton = findViewById(R.id.mailLoginButton)

        //Initialize Firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        emailButton.setOnClickListener{
            //Something
            startActivity(Intent(this, EmailLogin::class.java))
        }

        googleButton.setOnClickListener{
            //Configure google signIn
            gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            gsc = GoogleSignIn.getClient(this, gso)

            val intent = gsc.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN){
            Log.d(TAG,"onActivityResult: GoogleSignIn intent result")
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount(account)
            } catch (e: Exception){
                Log.d(TAG, "onActivityResult: ${e.message}")
            }
        }
    }

    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?) {
        Log.d(TAG, "firebaseAuthWithGoogleAccount : begin firebase auth with google account")
        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                Log.d(TAG, "firebaseAuthWithGoogleAccount : logged in")

                val firebaseUser = firebaseAuth.currentUser
                val uid = firebaseUser!!.uid
                val email = firebaseUser.email
                startActivity(Intent(this, RootTabActivity::class.java))
            }
    }


    fun goToMap(view: View?){
        val intent = Intent(this, RootTabActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null){
            startActivity(Intent(this, RootTabActivity::class.java))
        }
    }
}