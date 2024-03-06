package com.example.withdignityfinal.adapter

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.withdignityfinal.R
import com.example.withdignityfinal.data.CartProduct
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CartAdapter(private val cartItems: MutableList<CartProduct>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.nameTextView)
        val price: TextView = itemView.findViewById(R.id.priceTextView)
        val image: ImageView = itemView.findViewById(R.id.imageView)
        val quantity: TextView = itemView.findViewById(R.id.quantityTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.package_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.name.text = cartItem.packageItem.name
        holder.price.text = cartItem.packageItem.price.toString()
        holder.image.setImageResource(cartItems[position].image)
        holder.quantity.text = cartItem.quantity.toString()

        // Set up the remove button click listener
        holder.itemView.findViewById<Button>(R.id.ButtonRemoveItem).setOnClickListener {
            removeItem(position)
        }
    }

    private fun removeItem(position: Int) {
        val cartItemToRemove = cartItems[position]
        // Assuming each cart item in Firestore has a unique ID stored in cartItemToRemove.id
        val orderId = cartItemToRemove.id // Adjust this based on your actual data structure

        // Remove the item from Firestore
        val db = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        db.collection("users").document(userId).collection("cart").document(orderId)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "Order removed successfully")
                // Remove the item from the local list and notify the adapter
                cartItems.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, cartItems.size)
                // Update the UI to reflect the removal and new total price
                displayTotalPrice()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error removing order", e)
            }
    }

    // Define an interface inside your CartAdapter
    interface OnTotalPriceUpdatedListener {
        fun onTotalPriceUpdated(totalPrice: Double)
    }

    // Add a listener to your adapter
    var totalPriceListener: OnTotalPriceUpdatedListener? = null

    // Modify the displayTotalPrice method to use the listener
    private fun displayTotalPrice() {
        val totalPrice = calculateTotalPrice()
        totalPriceListener?.onTotalPriceUpdated(totalPrice)
    }

    override fun getItemCount(): Int = cartItems.size

    fun calculateTotalPrice(): Double {
        var total = 0.0
        for (item in cartItems) {
            total += item.packageItem.price * item.quantity
        }
        return total
    }
}