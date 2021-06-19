package com.example.demofirebaseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.tasks.await
//import kotlinx.coroutines.withContext

/*
TODO
1. finish()

*/
class SignUp : AppCompatActivity() {

    private companion object {
        private const val TAG = "SignUpActivity"
        private const val RC_GOOGLE_SIGN_IN = 5555
    }
    private lateinit var auth: FirebaseAuth
//    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = Firebase.auth

//        viewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)
        Log.w(TAG,"signup before button clicked")

        btnSignInRegister.setOnClickListener {
            onBackPressed()
        }

        btnSignUp.setOnClickListener {

            if( !tiUsername.text.toString().trim().isNullOrBlank() && !tiEmail.text.toString().trim().isNullOrBlank() &&
                !tiPassword.text.toString().trim().isNullOrBlank() ) {
                // username and tiEMail should use NOT DUPLICATE function with NullOrBlank
                createAccount(tiEmail.text.toString(), tiPassword.text.toString())
                // USE viewModel?
                // takes userId, username, userEmail and stores it on realtime DB
                val user = Firebase.auth.currentUser
                val uid = user?.uid

                var realtimeDb: RealtimeDatabase = RealtimeDatabase()

                Toast.makeText(this, "" + uid, Toast.LENGTH_SHORT).show();
                realtimeDb?.writeNewUser(uid.toString(),tiUsername.text.toString(),tiEmail.text.toString())
                Log.i(TAG,auth.uid.toString())
            }
            else {
                Toast.makeText(applicationContext,"Please fill up the fields correctly",Toast.LENGTH_SHORT).show()
            }
        }


    }



    private fun createAccount(email: String, password: String) {
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "New account creation failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                    reload()
                }
            }
        // [END create_user_with_email]
    }

    private fun sendEmailVerification() {
        // [START send_email_verification]
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                // Email Verification sent
            }
        // [END send_email_verification]
    }

    private fun updateUI(user: FirebaseUser?) {
        startActivity(Intent(this,MainActivity::class.java))
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right)
    }

    private fun reload() {
        finish();
        startActivity(getIntent());
        Toast.makeText(applicationContext, "Sign Up failed.",
            Toast.LENGTH_SHORT).show()
    }

}