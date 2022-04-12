package com.aadya.gist.login.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectLanguageViewModel : ViewModel() {
    private val selectedlanguageviewmodel : MutableLiveData<Int> = MutableLiveData()
    fun getSelectedLanguageViewState() : MutableLiveData<Int>? { return  selectedlanguageviewmodel}


    fun selectLanguage( selectedLang: Int){
        selectedlanguageviewmodel.value = selectedLang
    }

}