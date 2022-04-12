package com.aadya.gist.login.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SplashViewModel : ViewModel() {
    private val splashviewmodel : MutableLiveData<Boolean> = MutableLiveData()
    fun getSplashViewState() : MutableLiveData<Boolean>? { return  splashviewmodel}


    fun checkSplashActivityState(){
        Handler(Looper.getMainLooper()).postDelayed({
            splashviewmodel.value = true
        },2000

        )
    }

}