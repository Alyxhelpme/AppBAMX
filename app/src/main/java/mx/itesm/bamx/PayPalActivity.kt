package mx.itesm.bamx

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.get
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.config.SettingsConfig
import com.paypal.checkout.createorder.CreateOrder
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.OrderIntent
import com.paypal.checkout.createorder.UserAction
import com.paypal.checkout.order.Amount
import com.paypal.checkout.order.AppContext
import com.paypal.checkout.order.Order
import com.paypal.checkout.order.PurchaseUnit
import com.paypal.checkout.paymentbutton.PaymentButtonContainer

class PayPalActivity : AppCompatActivity() {
    lateinit var paymentButtn: PaymentButtonContainer
    lateinit var webview: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pago)
        webview = findViewById(R.id.webView)
        webview.settings.allowFileAccess = true
        webview.settings.allowContentAccess = true
        webview.settings.domStorageEnabled = true
        webview.settings.javaScriptEnabled = true

        webview.loadUrl("file:///android_asset/index.html")

        webview.webViewClient = object : WebViewClient(){
            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                webview.evaluateJavascript("replace('modifiable value','${carrito.toString()}')")    {}
            }
        }
        //webview.loadUrl("javascript:replace('modifiable value','${carrito.toString()}');")



//        val config = CheckoutConfig(
//            application = application,
//            clientId = "ATCOmJj1A9ymZZ3AkHL2yYjVv2HK7WlK122Eew4mpMF-9zR5JhiArL83HuciJn2byEiUtlSQoq3mFB0v",
//            environment = Environment.SANDBOX,
//            returnUrl = "mx.itesm.bamx://paypalpay",
//            currencyCode = CurrencyCode.MXN,
//            userAction = UserAction.PAY_NOW,
//            settingsConfig = SettingsConfig(
//                loggingEnabled = true
//            )
//        )
//        PayPalCheckout.setConfig(config)
//        paymentButtn = findViewById(R.id.paymentButtn)
//        paymentButtn.setup(
//            createOrder =
//            CreateOrder { createOrderActions ->
//                val order =
//                    Order(
//                        intent = OrderIntent.CAPTURE,
//                        appContext = AppContext(userAction = UserAction.PAY_NOW),
//                        purchaseUnitList =
//                        listOf(
//                            PurchaseUnit(
//                                amount =
//                                Amount(currencyCode = CurrencyCode.USD, value = "10.00")
//                            )
//                        )
//                    )
//                createOrderActions.create(order)
//            },
//            onApprove =
//            OnApprove { approval ->
//                approval.orderActions.capture { captureOrderResult ->
//                    Log.i("CaptureOrder", "CaptureOrderResult: $captureOrderResult")
//                }
//            }
//        )
    }
}