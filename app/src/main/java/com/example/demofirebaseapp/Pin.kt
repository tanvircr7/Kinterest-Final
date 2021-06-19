package com.example.demofirebaseapp

import kotlin.random.Random.Default.nextInt

data class Pin(val name: String, val likes: Int) {
    var pinUrl = "https://picsum.photos/${nextInt(200,500)}/${nextInt(200,500)}"

}