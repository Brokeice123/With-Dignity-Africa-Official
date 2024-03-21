package com.example.withdignityfinal.data

import java.util.ArrayList
import androidx.annotation.Keep

@Keep
data class Product(
    val id: String? = null,
    val name: String? = null,
    val price: Float? = null,
    val description: String? = null,
    val images: ArrayList<String> = arrayListOf()
)