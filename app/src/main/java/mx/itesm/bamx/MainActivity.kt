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
                    "<strong>T??rminos &amp; Condiciones</strong> <p>" +
                    "   Al descargar o usar la aplicaci??n, estos t??rminos se aplicar??n autom??ticamente a usted; \n" +
                    "   por lo tanto, debe asegurarse de leerlos detenidamente antes de usar la aplicaci??n. No \n" +
                    "   tiene permitido copiar ni modificar la aplicaci??n, ninguna parte de la aplicaci??n o nuestras " +
                    "   marcas comerciales de ninguna manera. No se le permite intentar extraer el c??digo fuente " +
                    "   de la aplicaci??n, y tampoco debe intentar traducir la aplicaci??n a otros idiomas o hacer " +
                    "   versiones derivadas. La aplicaci??n en s??, y todas las marcas comerciales, derechos de " +
                    "   autor, derechos de bases de datos y otros derechos de propiedad intelectual relacionados " +
                    "   con ella, siguen perteneciendo a AppBAMX. </p>"+

                    "   <p> AppBAMX se compromete a garantizar que la aplicaci??n sea lo m??s ??til y eficiente posible. \n" +
                    "    Por ese motivo, nos reservamos el derecho de realizar cambios en la aplicaci??n o cobrar \n" +
                    "    por sus servicios, en cualquier momento y por cualquier motivo. Nunca le cobraremos por \n" +
                    "    la aplicaci??n o sus servicios sin dejarle muy claro exactamente lo que est?? pagando.</p>\n" +


                    "   <p>La aplicaci??n AppBAMX almacena y procesa los datos personales que nos ha \n" +
                    "   proporcionado para ofrecer nuestro servicio. Es su responsabilidad mantener su tel??fono \n"+
                    "   y el acceso a la aplicaci??n seguros. Por lo tanto, le recomendamos que no haga jailbreak \n"+
                    "   ni rootee su tel??fono, que es el proceso de eliminar las restricciones y limitaciones de \n"+
                    "   software impuestas por el sistema operativo oficial de su dispositivo. \n"+
                    "   Podr??a hacer que su tel??fono sea vulnerable a malware/virus/programas maliciosos, comprometer \n" +
                    "   las funciones de seguridad de su tel??fono y llevar??a a que la aplicaci??n \n" +
                    "   AppBAMX no funcionar?? correctamente o no funcionar?? en absoluto. La aplicaci??n AppBAMX \n" +
                    "   almacena y procesa los datos personales que nos ha proporcionado para ofrecer nuestro servicio. \n" +
                    "   Es su responsabilidad mantener su tel??fono y el acceso a la aplicaci??n seguros. Por lo \n" +
                    "   tanto, le recomendamos que no haga jailbreak ni rootee su tel??fono, que es el proceso de eliminar \n " +
                    "   las restricciones y limitaciones de software impuestas por el sistema operativo oficial de su dispositivo. \n"+
                    "   Podr??a hacer que su tel??fono sea vulnerable a malware/virus/programas maliciosos, comprometer \n" +
                    "   las funciones de seguridad de su tel??fono y llevar??a a que la aplicaci??n AppBAMX no funcionar?? \n" +
                    "   correctamente o no funcionar?? en absoluto. </p> \n" +

                    "</p> <div><p>\n" +
                    "    La aplicaci??n utiliza servicios de terceros que declaran sus T??rminos y Condiciones en su sitio oficial." +
                    "</p> <p>\n" +
                    "   Proveedores de servicios de terceros utilizados por la aplicaci??n \n" +
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
                    "AppBAMX no se har?? responsable de. Ciertas funciones de la aplicaci??n "+
                    "requerir??n que la aplicaci??n tenga conexi??n a Internet activa. La "+
                    "conexi??n puede ser Wi-Fi o proporcionada por su proveedor de red m??vil, "+
                    "pero AppBAMX no asume la responsabilidad de que la aplicaci??n no "+
                    "ofrezca todas las funcionalidades si no tiene acceso a Wi-Fi." +

                    "</p> <p></p> <p>\n" +
                    "    Si est?? utilizando la aplicaci??n fuera de un ??rea con Wi-Fi, debe " +
                    "recordar que se seguir??n aplicando los t??rminos del acuerdo con su proveedor " +
                    "de red m??vil. Es posible que su proveedor de telefon??a m??vil " +
                    "le cobre el costo de los datos durante la duraci??n de la conexi??n " +
                    "mientras accede a la aplicaci??n u otros cargos de terceros. Al usar la " +
                    "aplicaci??n, usted acepta la responsabilidad de dichos cargos, incluidos los " +
                    "cargos de roaming de datos si usa la aplicaci??n fuera de su territorio de " +
                    "origen (es decir, regi??n o pa??s) sin desactivar el roaming de datos. Si " +
                    "usted no es el responsable de pago del dispositivo en el que est?? usando, " +
                    "asumimos que ha recibido permiso del " +
                    "responsable de la cuenta de pago para usar la aplicaci??n." +
                    "</p> <p>\n" +
                    " De la misma manera, AppBAMX no siempre puede asumir la responsabilidad por " +
                    "la forma en que usa la aplicaci??n, es decir, debe asegurarse de que su " +
                    "dispositivo permanezca cargado; si se queda sin bater??a y no puede encenderlo " +
                    "para aprovechar el servicio, AppBAMX no puede aceptar la responsabilidad." +
                    "</p> <p>\n" +
                    "Con respecto a la responsabilidad de AppBAMX por su uso de la aplicaci??n, " +
                    "cuando est?? usando la aplicaci??n, es importante tener en cuenta que, aunque " +
                    "nos esforzamos por garantizar que est?? actualizada y sea correcta en todo " +
                    "momento, confiamos en terceros para proporcionar informaci??n para que podamos " +
                    "ponerla a su disposici??n. AppBAMX no acepta ninguna responsabilidad por " +
                    "cualquier p??rdida, directa o indirecta, que experimente como resultado de "+
                    "confiar completamente en esta funcionalidad de la aplicaci??n." +
                    "</p> <p>\n" +
                    "En alg??n momento, es posible que deseemos actualizar la aplicaci??n. La " +
                    "aplicaci??n todav??a no est?? actualmente disponible en Android: los requisitos para el " +
                    "sistema (y para cualquier sistema adicional al que decidamos extender la " +
                    "disponibilidad de la aplicaci??n) pueden cambiar, y deber?? descargar las " +
                    "actualizaciones si desea usar la aplicaci??n. AppBAMX no promete " +
                    "que siempre actualizar?? la aplicaci??n para que sea relevante para usted y/o " +
                    "funcione con la versi??n de Android que tiene instalada en su dispositivo. " +
                    "Sin embargo, usted se compromete a aceptar siempre las actualizaciones de " +
                    "la aplicaci??n cuando se le ofrezcan. Tambi??n es posible que deseemos dejar " +
                    "de desarrollar la aplicaci??n y podemos dejar de usarla en cualquier momento " +
                    "sin notificarle. \n" +
                    " A menos que le indiquemos lo contrario, ante cualquier terminaci??n, \n (a) " +
                    "los derechos y licencias que se le otorgaron en estos t??rminos terminar??n; " +
                    "(b) debe dejar de usar la aplicaci??n y (si es necesario) eliminarla de su " +
                    " dispositivo. \n" +
                    "</p> <p><strong>Cambios a estos T??rminos y Condiciones</strong></p> <p>\n" +
                    "    Es posible que actualicemos nuestros T??rminos y condiciones de vez en " +
                    "cuando. Por lo tanto, se le recomienda revisar peri??dicamente " +
                    "para ver si hay cambios. \n" +
                    "</p> <p>\n" +
                    "    Estos t??rminos y condiciones son efectivos a partir del 2022-10-19\n" +
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
                setMessage("Por favor, acepta los t??rminos y condiciones antes de iniciar")
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
                setMessage("Por favor, acepta los t??rminos y condiciones antes de iniciar")
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