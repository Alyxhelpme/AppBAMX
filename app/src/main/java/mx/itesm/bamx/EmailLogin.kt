package mx.itesm.bamx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import mx.itesm.bamx.fragments.loginEmailFragment
import mx.itesm.bamx.fragments.registerEmailFragment

class EmailLogin : AppCompatActivity() {

    lateinit var fragmentoRegistro : registerEmailFragment
    lateinit var fragmentoLogin : loginEmailFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_login)

        fragmentoRegistro = registerEmailFragment()
        fragmentoLogin = loginEmailFragment.newInstance("","")

        /*val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.loginFragmentView, fragmentoLogin, TAG_FRAGMENTO)
        transaction.commit()*/


    }

    companion object {

        private const val TAG_FRAGMENTO = "fragmentito"
    }
}