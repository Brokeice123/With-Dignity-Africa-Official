package com.example.withdignityfinal

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    lateinit var m_edt_name: EditText
    lateinit var m_edt_email: EditText
    lateinit var m_edt_phone: EditText
    lateinit var m_edt_password: EditText
    lateinit var m_btn_create: Button

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        // hide status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        FirebaseApp.initializeApp(this)

        m_edt_name = findViewById(R.id.edtRegname)
        m_edt_email = findViewById(R.id.edtRegemail)
        m_edt_phone = findViewById(R.id.edtRegphone)
        m_edt_password = findViewById(R.id.edtRegpassword)
        m_btn_create = findViewById(R.id.btnRegcreate)

        auth = FirebaseAuth.getInstance()
        //Create Account
        m_btn_create.setOnClickListener {
            var name = m_edt_name.text.toString().trim()
            var email = m_edt_email.text.toString().trim()
            var phone = m_edt_phone.text.toString().trim()
            var password = m_edt_password.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Cannot submit empty fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "User Created Successfully", Toast.LENGTH_SHORT).show()

                        var gotologin = Intent(this, SignInActivity::class.java)
                        startActivity(gotologin)
                    } else {
                        Toast.makeText(this, "Failed to Create Account", Toast.LENGTH_SHORT).show()
                    }
                }

            }

        }
    }
}