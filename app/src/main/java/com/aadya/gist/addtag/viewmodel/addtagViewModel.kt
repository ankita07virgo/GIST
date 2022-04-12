package com.aadya.gist.addtag.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aadya.gist.addtag.model.AddTAGResponseModel
import com.aadya.gist.addtag.model.AddTagRequestModel
import com.aadya.gist.registration.model.AlertModel

class addtagViewModel(addtagRepository: addtagRepository) : ViewModel() {
    private var addtagRepository: addtagRepository = addtagRepository


    fun getAddTag(addTagRequestModel: AddTagRequestModel) {
        addtagRepository.addTag(addTagRequestModel)
   }

    fun getAddTagObserver() : MutableLiveData<AddTAGResponseModel> {
        return  addtagRepository.getAddTagState()
    }
    fun getAddTagProgressState(): LiveData<Int?>? {
        return addtagRepository.getProgressState()
    }

    fun getAddTagAlertViewState(): LiveData<AlertModel?>? {
        return addtagRepository.getAlertViewState()
    }

}