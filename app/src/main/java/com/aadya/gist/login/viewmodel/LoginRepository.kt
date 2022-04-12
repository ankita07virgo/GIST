package com.aadya.gist.login.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.aadya.aadyanews.R
import com.aadya.gist.login.model.LoginRequestModel
import com.aadya.gist.login.model.LoginResponseModel
import com.aadya.gist.registration.model.AlertModel
import com.aadya.gist.retrofit.APIResponseListener
import com.aadya.gist.retrofit.RetrofitService
import com.aadya.gist.utils.CommonUtils
import com.aadya.gist.utils.Connection
import com.aadya.gist.utils.SessionManager
import retrofit2.Response

class LoginRepository(application: Application) {
    private var application : Application = application
    private var mCommonUtils : CommonUtils = CommonUtils()


    private val loginliveData: MutableLiveData<LoginResponseModel?> = MutableLiveData<LoginResponseModel?>()
    private val progressLiveData = MutableLiveData<Int>()
    private val alertLiveData: MutableLiveData<AlertModel> = MutableLiveData<AlertModel>()

    fun getLoginViewState(): MutableLiveData<LoginResponseModel?>? {
        return loginliveData
    }

    fun getProgressState(): MutableLiveData<Int>? {
        return progressLiveData
    }

    fun getAlertViewState(): MutableLiveData<AlertModel>? {
        return alertLiveData
    }

    fun checkAuthentication(mContext : Context,
                            userNameText: String?,
                            passwordText: String?
    )
    {

        if (userNameText.equals(
                "",
                ignoreCase = true
            )
        ) setAlert(
            mContext.getString(R.string.Login_Error),
            mContext.getString(R.string.please_enter_user_name),
            false
        ) else if (passwordText.equals(
                "",
                ignoreCase = true
            )
        ) setAlert(
            mContext.getString(R.string.Login_Error),
            mContext.getString(R.string.please_enter_password),
            false
        )else{
            val loginModel : LoginRequestModel = LoginRequestModel()
            loginModel.username = userNameText
            loginModel.password = passwordText


            checkAuthentication(loginModel,mContext)
        }
    }

    private fun checkAuthentication(
        loginRequestModel: LoginRequestModel , mContext : Context
    ) {
        if (Connection.getInstance().isNetworkAvailable(application)) {
            progressLiveData.value = CommonUtils.ProgressDialog.showDialog
            RetrofitService().checkAuthentication(
                loginRequestModel,

                object : APIResponseListener {
                    override fun onSuccess(response: Response<Any>) {

                        progressLiveData.value = CommonUtils.ProgressDialog.dismissDialog

                        try {


                            val loginResponseModel: LoginResponseModel? = response.body() as LoginResponseModel?
                            if (loginResponseModel?.statusCode == 200) {
                                if (response.body() != null) {
                                    loginliveData.value = loginResponseModel
                                    loginResponseModel.userData[0].status = true
                                    if (loginResponseModel != null) SessionManager.getInstance(
                                        application
                                    )
                                        ?.setUserLoginDetailModel(
                                            loginResponseModel.userData
                                        )
                                }

                            } else if (loginResponseModel?.statusCode  == 400) {
                                setAlert(
                                    mContext.getString(R.string.Login_Error),
                                    mContext.getString(R.string.sorry_invalid_user_name_email),
                                    false
                                )
                            }


                        } catch (e: Exception) {
                            e.printStackTrace()
                        }


                    }

                    override fun onFailure() {
                        progressLiveData.value = CommonUtils.ProgressDialog.dismissDialog
                    }
                })
        } else setAlert(
            mContext.getString(R.string.no_internet_connection),
            mContext.getString(R.string.not_connected_to_internet),
            false
        )
    }


    private fun setAlert(title: String, message: String, isSuccess: Boolean) {
        val drawable: Int = if (isSuccess) R.drawable.correct_icon else R.drawable.wrong_icon
        val color: Int = if (isSuccess) R.color.notiSuccessColor else R.color.notiFailColor
        alertLiveData.value = AlertModel(2000, title, message, drawable, color)
    }


}
