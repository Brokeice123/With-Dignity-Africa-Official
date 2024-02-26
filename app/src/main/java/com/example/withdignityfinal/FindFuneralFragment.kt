package com.example.withdignityfinal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.withdignityfinal.adapter.ProductAdapter
import com.example.withdignityfinal.data.Product
import com.example.withdignityfinal.databinding.FragmentFindFuneralBinding
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FindFuneralFragment : Fragment() {

    private lateinit var binding: FragmentFindFuneralBinding
    private val db = Firebase.firestore
    private lateinit var productAdapter: ProductAdapter
    private val productList = mutableListOf<Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFindFuneralBinding.inflate(inflater)
        val view = binding.root

        setupRecyclerView()
        getProductData()

        return view
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        productAdapter = ProductAdapter(productList, db)
        binding.recyclerView.adapter = productAdapter
    }

    private fun getProductData() {
        db.collection("Products")
            .get()
            .addOnSuccessListener { querySnapshot: QuerySnapshot ->
                productList.clear()
                for (product in querySnapshot.documents) {
                    val productData = product.data
                    val name = productData?.get("name") as? String ?: ""
                    val description = productData?.get("description") as? String ?: ""
                    val images = productData?.get("images") as? ArrayList<String> ?: arrayListOf()
                    val price = productData?.get("price") as? Double ?: 0.0
                    val priceFloat = price.toFloat()

                    val productObj = Product(name, images, description, priceFloat)
                    productList.add(productObj)
                }

                productAdapter.updateData(productList)
            }
            .addOnFailureListener { exception ->
                Log.e("FindFuneralFragment", "Failed to retrieve product data", exception)
                Toast.makeText(requireContext(), exception.message, Toast.LENGTH_SHORT).show()
            }
    }
}