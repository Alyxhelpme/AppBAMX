package mx.itesm.bamx

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import supportClasses.CarAdapter
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CarActivity : AppCompatActivity(), CarAdapter.CarListener, View.OnClickListener {

    lateinit var RecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        println(nombresC)
        println(cantidadC)
        println(preciosC)
        println("hola")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car)

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
        TODO("Not yet implemented")
    }
}