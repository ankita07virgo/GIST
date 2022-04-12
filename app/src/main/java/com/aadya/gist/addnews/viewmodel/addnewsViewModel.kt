package com.aadya.gist.addnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aadya.gist.addnews.model.AddNewsRequestModel
import com.aadya.gist.addnews.model.AddNewsResponseModel
import com.aadya.gist.addtag.model.AddTAGResponseModel
import com.aadya.gist.addtag.model.AddTagRequestModel
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.registration.model.AlertModel

class addnewsViewModel(addnewsRepository: addnewsRepository) : ViewModel() {
    private var addnewsRepository: addnewsRepository = addnewsRepository


    fun getAddNews(addNewsRequestModel: AddNewsRequestModel) {
        addnewsRepository.addNews(addNewsRequestModel)
   }

    fun getAddNewsObserver() : MutableLiveData<AddNewsResponseModel> {
        return  addnewsRepository.getAddNewsState()
    }
    fun getAddNewsProgressState(): LiveData<Int?>? {
        return addnewsRepository.getProgressState()
    }

    fun getAddNewsAlertViewState(): LiveData<AlertModel?>? {
        return addnewsRepository.getAlertViewState()
    }

}