package com.example.demofirebaseapp


//import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity() {

    private val TAG = "ProfileActivity"
    private lateinit var auth: FirebaseAuth
    private lateinit var userObj: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        bottomNavigationView2.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.miHome -> {
                    Intent(this,MainActivity::class.java).also { startActivity(it) }

                }

//                R.id.miProfile ->{
//                    Intent(this,ProfileActivity::class.java).also { startActivity(it) }
//
//                }
            }
            true
        }

        //var realtimeDb: RealtimeDatabase = RealtimeDatabase()
        // Show user Name and Email------------------------------------------------------------------------------------
        userObj = getUserProfile()
        userEmailDb.text = userObj.email // setting email

        userNameDb.text = userObj.username







        Log.i(TAG,userObj.username+userObj.email)


        // Upload Ideas Button   Go - Profile Activity -->> Upload Activity--------------------------------------------
        ideaUploadBtn.setOnClickListener {
            Intent(this,UploadActivity::class.java).also {
                startActivity(it)
            }
        }

        // User loggin Out----------------------------------------------------------------------------------------------
        logOutBtn.setOnClickListener {
            Firebase.auth.signOut()
            finish()
            Intent(this,SignIn::class.java).also{
                startActivity(it)
            }
        }

    }
    private fun checkCurrentUser() {
        // [START check_current_user]
        val user = Firebase.auth.currentUser
        if (user != null) {
            // User is signed in
        } else {
            // No user is signed in
        }
        // [END check_current_user]
    }

    private fun getUserProfile(): User{

        // [START get_user_profile]
        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            //val photoUrl = user.photoUrl
            Log.i("getUserProfile",name.toString())

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid
            var userObj = User(name.toString(),email.toString())
            return userObj
        }
        // [END get_user_profile]
        val userObj = User("!!","!!")
        return userObj
    }

}

