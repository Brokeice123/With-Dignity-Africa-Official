package com.example.withdignityfinal.data

sealed class Category(val category: String) {

    object pickupofthedeceased: Category("Pickup of the deceased")
    object coffinsandcakets: Category("Coffins and caskets")
    object cremation: Category("Cremation")
    object churchbookings: Category("Church/hall bookings")
    object catering: Category("Catering")
    object flowers: Category("Flowers")
    object transport: Category("Transport")
    object groceries: Category("Groceries")
}