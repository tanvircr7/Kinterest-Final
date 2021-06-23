package com.example.demofirebaseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent

import android.util.Log
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_in.*


/*
TO DO:
1.Remove unncessary UI components.
2. Databinding
3. add Google federated Sign In
 */

class SignIn : AppCompatActivity() {

    //private lateinit var btnSignInGoogle: SignInButton
    private companion object {
        private const val TAG = "SignInActivity"
        private const val RC_GOOGLE_SIGN_IN = 4926
    }
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_DemoFirebaseApp)
        setContentView(R.layout.activity_sign_in)
        //btnSignInGoogle = findViewById(R.id.btnSignInGoogle)

        auth = Firebase.auth

        // so if I click on sign in I go to sign up??
        btnRegisterSignIn.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }

        btnSignIn.setOnClickListener {
            if(!tiEmail.text.toString().trim().isNullOrBlank() &&
                !tiPassword.text.toString().trim().isNullOrBlank())
                signIn(tiEmail.text.toString(), tiPassword.text.toString())
        }
    }

    // [START on_start_check_user]
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        //Firebase.auth.signOut() <- Was to log out proactively  FOR TESTING
        val currentUser = auth.currentUser
        Log.w(TAG,"${currentUser?.email.toString()}")
        if(currentUser != null){
            alreadySignedIn();
        }

    }
    // [END on_start_check_user]

    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                    reload()
                }
            }
        // [END sign_in_with_email]
    }

    private fun updateUI(user: FirebaseUser?) {
        finish()
        startActivity(Intent(this,MainActivity::class.java))
        Toast.makeText(applicationContext,"Signed in successfully",Toast.LENGTH_SHORT).show()
    }

    private fun alreadySignedIn() {
        finish();
        //startActivity(getIntent());
        Toast.makeText(applicationContext,"Already Signed In",Toast.LENGTH_LONG).show()
        startActivity(Intent(this,MainActivity::class.java))
    }

    private fun reload() {
        finish();
        startActivity(getIntent());
    }

}

