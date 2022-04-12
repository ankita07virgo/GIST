package com.aadya.gist.india.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.registration.model.AlertModel

class IndiaNewsViewModel(indiaNewsRepository: IndiaNewsRepository) : ViewModel() {
    private var indiaNewsRepository : IndiaNewsRepository
    init {
        this.indiaNewsRepository = indiaNewsRepository
    }


   fun getIndiaNewsList(navigationmenu: NavigationMenu) {
       indiaNewsRepository.getIndiaNewsList(navigationmenu)
   }

    fun getINdiaNewsListObserver() : LiveData<List<NewsModel>>{
        return  indiaNewsRepository.getIndiaNewsListState()
    }
    fun getProgressState(): LiveData<Int?>? {
        return indiaNewsRepository.getProgressState()
    }

    fun getAlertViewState(): LiveData<AlertModel?>? {
        return indiaNewsRepository.getAlertViewState()
    }

}