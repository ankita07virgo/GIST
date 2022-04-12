package com.aadya.gist.breakingnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.registration.model.AlertModel

class LifestyleNewsViewModel(lifestyleNewsRepository: LifestyleNewsRepository) : ViewModel() {
    private var lifestyleNewsRepository : LifestyleNewsRepository = lifestyleNewsRepository


    fun getLifeStyleNewsList(navigationmenu: NavigationMenu) {
       lifestyleNewsRepository.getLifestyleNewsList(navigationmenu)
   }

    fun getLifeStyleNewsListObserver() : MutableLiveData<List<NewsModel>>{
        return  lifestyleNewsRepository.getLifestyleNewsListState()
    }


    fun getProgressState(): LiveData<Int?>? {
        return lifestyleNewsRepository.getProgressState()
    }

    fun getAlertViewState(): LiveData<AlertModel?>? {
        return lifestyleNewsRepository.getAlertViewState()
    }


}