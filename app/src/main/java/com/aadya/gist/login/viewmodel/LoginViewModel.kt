package com.aadya.gist.login.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aadya.gist.login.model.LoginResponseModel
import com.aadya.gist.login.ui.LoginActivity
import com.aadya.gist.registration.model.AlertModel

class LoginViewModel(loginRepository: LoginRepository) : ViewModel() {
    private var loginRepository: LoginRepository = loginRepository


    fun getLoginViewState(): LiveData<LoginResponseModel?>? {
        return loginRepository.getLoginViewState()
    }

    fun getProgressState(): LiveData<Int?>? {
        return loginRepository.getProgressState()
    }

    fun getAlertViewState(): LiveData<AlertModel?>? {
        return loginRepository.getAlertViewState()
    }

    fun checkAuthentication(
        mcontext: Context,
        userNameText: String?,
        passwordText: String?,

        ) {
        loginRepository.checkAuthentication(mcontext,userNameText, passwordText)
    }



}