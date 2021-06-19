package com.example.demofirebaseapp

class Upload() {

    private lateinit var imageName: String
    private lateinit var imageUrl: String

    public fun getImageName(): String {
        return imageName
    }
    public fun getImageUrl(): String {
        return imageUrl
    }
    public fun setImageName(str: String){
        this.imageName = str
    }
    public fun setImageUrl(str: String){
        this.imageUrl = str
    }
}