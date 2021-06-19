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
    fun writeNewUser(userId: String, name: String, email: String) {
        val user = User(name, email)
        Log.w("RealtimeDB","Writing new user")
        Log.i(TAG,userId)
        database.child("users").child(userId).setValue(user)
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