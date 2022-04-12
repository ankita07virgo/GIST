package com.aadya.gist.sports.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.registration.model.AlertModel
import com.aadya.gist.sports.model.SportsNewsModel

class SportsNewsViewModel(sportsNewsRepository: SportNewsRepository) : ViewModel() {
    private var sportsNewsRepository : SportNewsRepository
    init {
        this.sportsNewsRepository = sportsNewsRepository
    }


   fun getSportsNewsList(navigationMenu: NavigationMenu) {
       sportsNewsRepository.getSportsNewsList(navigationMenu)
   }

    fun getSportseNewsListObserver() : MutableLiveData<List<NewsModel>> {
        return  sportsNewsRepository.getSportsNewsListState()
    }

    fun getProgressState(): LiveData<Int?>? {
        return sportsNewsRepository.getProgressState()
    }

    fun getAlertViewState(): LiveData<AlertModel?>? {
        return sportsNewsRepository.getAlertViewState()
    }
}