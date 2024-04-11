package com.example.withdignityfinal.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.withdignityfinal.data.Product
import com.example.withdignityfinal.databinding.RecyclerItemBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore

class ProductAdapter(
    private val context: MutableList<Product>,
    private val db: FirebaseFirestore = Firebase.firestore,
    private val query: Query = db.collection("Products")
) : RecyclerView.Adapter<ProductAdapter.ProductsViewsHolder>() {

    private val differ: AsyncListDiffer<Product>
        get() = AsyncListDiffer(this, diffCallback)

    fun updateData(newProductList: List<Product>) {
        differ.submitList(newProductList)
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewsHolder {
        return ProductsViewsHolder(
            RecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ProductsViewsHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    public class ProductsViewsHolder(private val binding: RecyclerItemBinding) : ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                Glide.with(itemView).load(product.images[0]).into(recimage)
                recprice.text = "KSH ${product.price}"
                recdescription.text = product.description
                rectitle.text = product.name
            }
        }
    }

    init {
        query.addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val products = snapshot.toObjects(Product::class.java)
                updateData(products)
            }
        }
    }
}