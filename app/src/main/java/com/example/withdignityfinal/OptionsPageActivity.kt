package com.example.withdignityfinal

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

class OptionsPageActivity : AppCompatActivity() {


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options_page)
        if (FirebaseApp.getApps(baseContext).none { it.name == "Products Adder" }) {
            val options = FirebaseOptions.Builder()
                .setApiKey("AIzaSyARxv-u8nzqEG5YnGg11upxptLyK3QkxdI")
                .setApplicationId("1:401245065563:android:ecd8d6441c72b9f9caca7e")
                .setDatabaseUrl("https://console.firebase.google.com/project/productsadder-c0046") // use the Firebase Console URL
                .setProjectId("productsadder-c0046") // set the projectId field
                .build()

            FirebaseApp.initializeApp(this, options, "Products Adder")
        }




    }
}

