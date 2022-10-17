package mx.itesm.bamx

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import supportClasses.CarAdapter
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CarActivity : AppCompatActivity(), CarAdapter.CarListener, View.OnClickListener {

    lateinit var RecyclerView : RecyclerView
    lateinit var payButton: Button
    lateinit var backButton: Button
    lateinit var totalTV : TextView
    lateinit var emptyCarTV : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        println(nombresC)
        println(cantidadC)
        println(preciosC)
        println("hola")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car)

        emptyCarTV = findViewById(R.id.emptyCar)
        totalTV = findViewById(R.id.totalTVCar)
        payButton = findViewById(R.id.payButton)
        backButton = findViewById(R.id.backButton)

        if (carrito == 0) {
            emptyCarTV.text = "Carrito vacio. Agrega un elemento."
            payButton.visibility = View.GONE
            totalTV.text = ""
        } else {
            emptyCarTV.text = ""
            totalTV.text = "Total: $" + carrito + " MXN"
        }


        backButton.setOnClickListener { onBackPressed() }
        payButton.setOnClickListener { startActivity(Intent(this, PagoActivity::class.java)) }

        RecyclerView = findViewById(R.id.RV_ShoppingCar)

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL

        RecyclerView.layoutManager = llm

        val adapter = CarAdapter(nombresC, preciosC, cantidadC, this, this)
        RecyclerView.adapter = adapter

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {

        return super.onCreateView(name, context, attrs)
    }

    // this may break all
    override fun updateCount() {
        TODO("Not yet implemented")
    }

    override fun onClick(p0: View?) {
        return
    }

    private fun goPay() {
        val intent = Intent(this, PaypalActivity::class.java)
        startActivity(intent)
    }

}