package com.example.withdignityfinal.packagefragments

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import android.widget.Button
import com.example.withdignityfinal.MyBounceInterpolator
import com.example.withdignityfinal.R
import com.example.withdignityfinal.data.PackageItem
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class goldfuneral_package : Fragment() {

    private lateinit var db: FirebaseFirestore
    private lateinit var userId: String

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_goldfuneral_package, container, false)

        db = FirebaseFirestore.getInstance()
        userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        // Example product data
        val product = PackageItem("goldfuneralpackage","Gold Funeral Package", R.drawable.gold_package,40000.00)

        val addToCartButton: Button = view.findViewById(R.id.addtocart)
        addToCartButton.setOnClickListener {
            addToCart(product)
        }

        val goldfuneraldeposit = view.findViewById<Button>(R.id.deposit)
        goldfuneraldeposit.setOnClickListener {
            // Handle click event here
            val fragment = com.example.withdignityfinal.preneedfragments.deposit()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment as Fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }

    private fun clicked(button: Button, context: Context) {
        button.text = "clicked"

        val myAnim: Animation = AnimationUtils.loadAnimation(context, R.anim.bounce)
        val interpolator = MyBounceInterpolator(amplitude = 0.2, frequency = 20.0)
        myAnim.interpolator = interpolator

        button.startAnimation(myAnim)
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
                                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                                // Animation and Snackbar logic here
                                val viewToAnimate = view?.findViewById<Button>(R.id.addtocart)
                                if (viewToAnimate != null) {
                                    clicked(viewToAnimate, requireContext())
                                }
                                Snackbar.make(viewToAnimate!!, "Item added to cart", Snackbar.LENGTH_LONG).show()
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)
                            }
                    } else {
                        // Item exists, update its quantity
                        val document = task.result?.documents?.first()
                        val newQuantity = document?.getLong("quantity")?.toInt()?.plus(1) ?: 1
                        db.collection("users").document(userId).collection("cart").document(document?.id ?: "")
                            .update("quantity", newQuantity)
                            .addOnSuccessListener {
                                Log.d(TAG, "Quantity updated successfully")
                                // Animation and Snackbar logic here
                                val viewToAnimate = view?.findViewById<Button>(R.id.addtocart)
                                if (viewToAnimate != null) {
                                    clicked(viewToAnimate, requireContext())
                                }
                                Snackbar.make(viewToAnimate!!, "Existing Item added to cart", Snackbar.LENGTH_LONG).show()
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error updating document", e)
                            }
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            }
    }



}