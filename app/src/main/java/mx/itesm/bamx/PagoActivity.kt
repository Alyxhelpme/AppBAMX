package mx.itesm.bamx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView

class PagoActivity : AppCompatActivity() {

    private lateinit var textoEditable : EditText

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pago)

        val textito = findViewById<TextView>(R.id.ejemploTV)
        textito.text = "Hola"
        textito.text = carrito.toString()
    }
}