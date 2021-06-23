package com.example.demofirebaseapp

import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RealtimeDatabase {
    private val TAG = "RealtimeDB"

    // [START declare_database_ref]
    private var database: DatabaseReference = Firebase.database.reference
    // [END declare_database_ref]

    init {
        // [START initialize_database_ref]
        Log.i(TAG,"init")
        // [END initialize_database_ref]
    }

    // [START rtdb_write_new_user]
    fun writeNewUser(name: String, email: String): String {
        val user = User(); user.setUserName(name); user.setUserEmail(email)
        val userId = database.child("users").push().key
        Log.w("RealtimeDB","Writing new user")
        Log.i(TAG,userId.toString())
        database.child("users").child(userId!!).setValue(user)
        return userId
    }

    // Read
    fun getUsername(userId: String) {
        Log.i(TAG,"GET USER NAME")
        database.child("users").child(userId).get().addOnSuccessListener {
            Log.i(TAG, "Got value ${it.value}")

        }.addOnFailureListener{
            Log.i(TAG, "Error getting data", it)
        }
    }




}