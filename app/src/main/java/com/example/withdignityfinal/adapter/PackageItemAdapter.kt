package com.example.withdignityfinal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.withdignityfinal.R
import com.example.withdignityfinal.data.PackageItem

class PackageItemAdapter(private val packageItems: MutableList<PackageItem>) : RecyclerView.Adapter<PackageItemAdapter.PackageItemViewHolder>() {

    class PackageItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.package_item_layout, parent, false)
        return PackageItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: PackageItemViewHolder, position: Int) {
        if (packageItems.size > 1) {
            val packageItem = packageItems[position]
            holder.nameTextView.text = packageItems[position].name
            holder.priceTextView.text = packageItems[position].price.toString()
            Glide.with(holder.itemView.context)
                .load(packageItem.image) // Assuming packageItem.image is a URL or file path
                .into(holder.imageView)
        } else {
            val packageItem = packageItems[position]
            holder.nameTextView.text = packageItems[0].name
            holder.priceTextView.text = packageItems[0].price.toString()
            Glide.with(holder.itemView.context)
                .load(packageItem.image) // Assuming packageItem.image is a URL or file path
                .into(holder.imageView)
        }
    }

    override fun getItemCount(): Int {
        return if (packageItems.size > 1) {
            packageItems.size
        } else {
            1
        }
    }
}

