package mx.itesm.bamx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class CarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        println(nombresC)
        println("hola")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car)
    }
}