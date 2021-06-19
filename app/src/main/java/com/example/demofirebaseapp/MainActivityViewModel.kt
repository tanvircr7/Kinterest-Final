package com.example.demofirebaseapp

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {
    private val pinsLiveData: MutableLiveData<MutableList<Pin>>
    private val isRefreshingLiveData: MutableLiveData<Boolean>
    private val TAG = "MainActivityViewModel"

    init {
        Log.i("MainActivityViewModel","init")
        pinsLiveData = MutableLiveData()
        pinsLiveData.value = createPins()
        isRefreshingLiveData = MutableLiveData()
        isRefreshingLiveData.value = false
    }

    fun getPins(): LiveData<MutableList<Pin>> {
        return pinsLiveData
    }

    private fun createPins(): MutableList<Pin> {
        Log.i("MainActivityViewModel","OnCreate PINS")
        val pins = mutableListOf<Pin>()
        for(i in 1..50) pins.add(Pin("Person $i", i ))
        return pins
    }

    fun getRefreshingLiveData(): LiveData<Boolean> {
        return isRefreshingLiveData
    }

    fun fetchNewPins() {
        Log.i(TAG,"Fetch new pins")
        isRefreshingLiveData.value = true
        // Android OS handler not the other one
        Handler().postDelayed(Runnable {
            val pins = pinsLiveData.value
            val refreshPin = Pin("tanvir",23)
            //refreshPin.pinUrl = "https://picsum.photos/200/301"
            pins?.add(0,refreshPin)
            pinsLiveData.value = pins
            isRefreshingLiveData.value = false
        }, 1000 )
        // add new contact
        // indicate that we're in refreshing state
    }

}