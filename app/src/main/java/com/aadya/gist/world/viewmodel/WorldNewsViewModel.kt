package com.aadya.gist.world.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.registration.model.AlertModel

class WorldNewsViewModel(worldNewsRepository: WorldNewsRepository) : ViewModel() {
    private var worldNewsRepository: WorldNewsRepository
    init {
        this.worldNewsRepository = worldNewsRepository
    }


   fun getWorldNewsList(navigationmenu: NavigationMenu) {
       worldNewsRepository.getWorldNewsList(navigationmenu)
   }

    fun getWorldNewsListObserver() : MutableLiveData<List<NewsModel>>{
        return  worldNewsRepository.getWorldNewsListState()
    }

    fun getProgressState(): LiveData<Int?>? {
        return worldNewsRepository.getProgressState()
    }

    fun getAlertViewState(): LiveData<AlertModel?>? {
        return worldNewsRepository.getAlertViewState()
    }

}