package com.example.withdignityfinal

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.withdignityfinal.adapter.CartAdapter
import com.example.withdignityfinal.data.CartProduct
import com.example.withdignityfinal.data.PackageItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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
                            id = document.getString("id")?:"",
                            document.getString("product.name") ?: "",
                            R.drawable.checkout_image, // Placeholder for default image
                            document.getDouble("product.price") ?: 0.0
                        )
                        val quantity = document.getLong("quantity")?.toInt() ?: 0
                        // Retrieve image as a Long and cast it to Int
                        val image = document.getLong("image")?.toInt() ?: R.drawable.carticon
                        val id = document.id
                        cartItems.add(CartProduct(packageItem, image, quantity, id))
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
}