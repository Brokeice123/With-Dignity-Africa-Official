package com.example.withdignityfinal.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.withdignityfinal.data.Category
import com.google.firebase.firestore.FirebaseFirestore

class CategoryViewModelFactory(
    private val firestore: FirebaseFirestore,
    private val category: Category
):ViewModelProvider.Factory {

}