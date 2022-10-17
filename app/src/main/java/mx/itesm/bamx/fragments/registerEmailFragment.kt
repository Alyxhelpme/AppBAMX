package mx.itesm.bamx.fragments

import android.os.Bundle
import android.os.TestLooperManager
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
import mx.itesm.bamx.EmailLogin
import mx.itesm.bamx.R
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [registerEmailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class registerEmailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var passwordConfirmation : EditText
    private lateinit var registerButton : Button
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var loginFragment : loginEmailFragment
    private lateinit var alreadyRegistered : TextView

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
        val view: View = inflater.inflate(R.layout.fragment_register_email, container, false)

        val activity = this.activity



        email = view.findViewById(R.id.emailTextInput)
        password = view.findViewById(R.id.passwordTextInput)
        passwordConfirmation = view.findViewById(R.id.passwordConfTextInput)
        registerButton = view.findViewById(R.id.registerButton)
        firebaseAuth = FirebaseAuth.getInstance()
        loginFragment = loginEmailFragment()
        alreadyRegistered = view.findViewById(R.id.alreadyRegistered)

        alreadyRegistered.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.loginFragmentView, loginFragment, TAG_FRAGMENTO)
                commit()
            }
        }

        registerButton.setOnClickListener{


            if (email.text.toString() != "" && password.text.toString() != "" && passwordConfirmation.text.toString() != "" &&
                password.text.toString() == passwordConfirmation.text.toString()){

                var authTask = Firebase.auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())

                authTask.addOnCompleteListener { result ->
                    if (result.isSuccessful){
                        Toast.makeText(this.context, "Cuenta creada", Toast.LENGTH_SHORT).show()
                        activity?.supportFragmentManager?.beginTransaction()?.apply {
                            replace(R.id.loginFragmentView, loginFragment, TAG_FRAGMENTO)
                            commit()
                        }
                        //DO SOMETHING
                    } else {
                        //DO SOMETHING ELSE
                        Log.wtf("FIREBASE", "ERROR: ${result.exception?.message}")
                        if (result.exception?.message.toString() == "The email address is badly formatted."){
                            Toast.makeText(this.context, "Introduce un correo electronico valido", Toast.LENGTH_LONG).show()
                        } else if (result.exception?.message.toString() == "The given password is invalid. [ Password should be at least 6 characters ]"){
                            Toast.makeText(this.context, "La contraseña es demasiado corta. Debe contener al menos 6 caracteres.", Toast.LENGTH_LONG).show()
                        } else if (result.exception?.message.toString() == "The email address is already in use by another account."){
                            Toast.makeText(this.context, "El correo electronico proporcionado ya esta registrado.", Toast.LENGTH_LONG).show()
                        }

                    }
                }
            } else {
                Toast.makeText(this.context, "Faltan datos por llenar para crear tu cuenta", Toast.LENGTH_LONG).show()
                email.text = null
                password.text = null
                passwordConfirmation.text = null

            }



        }



        return view
    }



    companion object {

        private const val TAG_FRAGMENTO = "fragmentito"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment registerEmailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            registerEmailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}