package com.example.withdignityfinal

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.withdignityfinal.data.PackageItem
import com.example.withdignityfinal.data.Product
import com.example.withdignityfinal.databinding.FragmentProductDetailBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProductDetail : Fragment() {

    private lateinit var binding: FragmentProductDetailBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var userId: String

    companion object {
        @JvmStatic
        fun newInstance(product: Product) =
            ProductDetail().apply {
                arguments = Bundle().apply {
                    putParcelable("product", product)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()
        userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        val product = arguments?.getParcelable<Product>("product")
        product?.let {
            binding.apply {
                rectitle.text = it.name
                recdescription.text = it.description
                recprice.text = "KSH ${it.price}"
                Glide.with(requireContext())
                    .load(it.images.firstOrNull())
                    .error(R.drawable.checkout_image)
                    .into(recimage)
            }

            view.findViewById<Button>(R.id.addtocart).setOnClickListener {
                addToCart(product)
            }
        }
    }

    private fun clicked(button: Button, context: Context) {
        button.text = "clicked"

        val myAnim: Animation = AnimationUtils.loadAnimation(context, R.anim.bounce)
        val interpolator = MyBounceInterpolator(amplitude = 0.2, frequency = 20.0)
        myAnim.interpolator = interpolator

        button.startAnimation(myAnim)
    }

    private fun addToCart(product: Product) {
        val cartItem = hashMapOf(
            "product" to hashMapOf("id" to product.id, "name" to product.name,"price" to product.price),
            "quantity" to 1,
            "image" to product.images.firstOrNull()
        )

        db.collection("users").document(userId).collection("cart")
            .whereEqualTo("product.id", product.id)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result?.documents?.size == 0) {
                        db.collection("users").document(userId).collection("cart").add(cartItem)
                            .addOnSuccessListener { documentReference ->
                                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                                val viewToAnimate = view?.findViewById<Button>(R.id.addtocart)
                                if (viewToAnimate != null) {
                                    clicked(viewToAnimate, requireContext())
                                }
                                Snackbar.make(viewToAnimate!!, "Item added to cart", Snackbar.LENGTH_LONG).show()
                            }
                            .addOnFailureListener { e ->
                                Log.w(ContentValues.TAG, "Error adding document", e)
                            }
                    } else {
                        val document = task.result?.documents?.first()
                        val newQuantity = document?.getLong("quantity")?.toInt()?.plus(1) ?: 1
                        db.collection("users").document(userId).collection("cart").document(document?.id ?: "")
                            .update("quantity", newQuantity)
                            .addOnSuccessListener {
                                Log.d(ContentValues.TAG, "Quantity updated successfully")
                                val viewToAnimate = view?.findViewById<Button>(R.id.addtocart)
                                if (viewToAnimate != null) {
                                    clicked(viewToAnimate, requireContext())
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
