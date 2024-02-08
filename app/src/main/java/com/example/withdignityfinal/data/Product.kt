package com.example.withdignityfinal.data

class Product{
    val id: String? = null
    var name: String? = null
    val category: String? = null
    var price: Float? = null
    var description: String? = null
    var images: String? = null
    constructor(){}

    constructor(
        name:String?,
        images:String?,
        description:String?,
        price:Float?,
    ){
        this.name = name
        this.images=images
        this.description=description
        this.price=price
    }
}