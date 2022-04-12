package com.aadya.gist.addtag.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aadya.gist.addtag.model.AddTAGResponseModel
import com.aadya.gist.addtag.model.AddTagRequestModel
import com.aadya.gist.currentweather.model.CurrentWeatherRequestModel
import com.aadya.gist.currentweather.model.CurrentweatherResponseModel
import com.aadya.gist.registration.model.AlertModel

class currentweatherViewModel(currentweatherRepository: currentweatherRepository) : ViewModel() {
    private var currentweatherRepository: currentweatherRepository = currentweatherRepository


    fun getcurrentweather(currentWeatherRequestModel : CurrentWeatherRequestModel) {
        currentweatherRepository.getCurrentWeather(currentWeatherRequestModel)
   }

    fun getcurrentweatherObserver() : MutableLiveData<CurrentweatherResponseModel> {
        return  currentweatherRepository.getCurrentWeatherState()
    }
    fun getcurrentweatherProgressState(): LiveData<Int?>? {
        return currentweatherRepository.getProgressState()
    }

    fun getcurrentweatherViewState(): LiveData<AlertModel?>? {
        return currentweatherRepository.getAlertViewState()
    }

}