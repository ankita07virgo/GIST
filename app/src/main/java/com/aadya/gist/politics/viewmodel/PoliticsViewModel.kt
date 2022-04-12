package com.aadya.gist.politics.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.registration.model.AlertModel

class PoliticsViewModel(politicsRepository: PoliticsRepository) : ViewModel() {
    private var politicsRepository: PoliticsRepository
    init {
        this.politicsRepository = politicsRepository
    }


   fun getPoliticsNewsList(navigationmenu: NavigationMenu) {
       politicsRepository.getPoliticsNewsList(navigationmenu)
   }

    fun getPoliticsNewsListObserver() : MutableLiveData<List<NewsModel>>{
        return  politicsRepository.getPoliticsListState()
    }


    fun getPoliticsImagesList(navigationmenu: NavigationMenu) {
        politicsRepository.getPoliticsImagesList(navigationmenu)
    }

    fun getPoliticsImagesListObserver() : MutableLiveData<List<Int>>{
        return politicsRepository.getPoliticsImagesListState()
    }

    fun getProgressState(): LiveData<Int?>? {
        return politicsRepository.getProgressState()
    }

    fun getAlertViewState(): LiveData<AlertModel?>? {
        return politicsRepository.getAlertViewState()
    }

}