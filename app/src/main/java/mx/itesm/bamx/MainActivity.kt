package mx.itesm.bamx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import mx.itesm.bamx.R
import mx.itesm.bamx.RootTabActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun getIn(view: View?){
        val intent = Intent(this, RootTabActivity::class.java)
        startActivity(intent)
    }
}