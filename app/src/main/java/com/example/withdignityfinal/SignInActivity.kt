package com.example.withdignityfinal

import android.app.ProgressDialog
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
import android.widget.ImageView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

class SignInActivity : AppCompatActivity() {

    private lateinit var log_edt_email: EditText
    private lateinit var log_edt_password: EditText
    private lateinit var log_btn_log: Button

    private lateinit var loadingBarEmailPassword: ProgressDialog
    private lateinit var loadingBarGoogleSignIn: ProgressDialog
    private lateinit var client: GoogleSignInClient
    private lateinit var btnGoogleSignIn:ImageView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        // hide status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        loadingBarEmailPassword = ProgressDialog(this)
        loadingBarGoogleSignIn = ProgressDialog(this)

        btnGoogleSignIn = findViewById(R.id.googleSignInButton)
        val  options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            // dont worry about this error
            .requestEmail()
            .build()
        client = GoogleSignIn.getClient(this,options)

        FirebaseApp.initializeApp(this)

        log_edt_email = findViewById(R.id.edtLogemail)
        log_edt_password = findViewById(R.id.edtLogpassword)
        log_btn_log = findViewById(R.id.btnLog)

        auth = FirebaseAuth.getInstance()

        log_btn_log.setOnClickListener {
            // Show loading bar
            showLoadingBarEmailPassword()

            var email = log_edt_email.text.toString().trim()
            var password = log_edt_password.text.toString().trim()

            //Validate Input
            if (email.isEmpty() || password.isEmpty()) {
                // Hide loading bar
                hideLoadingBarEmailPassword()

                Toast.makeText(this, "One of the inputs is empty", Toast.LENGTH_SHORT).show()
            } else{
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
                    // Hide loading bar
                    hideLoadingBarEmailPassword()

                    if (it.isSuccessful){
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        var gotomain = Intent(this, MainActivity::class.java)
                        startActivity(gotomain)
                        finish()
                    } else{
                        Toast.makeText(this, "Login Failed. Kindly check your email or password", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

        // Set click listener for Google Sign In button
        btnGoogleSignIn.setOnClickListener {
            // Show loading bar for Google Sign-In
            showLoadingBarGoogleSignIn()

            val intent = client.signInIntent
            startActivityForResult(intent,10001)
        }

    }

    private fun showLoadingBarEmailPassword() {
        loadingBarEmailPassword.setMessage("Signing in...")
        loadingBarEmailPassword.setCancelable(false)
        loadingBarEmailPassword.show()
    }

    private fun hideLoadingBarEmailPassword() {
        loadingBarEmailPassword.dismiss()
    }

    private fun showLoadingBarGoogleSignIn() {
        loadingBarGoogleSignIn.setMessage("Signing in with Google...")
        loadingBarGoogleSignIn.setCancelable(false)
        loadingBarGoogleSignIn.show()
    }

    private fun hideLoadingBarGoogleSignIn() {
        loadingBarGoogleSignIn.dismiss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==10001){
            showLoadingBarGoogleSignIn()

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken,null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener{task->
                    // Hide loading bar for Google Sign-In
                    hideLoadingBarGoogleSignIn()

                    if(task.isSuccessful){

                        val i  = Intent(this,HomeFragment::class.java)
                        startActivity(i)

                    }else{
                        Toast.makeText(this,task.exception?.message,Toast.LENGTH_SHORT).show()
                    }

                }
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