package com.example.withdignityfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView=findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener(){ menuItem ->
            when(menuItem.itemId){
                R.id.home_bottomnav -> {
                    setBottomNavigationItemTextColor(R.color.white)
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.package_bottomnav -> {
                    setBottomNavigationItemTextColor(R.color.white)
                    replaceFragment(FuneralPackageFragment())
                    true
                }
                R.id.findfuneral_bottomnav -> {
                    setBottomNavigationItemTextColor(R.color.white)
                    replaceFragment(FindFuneralFragment())
                    true
                }
                R.id.help_bottomnav -> {
                    setBottomNavigationItemTextColor(R.color.white)
                    replaceFragment(HelpFragment())
                    true
                }
                R.id.checkout_bottomnav -> {
                    setBottomNavigationItemTextColor(R.color.white)
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

    private fun setBottomNavigationItemTextColor(colorResId: Int) {
        val color = ContextCompat.getColor(this, colorResId)
        val uncheckedColor = Color.WHITE

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