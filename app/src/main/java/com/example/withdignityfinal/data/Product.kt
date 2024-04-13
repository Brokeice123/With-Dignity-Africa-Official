package com.example.withdignityfinal.data

import android.os.Parcelable
import java.util.ArrayList
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class Product(
    val id: String? = null,
    val name: String? = null,
    val price: Float? = null,
    val description: String? = null,
    val images: ArrayList<String> = arrayListOf(),
    val category: String? = null
): Parcelable
