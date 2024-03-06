package com.example.withdignityfinal.data

import java.util.ArrayList
import androidx.annotation.Keep

class Product{
    val id: String? = null
    var name: String? = null
    val category: String? = null
    var price: Float? = null
    var description: String? = null
    var images: ArrayList<String> = arrayListOf()
    constructor(){}

    constructor(
        name: String?,
        images: ArrayList<String>,
        description: String?,
        price: Float?
    ) {
        this.name = name
        this.images = images
        this.description = description
        this.price = price
    }


}