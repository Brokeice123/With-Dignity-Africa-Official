package com.example.withdignityfinal.packagefragments

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.withdignityfinal.R
import com.example.withdignityfinal.data.PackageItem
import com.flutterwave.raveandroid.RavePayActivity
import com.flutterwave.raveandroid.RaveUiManager
import com.flutterwave.raveandroid.rave_java_commons.Meta
import com.flutterwave.raveandroid.rave_java_commons.RaveConstants
import com.flutterwave.raveandroid.rave_java_commons.SubAccount
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class isithunzi_package : Fragment() {

    private lateinit var db: FirebaseFirestore
    private lateinit var userId: String

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_isithunzi_package, container, false)

        db = FirebaseFirestore.getInstance()
        userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        // Example product data
        val product = PackageItem("isithunzipackage","Isithunzi Funeral Package", R.drawable.funeral_tent,42280.00)

        val addToCartButton: Button = view.findViewById(R.id.addtocart)
        addToCartButton.setOnClickListener {
            addToCart(product)
        }

        val isithunzideposit = view.findViewById<Button>(R.id.deposit)
        isithunzideposit.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.preneedfragments.deposit()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val textView = view.findViewById<TextView>(R.id.marquee)
        textView.ellipsize = TextUtils.TruncateAt.MARQUEE
        textView.marqueeRepeatLimit = 2 // Repeat the marquee animation indefinitely
        textView.isSelected = true // Start the marquee animation

        return view
    }

    private fun createAddToCartAnimation(button: Button) {
        // Set the button's background to the progress animation drawable
        button.setBackgroundResource(R.drawable.progress_animation)
        val drawable = button.background as AnimationDrawable
        drawable.start()

        // After a delay, stop the animation and revert the button to its normal state
        Handler(Looper.getMainLooper()).postDelayed({
            drawable.stop()
            button.setBackgroundResource(R.drawable.button_background)
            button.text = "Added to Cart"
        }, 2000) // Adjust the delay as needed
    }




    private fun addToCart(product: PackageItem) {
        // Define the cart item as a map
        val cartItem = hashMapOf(
            "product" to hashMapOf("id" to product.id, "name" to product.name,"price" to product.price),
            "quantity" to 1,
            "image" to product.image
        )

        // Query Firestore for the item by product ID
        db.collection("users").document(userId).collection("cart")
            .whereEqualTo("product.id", product.id) // Assuming product.id is the unique identifier
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result?.documents?.size == 0) {
                        // Item does not exist in cart, add it
                        db.collection("users").document(userId).collection("cart").add(cartItem)
                            .addOnSuccessListener { documentReference ->
                                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                                // Animation and Snackbar logic here
                                val viewToAnimate = view?.findViewById<Button>(R.id.addtocart)
                                if (viewToAnimate != null) {
                                    createAddToCartAnimation(viewToAnimate)
                                }
                                Snackbar.make(viewToAnimate!!, "Item added to cart", Snackbar.LENGTH_LONG).show()
                            }
                            .addOnFailureListener { e ->
                                Log.w(ContentValues.TAG, "Error adding document", e)
                            }
                    } else {
                        // Item exists, update its quantity
                        val document = task.result?.documents?.first()
                        val newQuantity = document?.getLong("quantity")?.toInt()?.plus(1) ?: 1
                        db.collection("users").document(userId).collection("cart").document(document?.id ?: "")
                            .update("quantity", newQuantity)
                            .addOnSuccessListener {
                                Log.d(ContentValues.TAG, "Quantity updated successfully")
                                // Animation and Snackbar logic here
                                val viewToAnimate = view?.findViewById<Button>(R.id.addtocart)
                                if (viewToAnimate != null) {
                                    createAddToCartAnimation(viewToAnimate)
                                }
                                Snackbar.make(viewToAnimate!!, "Existing Item added to cart", Snackbar.LENGTH_LONG).show()
                            }
                            .addOnFailureListener { e ->
                                Log.w(ContentValues.TAG, "Error updating document", e)
                            }
                    }
                } else {
                    Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                }
            }
    }

}