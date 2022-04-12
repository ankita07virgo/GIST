package com.aadya.gist.technology.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.registration.model.AlertModel

class TechnologyNewsViewModel(technologyNewsRepository: TechnologyNewsRepository) : ViewModel() {
    private var technologyNewsRepository: TechnologyNewsRepository
    init {
        this.technologyNewsRepository = technologyNewsRepository
    }


   fun getTechnologyNewsList(navigationmenu: NavigationMenu) {
       technologyNewsRepository.getTechnologyNewsList(navigationmenu)
   }

    fun getTechnologyNewsListObserver() : MutableLiveData<List<NewsModel>>{
        return  technologyNewsRepository.getTechnologyNewsListState()
    }

    fun getProgressState(): LiveData<Int?>? {
        return technologyNewsRepository.getProgressState()
    }

    fun getAlertViewState(): LiveData<AlertModel?>? {
        return technologyNewsRepository.getAlertViewState()
    }
}