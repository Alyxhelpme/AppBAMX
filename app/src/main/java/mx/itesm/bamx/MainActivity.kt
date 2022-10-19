package mx.itesm.bamx

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

var carrito = 0
lateinit var test : String
lateinit var user : String
lateinit var nombresC : ArrayList<String>
lateinit var preciosC : ArrayList<String>
lateinit var cantidadC : ArrayList<Int>
lateinit var donation : HashMap<String, Any>
lateinit var goals : HashMap<String, Any>

var arroz = 0
var rollos = 0
var aceite = 0


class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var gsc : GoogleSignInClient
    private lateinit var gso : GoogleSignInOptions
    lateinit var googleButton : Button
    lateinit var emailButton : Button
    lateinit var termsButton : CheckBox

    private companion object{
        private const val RC_SIGN_IN = 100
        private const val TAG = "GOOGLE_SIGN_IN_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        termsButton = findViewById(R.id.checkBox)
        termsButton.setOnClickListener {

            val builder = AlertDialog.Builder(this@MainActivity).
            setMessage(Html.fromHtml("<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <meta charset='utf-8'>\n" +
                    "    <meta name='viewport' content='width=device-width'>\n" +

                    "</head>\n" +
                    "<body>\n" +
                    "<strong>Términos &amp; Condiciones</strong> <p>" +
                    "   Al descargar o usar la aplicación, estos términos se aplicarán automáticamente a usted; \n" +
                    "   por lo tanto, debe asegurarse de leerlos detenidamente antes de usar la aplicación. No \n" +
                    "   tiene permitido copiar ni modificar la aplicación, ninguna parte de la aplicación o nuestras " +
                    "   marcas comerciales de ninguna manera. No se le permite intentar extraer el código fuente " +
                    "   de la aplicación, y tampoco debe intentar traducir la aplicación a otros idiomas o hacer " +
                    "   versiones derivadas. La aplicación en sí, y todas las marcas comerciales, derechos de " +
                    "   autor, derechos de bases de datos y otros derechos de propiedad intelectual relacionados " +
                    "   con ella, siguen perteneciendo a AppBAMX. </p>"+

                    "   <p> AppBAMX se compromete a garantizar que la aplicación sea lo más útil y eficiente posible. \n" +
                    "    Por ese motivo, nos reservamos el derecho de realizar cambios en la aplicación o cobrar \n" +
                    "    por sus servicios, en cualquier momento y por cualquier motivo. Nunca le cobraremos por \n" +
                    "    la aplicación o sus servicios sin dejarle muy claro exactamente lo que está pagando.</p>\n" +


                    "   <p>La aplicación AppBAMX almacena y procesa los datos personales que nos ha \n" +
                    "   proporcionado para ofrecer nuestro servicio. Es su responsabilidad mantener su teléfono \n"+
                    "   y el acceso a la aplicación seguros. Por lo tanto, le recomendamos que no haga jailbreak \n"+
                    "   ni rootee su teléfono, que es el proceso de eliminar las restricciones y limitaciones de \n"+
                    "   software impuestas por el sistema operativo oficial de su dispositivo. \n"+
                    "   Podría hacer que su teléfono sea vulnerable a malware/virus/programas maliciosos, comprometer \n" +
                    "   las funciones de seguridad de su teléfono y llevaría a que la aplicación \n" +
                    "   AppBAMX no funcionará correctamente o no funcionará en absoluto. La aplicación AppBAMX \n" +
                    "   almacena y procesa los datos personales que nos ha proporcionado para ofrecer nuestro servicio. \n" +
                    "   Es su responsabilidad mantener su teléfono y el acceso a la aplicación seguros. Por lo \n" +
                    "   tanto, le recomendamos que no haga jailbreak ni rootee su teléfono, que es el proceso de eliminar \n " +
                    "   las restricciones y limitaciones de software impuestas por el sistema operativo oficial de su dispositivo. \n"+
                    "   Podría hacer que su teléfono sea vulnerable a malware/virus/programas maliciosos, comprometer \n" +
                    "   las funciones de seguridad de su teléfono y llevaría a que la aplicación AppBAMX no funcionará \n" +
                    "   correctamente o no funcionará en absoluto. </p> \n" +

                    "</p> <div><p>\n" +
                    "    La aplicación utiliza servicios de terceros que declaran sus Términos y Condiciones en su sitio oficial." +
                    "</p> <p>\n" +
                    "   Proveedores de servicios de terceros utilizados por la aplicación \n" +
                    "</p> <ul><li>" + "<a href=\"https://cloud.google.com/maps-platform/terms\">" +
                    "Google Maps Platform</a> "+"</li><!----><li>" +
                    "<a href=\"https://firebase.google.com/terms/analytics\" target=\"_blank\" rel=\"noopener noreferrer\">" +
                    "Google Analytics for Firebase</a></li><!----><!----><!----><!----><!----><!----><!----><!----><!----><li>" +
                    "<li><a href=\"https://firebase.google.com/terms/performance\" target=\"_blank\" rel=\"noopener noreferrer\">" +
                    "Google Performance for Firebase</a></li>" +
                    "<a href=\"https://developer.twitter.com/en/docs/twitter-api\" target=\"_blank\" rel=\"noopener noreferrer\">Twitter API</a>" +
                    "<li><a href=\"https://www.sdkbox.com/privacy\" target=\"_blank\" rel=\"noopener noreferrer\">" +
                    "SDKBOX</a></li> <!----><!----><!----><!----><!----><!----><!----><!----><!---->" +
                    "</li><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!----><!---->" +
                    "</ul></div> <p>\n" +
                    "    Debe ser consciente de que hay ciertas cosas que\n" +
                    "AppBAMX no se hará responsable de. Ciertas funciones de la aplicación "+
                    "requerirán que la aplicación tenga conexión a Internet activa. La "+
                    "conexión puede ser Wi-Fi o proporcionada por su proveedor de red móvil, "+
                    "pero AppBAMX no asume la responsabilidad de que la aplicación no "+
                    "ofrezca todas las funcionalidades si no tiene acceso a Wi-Fi." +

                    "</p> <p></p> <p>\n" +
                    "    Si está utilizando la aplicación fuera de un área con Wi-Fi, debe " +
                    "recordar que se seguirán aplicando los términos del acuerdo con su proveedor " +
                    "de red móvil. Es posible que su proveedor de telefonía móvil " +
                    "le cobre el costo de los datos durante la duración de la conexión " +
                    "mientras accede a la aplicación u otros cargos de terceros. Al usar la " +
                    "aplicación, usted acepta la responsabilidad de dichos cargos, incluidos los " +
                    "cargos de roaming de datos si usa la aplicación fuera de su territorio de " +
                    "origen (es decir, región o país) sin desactivar el roaming de datos. Si " +
                    "usted no es el responsable de pago del dispositivo en el que está usando, " +
                    "asumimos que ha recibido permiso del " +
                    "responsable de la cuenta de pago para usar la aplicación." +
                    "</p> <p>\n" +
                    " De la misma manera, AppBAMX no siempre puede asumir la responsabilidad por " +
                    "la forma en que usa la aplicación, es decir, debe asegurarse de que su " +
                    "dispositivo permanezca cargado; si se queda sin batería y no puede encenderlo " +
                    "para aprovechar el servicio, AppBAMX no puede aceptar la responsabilidad." +
                    "</p> <p>\n" +
                    "Con respecto a la responsabilidad de AppBAMX por su uso de la aplicación, " +
                    "cuando esté usando la aplicación, es importante tener en cuenta que, aunque " +
                    "nos esforzamos por garantizar que esté actualizada y sea correcta en todo " +
                    "momento, confiamos en terceros para proporcionar información para que podamos " +
                    "ponerla a su disposición. AppBAMX no acepta ninguna responsabilidad por " +
                    "cualquier pérdida, directa o indirecta, que experimente como resultado de "+
                    "confiar completamente en esta funcionalidad de la aplicación." +
                    "</p> <p>\n" +
                    "En algún momento, es posible que deseemos actualizar la aplicación. La " +
                    "aplicación todavía no está actualmente disponible en Android: los requisitos para el " +
                    "sistema (y para cualquier sistema adicional al que decidamos extender la " +
                    "disponibilidad de la aplicación) pueden cambiar, y deberá descargar las " +
                    "actualizaciones si desea usar la aplicación. AppBAMX no promete " +
                    "que siempre actualizará la aplicación para que sea relevante para usted y/o " +
                    "funcione con la versión de Android que tiene instalada en su dispositivo. " +
                    "Sin embargo, usted se compromete a aceptar siempre las actualizaciones de " +
                    "la aplicación cuando se le ofrezcan. También es posible que deseemos dejar " +
                    "de desarrollar la aplicación y podemos dejar de usarla en cualquier momento " +
                    "sin notificarle. \n" +
                    " A menos que le indiquemos lo contrario, ante cualquier terminación, \n (a) " +
                    "los derechos y licencias que se le otorgaron en estos términos terminarán; " +
                    "(b) debe dejar de usar la aplicación y (si es necesario) eliminarla de su " +
                    " dispositivo. \n" +
                    "</p> <p><strong>Cambios a estos Términos y Condiciones</strong></p> <p>\n" +
                    "    Es posible que actualicemos nuestros Términos y condiciones de vez en " +
                    "cuando. Por lo tanto, se le recomienda revisar periódicamente " +
                    "para ver si hay cambios. \n" +
                    "</p> <p>\n" +
                    "    Estos términos y condiciones son efectivos a partir del 2022-10-19\n" +
                    "</body>\n" +
                    "</html>\n "))

            val dialog = builder.create()
            dialog.show()
            dialog.window?.setLayout(1000, 1400)
        }

        googleButton = findViewById(R.id.googleLoginButton)
        emailButton = findViewById(R.id.mailLoginButton)

        //Initialize Firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        emailButton.setOnClickListener{
            if (termsButton.isChecked) {
                //Something
                startActivity(Intent(this, EmailLogin::class.java))
            }
            else {
                val builder = AlertDialog.Builder(this@MainActivity).
                setMessage("Acepta términos y condiciones")
                val dialog = builder.create()
                dialog.show()
            }
        }

        googleButton.setOnClickListener{
            if (termsButton.isChecked) {
                //Configure google signIn
                gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()

                gsc = GoogleSignIn.getClient(this, gso)
                val intent = gsc.signInIntent
                startActivityForResult(intent, RC_SIGN_IN)
            }
            else {
                val builder = AlertDialog.Builder(this@MainActivity).
                setMessage("Acepta términos y condiciones")
                val dialog = builder.create()
                dialog.show()
            }
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN){
            Log.d(TAG,"onActivityResult: GoogleSignIn intent result")
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount(account)
            } catch (e: Exception){
                Log.d(TAG, "onActivityResult: ${e.message}")
            }
        }
    }

    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?) {
        Log.d(TAG, "firebaseAuthWithGoogleAccount : begin firebase auth with google account")
        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                Log.d(TAG, "firebaseAuthWithGoogleAccount : logged in")

                val firebaseUser = firebaseAuth.currentUser
                val uid = firebaseUser!!.uid
                val email = firebaseUser.email
                user = firebaseAuth.currentUser!!.email.toString()

                if (termsButton.isChecked){
                    startActivity(Intent(this, RootTabActivity::class.java))
                }
            }
    }


    fun goToMap(view: View?){
        val intent = Intent(this, RootTabActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null && termsButton.isChecked){
            user = firebaseAuth.currentUser!!.email.toString()
            startActivity(Intent(this, RootTabActivity::class.java))
        }
    }
}