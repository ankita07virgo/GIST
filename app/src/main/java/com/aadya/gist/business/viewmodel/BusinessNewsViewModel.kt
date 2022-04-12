package com.aadya.gist.business.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aadya.gist.business.model.BusinessNewsModel
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.registration.model.AlertModel

class BusinessNewsViewModel(businessNewsRepository : BusinessNewsRepository) : ViewModel() {
    private var businessNewsRepository : BusinessNewsRepository
    init {
        this.businessNewsRepository = businessNewsRepository
    }


   fun getBusinessNewsList(navigationmenu: NavigationMenu) {
       businessNewsRepository.getBusinessNewsList(navigationmenu)
   }

    fun getBusinessNewsListObserver() : MutableLiveData<List<NewsModel>>{
        return  businessNewsRepository.getBusinessNewsListState()
    }

    fun getProgressState(): LiveData<Int?>? {
        return businessNewsRepository.getProgressState()
    }

    fun getAlertViewState(): LiveData<AlertModel?>? {
        return businessNewsRepository.getAlertViewState()
    }
}