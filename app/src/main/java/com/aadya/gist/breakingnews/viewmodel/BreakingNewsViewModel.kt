package com.aadya.gist.breakingnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.registration.model.AlertModel

class BreakingNewsViewModel(breakingNewsRepository: BreakingNewsRepository) : ViewModel() {
    private var breakingNewsRepository : BreakingNewsRepository = breakingNewsRepository


    fun getBreakingNewsList(navigationmenu: NavigationMenu) {
        breakingNewsRepository.getBreakingNewsList(navigationmenu)
   }

    fun getBreakingNewsListObserver() : MutableLiveData<List<NewsModel>>{
        return  breakingNewsRepository.getBreakingNewsListState()
    }

    fun getBreakingVideoList(navigationmenu: NavigationMenu) {
        breakingNewsRepository.getbreakingVideoList(navigationmenu)
    }

    fun getBreakingVideoListObserver() : MutableLiveData<List<String>>{
        return breakingNewsRepository.getBreakingVideoListState()
    }

    fun getProgressState(): LiveData<Int?>? {
        return breakingNewsRepository.getProgressState()
    }

    fun getAlertViewState(): LiveData<AlertModel?>? {
        return breakingNewsRepository.getAlertViewState()
    }


}