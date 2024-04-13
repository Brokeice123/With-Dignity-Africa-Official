package com.example.withdignityfinal.data

import java.io.Serializable

class PackageItem(
    val id: String,
    var name: String,
    var image: String,
    var price: Double,
    val imageType: String
):Serializable

