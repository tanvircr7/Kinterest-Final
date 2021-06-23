package com.example.demofirebaseapp

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class User() {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
    private var userName: String = "!!"
    private var userEmail: String = "!!"

    public fun getUserName(): String {
        return userName
    }
    public fun getUserEmail(): String {
        return userEmail
    }
    public fun setUserName(str: String){
        this.userName = str
    }
    public fun setUserEmail(str: String){
        this.userEmail = str
    }

}