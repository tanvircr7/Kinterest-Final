package com.example.demofirebaseapp

import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_community.*


class UserName {

    private var database: DatabaseReference = Firebase.database.getReference("users")

    public fun getUserName(currEmail: String): String{
        var tmpName = "!!"
        var tmpEmail = " "
        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for( user in snapshot.children ){
                    var node = user.getValue<User>()
                    tmpName = node!!.getUserName()
                    tmpEmail = node!!.getUserEmail()
                    //Toast.makeText(Pro,"tmpName"+tmpName,Toast.LENGTH_SHORT).show()
                    if(currEmail==tmpEmail){
                        Log.i("USER NAME","Matched "+tmpName+" "+tmpEmail)
                        return
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        return tmpName
    }
}