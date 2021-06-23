package com.example.demofirebaseapp


//import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity() {

    private val TAG = "ProfileActivity"
    private lateinit var auth: FirebaseAuth
    private lateinit var userObj: User
    lateinit private var databaseReference: DatabaseReference

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

        // Load Image--------------------------------------------------------------------------------------------------
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users")

        dpImg.setOnClickListener {
            Intent(this,EditProImgActivity::class.java).also{
                startActivity(it)
            }
        }

        getUserinfo()

        // Show user Name and Email------------------------------------------------------------------------------------
        //userObj = getUserProfile()
        userEmailDb.text = getUserEmail() // setting email
        //val uName = UserName()
        //userObj.setUserEmail(uName.getUserName(userObj.getUserEmail()))
        //userNameDb.text = uName.getUserName(userObj.getUserEmail())

        //Log.i(TAG,userObj.getUserEmail()+userObj.getUserName())


        // Upload Ideas Button   Go - Profile Activity -->> Upload Activity--------------------------------------------
        ideaUploadBtn.setOnClickListener {
            Intent(this,UploadActivity::class.java).also {
                startActivity(it)
            }
        }

        // Goto Community Section--------------------------------------------------------------------------------------
        communityBtn.setOnClickListener {
            Intent(this,CommunityActivity::class.java).also{
                startActivity(it)
            }
        }

        // User loggin Out----------------------------------------------------------------------------------------------
        logOutBtn.setOnClickListener {
            Firebase.auth.signOut()
            finish()
//            Intent(this,SignIn::class.java).also{
//                startActivity(it)
//            }

            val intent = Intent(this, SignIn::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
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

    private fun getUserEmail(): String{
        var tmp = ""

        // [START get_user_profile]
        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
           // val name = user.displayName
            val email = user.email
            //val photoUrl = user.photoUrl
            //Log.i("getUserProfile",name.toString())

            // Check if user's email is verified
            //val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            //val uid = user.uid
           // var userObj = User(); userObj.setUserName(name!!); userObj.setUserEmail(email!!)
            tmp = email!!
            return tmp
        }
        // [END get_user_profile]
        //val userObj = User(); userObj.setUserName("!!"); userObj.setUserEmail("!!")
        return tmp
    }

    private fun getUserinfo() {
        databaseReference.child(auth.currentUser!!.uid).addValueEventListener(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists() && (snapshot.childrenCount > 0)){

//                    val name = snapshot.child("name").getValue().toString()
//                    txtName.setText(name)

                    if(snapshot.hasChild("image")){
                        var image = snapshot.child("image").getValue().toString()
                        Picasso.with(applicationContext).load(image).into(dpImg)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,"Error :: Data Retrieving Failed!!", Toast.LENGTH_LONG).show()
            }
        })
    }

}

