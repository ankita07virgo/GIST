package com.aadya.gist.covid19.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.registration.model.AlertModel

class Covid19ViewModel(covid19Repository: Covid19Repository) : ViewModel() {
    private var covid19Repository: Covid19Repository
    init {
        this.covid19Repository = covid19Repository
    }


   fun getCovid19NewsList(navigationmenu: NavigationMenu) {
       covid19Repository.getCovid19List(navigationmenu)
   }

    fun getCovid19NewsListObserver() : MutableLiveData<List<NewsModel>>{
        return  covid19Repository.getCovid19ListState()
    }

    fun getProgressState(): LiveData<Int?>? {
        return covid19Repository.getProgressState()
    }

    fun getAlertViewState(): LiveData<AlertModel?>? {
        return covid19Repository.getAlertViewState()
    }
}