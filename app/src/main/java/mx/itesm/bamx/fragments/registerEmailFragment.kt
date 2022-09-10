package mx.itesm.bamx.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import mx.itesm.bamx.EmailLogin
import mx.itesm.bamx.R

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
    private lateinit var firebaseAuth: FirebaseAuth

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

        email = view.findViewById(R.id.emailTextInput)
        password = view.findViewById(R.id.passwordTextInput)
        passwordConfirmation = view.findViewById(R.id.passwordConfTextInput)
        registerButton = view.findViewById(R.id.registerButton)
        firebaseAuth = FirebaseAuth.getInstance()

        registerButton.setOnClickListener{
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches() && email.text.toString() != null && password.text.toString() == passwordConfirmation.text.toString()){
                var authTask = Firebase.auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())

                authTask.addOnCompleteListener { result ->
                    if (result.isSuccessful){
                        //DO SOMETHING
                    } else {
                        //DO SOMETHING ELSE
                    }
                }
            } else {
                Toast.makeText(this.context, "Intenta de nuevo con datos validos", Toast.LENGTH_LONG).show()
                email.text = null
                password.text = null
                passwordConfirmation.text = null

            }

        }



        return view
    }

    companion object {
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