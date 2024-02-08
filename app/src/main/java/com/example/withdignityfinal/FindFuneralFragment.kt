package com.example.withdignityfinal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.withdignityfinal.adapter.ProductAdapter
import com.example.withdignityfinal.data.Product
import com.example.withdignityfinal.databinding.FragmentFindFuneralBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class FindFuneralFragment : Fragment() {
    private lateinit var binding: FragmentFindFuneralBinding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productList: MutableList<Product>
    private val db = FirebaseFirestore.getInstance("https://console.firebase.google.com/project/productsadder-c0046/database/productsadder-c0046-default-rtdb/data")
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindFuneralBinding.inflate(inflater)
        val view = binding.root

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(requireContext()) // Use requireContext()

        productList = mutableListOf()
        getProductData()

        return view
    }

    private fun getProductData() {
        db.collection("Products")
            .get()
            .addOnSuccessListener { querySnapshot: QuerySnapshot ->
                for (product in querySnapshot.documents) {
                    val productData = product.data
                    val name = productData?.get("name") as String
                    val description = productData?.get("description") as String
                    val price = productData?.get("price") as Float
                    val images = productData?.get("images") as String

                    val productObj = Product(name, description, images,price)
                    productList.add(productObj)
                }
                productAdapter = ProductAdapter(requireContext(), productList)
                recyclerView?.adapter = productAdapter
            }
            .addOnFailureListener { exception: Exception ->
                Toast.makeText(requireContext(), exception.message, Toast.LENGTH_SHORT).show()
            }
    }
}