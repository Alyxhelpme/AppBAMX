package mx.itesm.bamx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.TextView

class PagoActivity : AppCompatActivity() {

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

        webview.loadUrl("file:///android_asset/index.html")


        webview.webViewClient = object : WebViewClient(){
            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                webview.evaluateJavascript("replace('modifiable value','${carrito.toString()}')")    {}
            }
        }
        val textito = findViewById<TextView>(R.id.ejemploTV)
        textito.text = "Total a pagar: " + carrito.toString()
    }
}