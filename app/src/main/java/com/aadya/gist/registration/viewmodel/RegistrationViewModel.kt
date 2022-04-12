package com.aadya.gist.registration.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aadya.gist.registration.model.AlertModel
import com.aadya.gist.registration.model.RegistrationResponseModel

class RegistrationViewModel(registrationRepository: RegistrationRepository) : ViewModel() {
    private var registrationRepository : RegistrationRepository = registrationRepository


    fun getRegistrationViewState(): LiveData<RegistrationResponseModel?>? {
        return registrationRepository.getRegistrationInViewState()
    }

    fun getProgressState(): LiveData<Int?>? {
        return registrationRepository.getProgressState()
    }

    fun getAlertViewState(): LiveData<AlertModel?>? {
        return registrationRepository.getAlertViewState()
    }

    fun createUser(
        userNameText: String?,
        passwordText: String?,
        name: String?,
        email: String?,
        gender: String?,
        mobile: String,
        usertype: String?
    ) {
        registrationRepository.userRegistration(userNameText, passwordText, name, email,gender,mobile,usertype)
    }



}