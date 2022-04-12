package com.aadya.gist.health.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aadya.gist.health.model.HealthNewsModel
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.registration.model.AlertModel

class HealthNewsViewModel(healthNewsRepository: HealthNewsRepository) : ViewModel() {
    private var healthNewsRepository : HealthNewsRepository
    init {
        this.healthNewsRepository = healthNewsRepository
    }


   fun getHealthNewsList(navigationmenu: NavigationMenu) {
       healthNewsRepository.getHealthNewsList(navigationmenu)
    }

    fun getHealthNewsListObserver() : MutableLiveData<List<NewsModel>> {
        return  healthNewsRepository.getHealthNewsListState()
    }

    fun getProgressState(): LiveData<Int?>? {
        return healthNewsRepository.getProgressState()
    }

    fun getAlertViewState(): LiveData<AlertModel?>? {
        return healthNewsRepository.getAlertViewState()
    }
}