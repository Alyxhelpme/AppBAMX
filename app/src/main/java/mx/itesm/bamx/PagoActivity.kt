package mx.itesm.bamx

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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
            Toast.makeText(mContext,tempPayment.toString(),Toast.LENGTH_SHORT).show()
            saveDonation()
        }

        fun saveDonation(){
            var db = Firebase.firestore

            if (PagoActivity.paymentstatus)
                db.collection("Donations").document().set(donation)
                    .addOnSuccessListener{
                        Toast.makeText(mContext, "Added", Toast.LENGTH_SHORT).show()
                    }

        }
    }



}