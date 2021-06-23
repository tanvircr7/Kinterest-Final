package com.example.demofirebaseapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_community.*
import kotlinx.android.synthetic.main.activity_image_firebase.*

class CommunityActivity : AppCompatActivity() {

    lateinit private var database: DatabaseReference
    lateinit private var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        database = Firebase.database.getReference("users")
        auth = FirebaseAuth.getInstance()

        rvCommunityRecycler.setHasFixedSize(true)
        rvCommunityRecycler.layoutManager = LinearLayoutManager(this)

        getData()

        backToCommunityBtn.setOnClickListener {
            Intent(this,ProfileActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun getData() {
        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var list = ArrayList<User>()
                for( user in snapshot.children ){
                    var node = user.getValue<User>()
                    list.add(node as User)
                }
                Toast.makeText(applicationContext,"List size"+list.size.toString(), Toast.LENGTH_LONG).show()
                if(list.size>0){
                    var adapter = CommunityAdapter(applicationContext,list)
                    rvCommunityRecycler.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,"Error :: Data Retrieving Failed!!", Toast.LENGTH_LONG).show()
            }

        })
    }

}