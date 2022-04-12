package com.aadya.gist.entertainment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aadya.gist.entertainment.model.EntertainmentNewsModel
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.registration.model.AlertModel

class EntertainmentNewsViewModel(entertainmentNewsRepository: EntertainmentNewsRepository) : ViewModel() {
    private var entertainmentNewsRepository : EntertainmentNewsRepository
    init {
        this.entertainmentNewsRepository = entertainmentNewsRepository
    }


   fun getEntertainmentNewsList(navigationmenu: NavigationMenu) {
       entertainmentNewsRepository.getEntertainmentNewsList(navigationmenu)
   }

    fun getEntertainmentNewsListObserver() : MutableLiveData<List<NewsModel>>{
        return  entertainmentNewsRepository.getEntertainmentNewsListState()
    }

    fun getProgressState(): LiveData<Int?>? {
        return entertainmentNewsRepository.getProgressState()
    }

    fun getAlertViewState(): LiveData<AlertModel?>? {
        return entertainmentNewsRepository.getAlertViewState()
    }
}