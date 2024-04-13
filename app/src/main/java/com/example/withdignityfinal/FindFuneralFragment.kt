package com.example.withdignityfinal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.withdignityfinal.adapter.ProductAdapter
import com.example.withdignityfinal.data.Product
import com.example.withdignityfinal.databinding.FragmentFindFuneralBinding
import com.example.withdignityfinal.databinding.RecyclerItemBinding
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.content.Context
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FindFuneralFragment : Fragment() {

    private lateinit var binding: FragmentFindFuneralBinding
    private val db = Firebase.firestore
    private lateinit var productAdapter: ProductAdapter
    private lateinit var adapter: FirestoreRecyclerAdapter<Product, ProductViewHolder>
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
        val query = FirebaseFirestore.getInstance().collection("Products")
        val options = FirestoreRecyclerOptions.Builder<Product>()
            .setQuery(query, Product::class.java)
            .build()

        adapter = object : FirestoreRecyclerAdapter<Product, ProductViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
                val binding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ProductViewHolder(binding)
            }

            override fun onBindViewHolder(holder: ProductViewHolder, position: Int, model: Product) {
                holder.bind(model)
                holder.itemView.setOnClickListener {
                    Log.d("ProductAdapter", "Product clicked: ${model.name}")
                    // Create an instance of the ProductDetail fragment
                    val productDetailFragment = ProductDetail().apply {
                        arguments = Bundle().apply {
                            putParcelable("product", model)
                        }
                    }
                    // Get the FragmentManager
                    val fragmentManager = (it.context as FragmentActivity).supportFragmentManager
                    Log.d("ProductAdapter", "Attempting to navigate to ProductDetail")
                    // Begin the transaction to replace the current fragment with the ProductDetail fragment
                    fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, productDetailFragment)
                        .addToBackStack(null) // Optional: Adds the transaction to the back stack
                        .commit()
                    Log.d("ProductAdapter", "Navigation to ProductDetail attempted")

                }
            }
        }

        // Initialize productAdapter here
        productAdapter = ProductAdapter(productList)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }


    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    fun loadImage(context: Context, imageUrl: String, imageView: ImageView) {
        Glide.with(context)
            .load(imageUrl)
            .apply(RequestOptions()
                .placeholder(R.drawable.checkout_image) // Placeholder image
                .error(R.drawable.ic_menu_gallery) // Error image in case of loading failure
            )
            .into(imageView)
    }

    private inner class ProductViewHolder(private val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                // Check if the images list is not empty before trying to access its elements
                if (product.images.isNotEmpty()) {
                    // If the list is not empty, safely access the first element
                    loadImage(requireContext(), product.images[0], recimage)
                } else {
                    // If the list is empty, you can either set a default image or leave the ImageView empty
                    // For example, to set a default image, you can use:
                    // recimage.setImageResource(R.drawable.default_image)
                    // Or, to leave the ImageView empty, you can use:
                    // recimage.setImageDrawable(null)
                }
                recprice.text = "KSH ${product.price}"
                recdescription.text = product.description
                rectitle.text = product.name
            }
        }
    }

    private fun getProductData() {
        db.collection("Products")
            .get()
            .addOnSuccessListener { querySnapshot: QuerySnapshot ->
                productList.clear()
                for (product in querySnapshot.documents) {
                    val productData = product.data
                    val id = productData?.get("id") as? String ?:""
                    val name = productData?.get("name") as? String ?: ""
                    val description = productData?.get("description") as? String ?: ""
                    val images = productData?.get("images") as? ArrayList<String> ?: arrayListOf()
                    val price = productData?.get("price") as? Double ?: 0.00
                    val priceFloat = price.toFloat()

                    val productObj = Product(id,name, priceFloat, description, images)
                    productList.add(productObj)
                }

                productAdapter.updateData(productList)
            }
            .addOnFailureListener { exception ->
                Log.e("FindFuneralFragment", "Failed to retrieve product data", exception)
                Toast.makeText(requireContext(), exception.message, Toast.LENGTH_SHORT).show()
            }
    }

    fun clicked(view: View) {}
}