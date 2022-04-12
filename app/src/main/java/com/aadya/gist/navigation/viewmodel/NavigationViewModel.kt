package com.aadya.gist.navigation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aadya.gist.login.model.LoginResponseModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.navigation.model.NavigationResponseModel
import com.aadya.gist.navigation.repository.NavigationRepository
import com.aadya.gist.registration.model.AlertModel

class NavigationViewModel(navigationRepository: NavigationRepository) :ViewModel() {
    private val navigationRepository: NavigationRepository = navigationRepository


    fun createNavigationMenu() {
        navigationRepository.createNavigationMenu()
    }

    fun getNavigationViewState(): LiveData<List<NavigationMenu>?>? {
        return navigationRepository.getNavigationViewState()
    }

    fun getProgressState(): LiveData<Int?>? {
        return navigationRepository.getProgressState()
    }

    fun getAlertViewState(): LiveData<AlertModel?>? {
        return navigationRepository.getAlertViewState()
    }
}