package com.example.withdignityfinal

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
                    setBottomNavigationItemTextColor(R.color.pink)
                    replaceFragment(com.example.withdignityfinal.HomeFragment())
                    true
                }
                R.id.package_bottomnav -> {
                    setBottomNavigationItemTextColor(R.color.pink)
                    replaceFragment(com.example.withdignityfinal.FuneralPackageFragment())
                    true
                }
                R.id.findfuneral_bottomnav -> {
                    setBottomNavigationItemTextColor(R.color.pink)
                    replaceFragment(com.example.withdignityfinal.FindFuneralFragment())
                    true
                }
                R.id.help_bottomnav -> {
                    setBottomNavigationItemTextColor(R.color.pink)
                    replaceFragment(com.example.withdignityfinal.HelpFragment())
                    true
                }
                R.id.checkout_bottomnav -> {
                    setBottomNavigationItemTextColor(R.color.pink)
                    replaceFragment(com.example.withdignityfinal.CheckoutFragment())
                    true
                }
                else -> false
            }

        }

        replaceFragment(com.example.withdignityfinal.HomeFragment())

    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()
    }

    private fun setBottomNavigationItemTextColor(colorResId: Int) {
        val color = ContextCompat.getColor(this, colorResId)
        val uncheckedColor = Color.BLACK

        val colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_checked)
            ),
            intArrayOf(
                color, // Color when checked
                uncheckedColor // Color when unchecked
            )
        )

        bottomNavigationView.itemTextColor = colorStateList
    }
}