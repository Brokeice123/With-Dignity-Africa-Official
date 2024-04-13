package com.example.withdignityfinal

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.os.Handler
import com.airbnb.lottie.LottieAnimationView

class WelcomeActivity : AppCompatActivity() {
    private val animationDuration = 5000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val animationView = findViewById<LottieAnimationView>(R.id.animationView)
        animationView.setAnimation(R.raw.flower_loading_bar)
        animationView.playAnimation()

        // Stop the animation after the specified duration and navigate to another activity
        Handler().postDelayed({
            animationView.cancelAnimation()
            navigateToNextActivity()
        }, animationDuration)

    }

    private fun navigateToNextActivity() {
        val intent = Intent(this, SplashScreenActivity::class.java)
        startActivity(intent)
        finish() // Optional: Finish current activity to prevent going back
    }

}