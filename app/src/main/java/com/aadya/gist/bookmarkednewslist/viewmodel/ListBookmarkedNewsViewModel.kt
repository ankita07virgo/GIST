package com.aadya.gist.bookmarkednewslist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.registration.model.AlertModel

class ListBookmarkedNewsViewModel(listBookmarkedNewsRepository: ListBookmarkedNewsRepository) : ViewModel() {
    private var listBookmarkedNewsRepository : ListBookmarkedNewsRepository = listBookmarkedNewsRepository


    fun getBookmarkedNewsList(){
       listBookmarkedNewsRepository.getBookmarkedNewsList()
   }

    fun getBookmarkedNewsListObserver() : MutableLiveData<List<NewsModel>>{
        return  listBookmarkedNewsRepository.getBookmarkedNewsListState()
    }

    fun getProgressState(): LiveData<Int?>? {
        return listBookmarkedNewsRepository.getProgressState()
    }

    fun getAlertViewState(): LiveData<AlertModel?>? {
        return listBookmarkedNewsRepository.getAlertViewState()
    }

}