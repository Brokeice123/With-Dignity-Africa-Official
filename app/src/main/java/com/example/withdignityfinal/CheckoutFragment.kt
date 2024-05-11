package com.example.withdignityfinal

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.withdignityfinal.adapter.CartAdapter
import com.example.withdignityfinal.data.CartProduct
import com.example.withdignityfinal.data.PackageItem
import com.flutterwave.raveandroid.RavePayActivity
import com.flutterwave.raveandroid.RaveUiManager
import com.flutterwave.raveandroid.rave_java_commons.RaveConstants
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.functions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CheckoutFragment : Fragment(), CartAdapter.OnTotalPriceUpdatedListener {
    private lateinit var db: FirebaseFirestore
    private lateinit var userId: String
    private val cartItems = mutableListOf<CartProduct>()
    private lateinit var adapter: CartAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_checkout, container, false)
        db = FirebaseFirestore.getInstance()
        userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        val recyclerView: RecyclerView = view.findViewById(R.id.checkoutrecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = CartAdapter(cartItems).apply {
            totalPriceListener = this@CheckoutFragment
        }
        recyclerView.adapter = adapter

        retrieveCartItems()

        val confirmCheckoutButton: Button = view.findViewById(R.id.ButtonConfirmCheckout)
        confirmCheckoutButton.setOnClickListener {
            initiateFlutterwavePayment()
        }

        return view
    }

    private fun retrieveCartItems() {
        db.collection("users").document(userId).collection("cart")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    cartItems.clear()
                    for (document in task.result) {
                        val packageItem = PackageItem(
                            id = document.getString("product.id") ?: "",
                            name = document.getString("product.name") ?: "",
                            image = document.getString("image") ?: "",
                            price = document.getDouble("product.price") ?: 0.0,
                            imageType = document.getString("imageType") ?: ""
                        )
                        val quantity = document.getLong("quantity")?.toInt() ?: 0
                        val id = document.id
                        cartItems.add(CartProduct(packageItem, image = packageItem.image, quantity = quantity, id = id))
                    }
                    adapter.notifyDataSetChanged()
                    displayTotalPrice()
                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            }
    }


    @SuppressLint("StringFormatMatches")
    private fun displayTotalPrice() {
        val totalPrice = adapter.calculateTotalPrice()
        val totalPriceTextView = view?.findViewById<TextView>(R.id.totalPriceTextView)
        if (totalPriceTextView != null) {
            totalPriceTextView.text = getString(R.string.total_price, totalPrice)
        }
    }

    override fun onTotalPriceUpdated(totalPrice: Double) {
        // Update your total price TextView here
        val totalPrice = adapter.calculateTotalPrice()
        val totalPriceTextView = view?.findViewById<TextView>(R.id.totalPriceTextView)
        if (totalPriceTextView != null) {
            totalPriceTextView.text = getString(R.string.total_price, totalPrice)
        }
    }

    private fun initiateFlutterwavePayment() {
        val firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser
        val name = user?.displayName ?: "" // Use a default name if displayName is null
        val email = user?.email ?: "" // Ensure email is not null
        val phoneNumber = user?.phoneNumber ?: "" // Ensure phoneNumber is not null

        RaveUiManager(requireActivity())
            .setAmount(adapter.calculateTotalPrice())
            .setCurrency("KES")
            .setCountry("KE")
            .setEmail(email)
            .setfName(name)
            .setPhoneNumber(phoneNumber)
            .setNarration("Payment for products")
            .setPublicKey("FLWPUBK_TEST-dd026b84a21b9cd2d7f44fc7786035b1-X")
            .setEncryptionKey("FLWSECK_TESTdabb62a23a4f")
            .setTxRef(System.currentTimeMillis().toString())
            .setPhoneNumber(phoneNumber, true)
            .acceptAccountPayments(true)
            .acceptCardPayments(true)
            .acceptMpesaPayments(true)
            .acceptAchPayments(true)
            .acceptGHMobileMoneyPayments(true)
            .acceptUgMobileMoneyPayments(true)
            .acceptZmMobileMoneyPayments(true)
            .acceptRwfMobileMoneyPayments(true)
            .acceptSaBankPayments(true)
            .acceptUkPayments(true)
            .acceptBankTransferPayments(true)
            .acceptUssdPayments(true)
            .acceptBarterPayments(true)
            .allowSaveCardFeature(true)
            .onStagingEnv(true)
            .isPreAuth(true)
            .shouldDisplayFee(true)
            .showStagingLabel(false)
            .initialize()
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            val message = data.getStringExtra("response")
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                // Payment was successful
                updatePurchaseStatus()
                sendOrderConfirmation()
                Toast.makeText(context, "Purchase successful!", Toast.LENGTH_SHORT).show() // Notify user of successful purchase
                // Update purchase status in Firebase
            } else if (resultCode == RavePayActivity.RESULT_ERROR) {
                // Payment failed
                Log.e(TAG, "Payment failed: $message")
                Toast.makeText(context, "Payment failed: $message", Toast.LENGTH_SHORT).show() // Notify user of failed purchase
            } else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                // Payment was cancelled
                Log.i(TAG, "Payment cancelled: $message")
                Toast.makeText(context, "Payment cancelled: $message", Toast.LENGTH_SHORT).show() // Notify user of cancelled purchase
            }
        }
    }

    private fun updatePurchaseStatus() {
        val purchase = hashMapOf(
            "userId" to userId,
            "items" to cartItems,
            "totalPrice" to adapter.calculateTotalPrice(),
            "status" to "pending"
        )

        db.collection("users").document(userId).collection("purchases").add(purchase)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Purchase added with ID: ${documentReference.id}")
                // Update UI or notify user
                Toast.makeText(context,"Purchase status updated successfully.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding purchase", e)
                // Show error message to user
                Toast.makeText(context, "Failed to update purchase status.", Toast.LENGTH_SHORT).show()
            }
    }


    private fun sendConfirmationEmail() {
        val firebaseAuth= FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser
        val name=user?.displayName ?: "Customer" // Use a default name if displayName is null
        val email=user?.email ?: "" // Ensure email is not null
        val emailTo = email // The user's email address
        val emailSubject = "Purchase Confirmation"
        val itemsList = generatePurchasedItemsList() // Generate the list of purchased items
        val totalAmount = adapter.calculateTotalPrice() // Calculate the total amount

        // Construct the email body
        val emailBody = StringBuilder()
            .append("Dear $name,\n\n")
            .append("This email is to confirm that the following products have been purchased:\n\n")
            .append(itemsList)
            .append("\n\nTotal Amount: KES $totalAmount\n\n")
            .append("Thank you for your purchase.")
            .toString()

        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_EMAIL, arrayOf(emailTo))
            putExtra(Intent.EXTRA_SUBJECT, emailSubject)
            putExtra(Intent.EXTRA_TEXT, emailBody)
            type = "message/rfc822"
        }

        try {
            startActivity(Intent.createChooser(intent, "Choose an Email client :"))
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendOrderConfirmation() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.currentUser?.reload()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = firebaseAuth.currentUser
                val name = user?.displayName ?: "Customer"
                val email = user?.email ?: ""
                val emailTo = email
                val companyEmail = "mariaolotira@gmail.com"
                val emailSubject = "Purchase Confirmation"
                val itemsList = generatePurchasedItemsList()
                val totalAmount = adapter.calculateTotalPrice()
                val emailBody = StringBuilder()
                    .append("Dear $name,\\n\\n")
                    .append("This email is to confirm that the following products have been purchased:\\n\\n")
                    .append(itemsList)
                    .append("\\n\\nTotal Amount: KES $totalAmount\\n\\n")
                    .append("by $name ($email)")
                    .append("Thank you for your purchase.")
                    .toString()

                if (isNetworkAvailable(requireContext())) {
                    try {
                        Log.d(TAG, "Attempting to send email...")
                        sendEmailWithCloudFunction(emailTo, emailSubject, emailBody)
                        Log.d(TAG, "Email sent successfully!")
                        Toast.makeText(context, "Email sent successfully!", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to send email: ${e.message}")
                        Toast.makeText(context, "Failed to send email. Please check your internet connection.", Toast.LENGTH_LONG).show()
                    }

                } else {
                    Log.e(TAG, "No network connection available. Unable to send order confirmation email.")
                    Toast.makeText(context, "No network connection available. Unable to send order confirmation email.", Toast.LENGTH_LONG).show()
                }
            } else {
                Log.e(TAG, "Failed to reload user data.", task.exception)
                Toast.makeText(context, "Failed to reload user data. Please check your internet connection.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun sendEmailWithCloudFunction(to: String, subject: String, body: String) {
        val functions = FirebaseFunctions.getInstance()
        val data = hashMapOf(
            "to" to to,
            "subject" to subject,
            "body" to body
        )

        functions.getHttpsCallable("sendOrderConfirmation")
            .call(data)
            .addOnSuccessListener { result ->
                val success = result.data as Boolean
                if (success) {
                    Toast.makeText(context, "Email sent successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failed to send email. Please check your internet connection.", Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Error sending email: ${exception.message}", Toast.LENGTH_LONG).show()
            }
    }


    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }




    private fun generatePurchasedItemsList(): String {
        var itemsList = ""
        for (item in cartItems) {
            itemsList += "${item.packageItem.name} - Quantity: ${item.quantity}, "
        }
        Log.d(TAG, "Generated Items List: $itemsList") // Debug log
        return itemsList.trimEnd(',')
    }





}