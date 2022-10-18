package mx.itesm.bamx

import android.content.Context
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jakewharton.processphoenix.ProcessPhoenix;
import mx.itesm.bamx.fragments.HomeFragment
import java.util.*

class PagoActivity : AppCompatActivity() {
    companion object{
        public var paymentstatus: Boolean = false // ESTE ES LA VARIABLE DE CONFIRMACION DE PAGO
    }

    lateinit var mcontext: Context
    private lateinit var textoEditable : EditText
    lateinit var webview: WebView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pago)
        webview = findViewById(R.id.webView)
        webview.settings.allowFileAccess = true
        webview.settings.allowContentAccess = true
        webview.settings.domStorageEnabled = true
        webview.settings.javaScriptEnabled = true
        webview.addJavascriptInterface(WebAppInterface(this),"Android")
        webview.loadUrl("file:///android_asset/index.html")

        webview.webViewClient = object : WebViewClient(){
            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                webview.evaluateJavascript("replace('modifiable value','${carrito.toString()}')")    {}
            }
        }
        webview.loadUrl("javascript:Android.getIds()")
        val textito = findViewById<TextView>(R.id.ejemploTV)
        textito.text = "Total a pagar: $" + carrito.toString() + " MXN"

    }
    class WebAppInterface(private val mContext: Context){
        @JavascriptInterface
        fun getStatus(tempPayment: Boolean){ //Aqui esta la variable de pago
            PagoActivity.paymentstatus = tempPayment
//            Toast.makeText(mContext,tempPayment.toString(),Toast.LENGTH_SHORT).show()
            saveDonation()
        }

        fun saveDonation(){
            var db = Firebase.firestore
//            var arroz = 0
//            var aceite = 0
//            var rollos = 0

            if (PagoActivity.paymentstatus) {
                db.collection("Donaciones").document().set(donation)
                    .addOnSuccessListener {
                        Toast.makeText(mContext, "Donacion realizada", Toast.LENGTH_LONG).show()
                    }
                Firebase.firestore.collection("Metas").get()
                    .addOnSuccessListener { result->
                        for (documentoActual in result){
                            arroz = documentoActual.get("Arroz").toString().toInt()
                            aceite = documentoActual.get("Aceite").toString().toInt()
                            rollos = documentoActual.get("Rollos").toString().toInt()
                        }

                        var goalArroz = donation.getValue("Arroz").toString().toInt() + arroz
                        var goalAceite = donation.getValue("Aceite").toString().toInt() + aceite
                        var goalRollos = donation.getValue("Papel higiénico").toString().toInt() + rollos
                        db.collection("Metas").document("Total")
                            .update("Arroz", goalArroz,
                                "Aceite", goalAceite,
                                "Rollos", goalRollos)
                }
            }
            val coleccion = Firebase.firestore.collection("Metas")

            val queryTask = coleccion.get()
            queryTask.addOnSuccessListener { result ->
                // recorrer datos
                for (documentoActual in result) {
                    Log.d(
                        "FIRESTORE", "${documentoActual.id}"
                    )
                    arroz = documentoActual.get("Arroz").toString().toInt()
                    rollos = documentoActual.get("Rollos").toString().toInt()
                    aceite = documentoActual.get("Aceite").toString().toInt()
                }
            }.addOnFailureListener { error ->
                Log.e("FIRESTORE", "error in query: $error")
            }
            goHome()
        }

        fun goHome() {
            val intent = Intent(mContext, RootTabActivity::class.java)
            mContext.startActivity(intent)
        }

    }

}

