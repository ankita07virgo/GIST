package com.aadya.gist.health.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.aadya.aadyanews.R
import com.aadya.gist.health.model.HealthNewsModel
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.india.model.RequestModel
import com.aadya.gist.india.model.ResponseModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.registration.model.AlertModel
import com.aadya.gist.retrofit.APIResponseListener
import com.aadya.gist.retrofit.RetrofitService
import com.aadya.gist.utils.CommonUtils
import com.aadya.gist.utils.Connection
import retrofit2.Response

class HealthNewsRepository(application: Application) {
    private var application : Application

    init {
        this.application = application
    }
    private val healthNewsLiveData = MutableLiveData<List<NewsModel>>()
    private val alertLiveData: MutableLiveData<AlertModel> = MutableLiveData<AlertModel>()
    private val progressLiveData = MutableLiveData<Int>()

    fun getProgressState(): MutableLiveData<Int>? {
        return progressLiveData
    }

    fun getAlertViewState(): MutableLiveData<AlertModel>? {
        return alertLiveData
    }

    fun getHealthNewsListState() : MutableLiveData<List<NewsModel>> {
        return  healthNewsLiveData
    }

    fun getHealthNewsList(navigationmenu: NavigationMenu) {
        if (Connection.getInstance().isNetworkAvailable(application)) {
            progressLiveData.value = CommonUtils.ProgressDialog.showDialog
            val indiarequestModel = RequestModel()
            indiarequestModel.category_id = navigationmenu.category_id.toString()
            RetrofitService().getNewsList(indiarequestModel,
                object : APIResponseListener {
                    override fun onSuccess(response: Response<Any>) {

                        progressLiveData.value = CommonUtils.ProgressDialog.dismissDialog

                        val model: ResponseModel? = response.body() as ResponseModel?
                        try {
                            if (model?.statusCode == 200) {
                                if (response.body() != null) {


                                    healthNewsLiveData.value = model.newsList

                                }

                            } else if (model?.statusCode == 400) {
                                setAlert(
                                    application.getString(R.string.Navigation_error),
                                    application.getString(R.string.sorry_empty_ist),
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
