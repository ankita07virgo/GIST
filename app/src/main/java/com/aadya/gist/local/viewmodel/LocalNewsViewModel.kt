package com.aadya.gist.local.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.registration.model.AlertModel

class LocalNewsViewModel(localNewsRepository: LocalNewsRepository) : ViewModel() {
    private var localNewsRepository : LocalNewsRepository = localNewsRepository


    fun getLocalNewsList(navigationmenu: NavigationMenu) {
       localNewsRepository.getLocalNewsList(navigationmenu)
   }

    fun getLocalNewsListObserver() : MutableLiveData<List<NewsModel>>{
        return  localNewsRepository.getLocalNewsListState()
    }
    fun getProgressState(): LiveData<Int?>? {
        return localNewsRepository.getProgressState()
    }

    fun getAlertViewState(): LiveData<AlertModel?>? {
        return localNewsRepository.getAlertViewState()
    }

}