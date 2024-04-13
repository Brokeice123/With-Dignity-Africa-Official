package com.example.withdignityfinal

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var splash_btn_Log: Button
    private lateinit var splash_btn_Reg: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        // hide status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        splash_btn_Log = findViewById(R.id.btnSplashLogin)
        splash_btn_Reg = findViewById(R.id.btnSplashReg)

        splash_btn_Log.setOnClickListener{
            var gotoSignIn = Intent(this, SignInActivity::class.java)
            startActivity(gotoSignIn)
        }

        splash_btn_Reg.setOnClickListener {
            var gotoSignUp = Intent(this, SignUpActivity::class.java)
            startActivity(gotoSignUp)
        }

    }

    override fun onStart() {
        super.onStart()
        if(FirebaseAuth.getInstance().currentUser != null){
            val i  = Intent(this,MainActivity::class.java)
            startActivity(i)
        }
    }
}