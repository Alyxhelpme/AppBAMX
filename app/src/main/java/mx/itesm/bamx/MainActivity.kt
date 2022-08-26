package mx.itesm.bamx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /*
    fun Maps(view: View?){
        val intent = Intent(this, MapsActivity::class.java)

        startActivity(intent)
    }*/

    fun goToMap(view: View?){
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
    }
}