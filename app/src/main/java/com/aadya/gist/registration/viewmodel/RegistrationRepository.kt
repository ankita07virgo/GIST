package com.aadya.gist.registration.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.aadya.aadyanews.R
import com.aadya.gist.login.model.LoginResponseModel
import com.aadya.gist.registration.model.AlertModel
import com.aadya.gist.registration.model.RegistrationRequestModel
import com.aadya.gist.registration.model.RegistrationResponseModel
import com.aadya.gist.retrofit.APIResponseListener
import com.aadya.gist.retrofit.RetrofitService
import com.aadya.gist.utils.CommonUtils
import com.aadya.gist.utils.Connection
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import retrofit2.Response
import java.io.IOException
import java.lang.Exception

class RegistrationRepository(application: Application) {
    private var application : Application = application
    private var mCommonUtils : CommonUtils = CommonUtils()


    private val registrationliveData: MutableLiveData<RegistrationResponseModel?> = MutableLiveData<RegistrationResponseModel?>()
    private val progressLiveData = MutableLiveData<Int>()
    private val alertLiveData: MutableLiveData<AlertModel> = MutableLiveData<AlertModel>()

    fun getRegistrationInViewState(): MutableLiveData<RegistrationResponseModel?>? {
        return registrationliveData
    }

    fun getProgressState(): MutableLiveData<Int>? {
        return progressLiveData
    }

    fun getAlertViewState(): MutableLiveData<AlertModel>? {
        return alertLiveData
    }

    fun userRegistration(
        userNameText: String?,
        passwordText: String?,
        usertype: String?,
        name: String?, email: String?, mobile: String, gender: String?
    ) {
        if (userNameText.equals(
                "",
                ignoreCase = true
            )
        ) setAlert(
            application.getString(R.string.Login_Error),
            application.getString(R.string.please_enter_user_name),
            false
        ) else if (passwordText.equals(
                "",
                ignoreCase = true
            )
        ) setAlert(
            application.getString(R.string.Login_Error),
            application.getString(R.string.please_enter_password),
            false
        )else if (email?.trim().equals(
                "",
                ignoreCase = true
            )
        ) setAlert(
            application.getString(R.string.Login_Error),
            application.getString(R.string.please_enter_email),
            false
        ) else if (usertype?.trim().equals(
                "",
                ignoreCase = true))
        setAlert(
            application.getString(R.string.Login_Error),
            application.getString(R.string.please_enter_usertype),
            false
        )else{
            val registrationModel  = RegistrationRequestModel()
            registrationModel.username = userNameText
            registrationModel.password = passwordText
            registrationModel.name = name
            registrationModel.user_email = email
            registrationModel.user_mobile = mobile
            registrationModel.gender = gender
            registrationModel.user_type = usertype

            createUser(registrationModel)
        }
    }

    private fun createUser(
        registrationmodel: RegistrationRequestModel
    ) {
        if (Connection.getInstance().isNetworkAvailable(application)) {
            progressLiveData.value = CommonUtils.ProgressDialog.showDialog
            RetrofitService().registereUser(
                registrationmodel,

                object : APIResponseListener {
                    override fun onSuccess(response: Response<Any>) {
                        Log.d("TAG","2"+response)

                        progressLiveData.value = CommonUtils.ProgressDialog.dismissDialog

                        try {

                            if(response.body() != null){
                                val registerResponse: RegistrationResponseModel? = response.body() as RegistrationResponseModel?

                                if (registerResponse?.getstatusCode()== 200) {
                                    registrationliveData.value = registerResponse

                                }
                            }
                            else  if(response.errorBody() != null){
                                val gson = Gson()
                                val adapter: TypeAdapter<RegistrationResponseModel> =
                                    gson.getAdapter(RegistrationResponseModel::class.java)


                                    val registerResponse: RegistrationResponseModel =
                                        adapter.fromJson(
                                            response.errorBody()!!.string()
                                        )

                                if(registerResponse.getstatusCode() == 400) {
                                    setAlert(
                                        application.getString(R.string.Login_Error),
                                        registerResponse.getmessage(),
                                        false
                                    )
                                }

                            }
                            else{
                                Log.d("TAG","3"+response.body())
                            }


                                /*else if(response.code() == 400) {
                                    val gson = Gson()
                                    val adapter: TypeAdapter<RegistrationResponseModel> =
                                        gson.getAdapter(RegistrationResponseModel::class.java)

                                    if(response.errorBody() != null) {
                                        val registerResponse: RegistrationResponseModel =
                                            adapter.fromJson(
                                                response.errorBody()!!.string()
                                            )

                                        setAlert(
                                            application.getString(R.string.Login_Error),
                                            registerResponse.getmessage(),
                                            false
                                        )
                                    }
                                }*/


                        }
                        catch (e: Exception){
                            e.printStackTrace()
                        }



                    }

                    override fun onFailure() {
                        progressLiveData.value = CommonUtils.ProgressDialog.dismissDialog
                    }
                })
        } else setAlert(
            application.getString(R.string.no_internet_connection),
            application.getString(R.string.not_connected_to_internet),
            false
        )
    }


    private fun setAlert(title: String, message: String, isSuccess: Boolean) {
        val drawable: Int = if (isSuccess) R.drawable.correct_icon else R.drawable.wrong_icon
        val color: Int = if (isSuccess) R.color.notiSuccessColor else R.color.notiFailColor
        alertLiveData.value = AlertModel(2000, title, message, drawable, color)
    }


}
