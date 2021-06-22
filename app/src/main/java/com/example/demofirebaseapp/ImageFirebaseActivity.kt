package com.example.demofirebaseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.activity_image_firebase.*
import kotlinx.android.synthetic.main.activity_upload.*

class ImageFirebaseActivity : AppCompatActivity() {

    private val TAG = "ImageFirebaseActivity"
    lateinit private var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_firebase)

        rvFirebase.setHasFixedSize(true)
        rvFirebase.layoutManager = LinearLayoutManager(this)


        // TODO: collecting children data to show on Recyclerview
        val uploadList = mutableListOf<Upload>()
        databaseReference = FirebaseDatabase.getInstance().getReference("Upload")

        databaseReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for( stuff in snapshot.children ){
                    val upload = stuff.getValue<Upload>()
                    Log.i(TAG, upload?.getImageName().toString())
                    uploadList.add(upload!!)
                }
//                uploadList.add(Upload("hamburger","https://picsum.photos/id/237/200/300"))
//                uploadList.add(Upload("kobutor","https://picsum.photos/seed/picsum/200/300"))
//                uploadList.add(Upload("stuff","https://picsum.photos/200/300?grayscale"))

                val myFirebaseAdapter = MyFirebaseAdapter(applicationContext, uploadList)
                rvFirebase.adapter = myFirebaseAdapter
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,"Error :: Data Retrieving Failed!!",Toast.LENGTH_LONG).show()
            }
        })


//        uploadList.add(Upload("hamburger","https://picsum.photos/id/237/200/300"))
//        uploadList.add(Upload("kobutor","https://picsum.photos/seed/picsum/200/300"))
//        uploadList.add(Upload("stuff","https://picsum.photos/200/300?grayscale"))

//        val myFirebaseAdapter = MyFirebaseAdapter(applicationContext, uploadList)
//        rvFirebase.adapter = myFirebaseAdapter

    }

}