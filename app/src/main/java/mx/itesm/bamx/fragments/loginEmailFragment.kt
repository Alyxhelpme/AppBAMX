package mx.itesm.bamx.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import mx.itesm.bamx.R
import mx.itesm.bamx.RootTabActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [loginEmailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class loginEmailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var loginButton : Button
    private lateinit var emailLogin : EditText
    private lateinit var passwordLogin : EditText
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var noAccount : TextView
    private lateinit var registerFragment : registerEmailFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_login_email, container, false)

        val activity = this.activity

        emailLogin = view.findViewById(R.id.emailLoginInput)
        passwordLogin = view.findViewById(R.id.passwordLoginInput)
        loginButton = view.findViewById(R.id.loginButton)
        noAccount = view.findViewById(R.id.noAccount)
        registerFragment = registerEmailFragment()
        firebaseAuth = FirebaseAuth.getInstance()

        noAccount.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.loginFragmentView, registerFragment, TAG_FRAGMENTO)
                commit()
            }
        }

        loginButton.setOnClickListener {

            if (emailLogin.text.toString() != "" && passwordLogin.text.toString() != "") {

                var authTask = Firebase.auth.signInWithEmailAndPassword(
                    emailLogin.text.toString(),
                    passwordLogin.text.toString()
                )
                authTask.addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        Toast.makeText(this.context, "Iniciando sesion", Toast.LENGTH_SHORT).show()
                        Log.d("FIREBASE", "DEBUG: ${result.exception?.message}")
                        startActivity(Intent(this.context, RootTabActivity::class.java))

                    } else {
                        Log.wtf("FIREBASE", "ERROR: ${result.exception?.message}")
                        if (result.exception?.message.toString() == "There is no user record corresponding to this identifier. The user may have been deleted.") {
                            Toast.makeText(
                                this.context,
                                "El correo electronico proporcionado no esta registrado",
                                Toast.LENGTH_LONG
                            ).show()
                            passwordLogin.text = null
                        } else if (result.exception?.message.toString() == "The password is invalid or the user does not have a password.") {
                            Toast.makeText(this.context, "Contraseña incorrecta", Toast.LENGTH_LONG)
                                .show()
                            passwordLogin.text = null
                        } else if (result.exception?.message.toString() == "The email address is badly formatted."){
                            Toast.makeText(this.context, "Introduce un correo electronico valido", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } else {
                Toast.makeText(
                    this.context,
                    "Faltan datos por llenar para el inicio de sesion",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        return view
    }

    fun login(string1 : String, string2 : String) : Boolean{
        if (string1 != "" && string2 != ""){
            var authTask = Firebase.auth.signInWithEmailAndPassword(string1, string2)
        }

        return !(string1.isEmpty() || string2.isBlank())

    }

    companion object {
        private const val TAG_FRAGMENTO = "fragmentito"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment loginEmailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            loginEmailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}