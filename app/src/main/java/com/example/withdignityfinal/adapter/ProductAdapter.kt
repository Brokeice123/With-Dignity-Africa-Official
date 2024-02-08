package com.example.withdignityfinal.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.withdignityfinal.data.Product
import com.example.withdignityfinal.databinding.RecyclerItemBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ProductAdapter(
    var c: Context,
    productList: MutableList<Product>
): RecyclerView.Adapter<ProductAdapter.ProductsViewsHolder>() {

    private val db = FirebaseFirestore.getInstance("https://console.firebase.google.com/project/productsadder-c0046/database/productsadder-c0046-default-rtdb/data")
    private val query: Query = db.collection("Products")
    private val differ: AsyncListDiffer<Product>
        get() = AsyncListDiffer(this, diffCallback)

    inner class ProductsViewsHolder(private val binding: RecyclerItemBinding): ViewHolder(binding.root){
        fun bind (product: Product){
            binding.apply {
                Glide.with(itemView).load(product.images?.get(0)).into(recimage)
                recprice.text = "$ ${product.price}"
                recdescription.text = product.description
                rectitle.text = product.name
            }
        }
    }

    private val diffCallback = object  : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    init {
        query.addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val products = snapshot.toObjects(Product::class.java)
                differ.submitList(products)
            }
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
}