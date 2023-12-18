package com.example.withdignityfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView=findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener(){ menuItem ->
            when(menuItem.itemId){
                R.id.home_bottomnav -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.package_bottomnav -> {
                    replaceFragment(FuneralPackageFragment())
                    true
                }
                R.id.findfuneral_bottomnav -> {
                    replaceFragment(FindFuneralFragment())
                    true
                }
                R.id.help_bottomnav -> {
                    replaceFragment(HelpFragment())
                    true
                }
                R.id.checkout_bottomnav -> {
                    replaceFragment(CheckoutFragment())
                    true
                }
                else -> false
            }

        }

        replaceFragment(HomeFragment())

    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()
    }
}