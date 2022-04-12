package com.aadya.gist.breakingnews.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.aadya.aadyanews.R
import com.aadya.gist.breakingnews.model.LatestNewsModel
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

class LifestyleNewsRepository(application: Application) {
    private var application : Application
    var  mNewsModelList = ArrayList<LatestNewsModel>()


    init {
        this.application = application
    }
    private val lifestyleNewsLiveData = MutableLiveData<List<NewsModel>>()

    fun getLifestyleNewsListState() : MutableLiveData<List<NewsModel>> {
        return  lifestyleNewsLiveData
    }

    fun getLifestyleNewsList(navigationmenu: NavigationMenu) {
        if (Connection.getInstance().isNetworkAvailable(application)) {
            progressLiveData.value = CommonUtils.ProgressDialog.showDialog
            val requestModel = RequestModel()
            requestModel.category_id = navigationmenu.category_id.toString()
            RetrofitService().getNewsList(requestModel,
                object : APIResponseListener {
                    override fun onSuccess(response: Response<Any>) {

                        progressLiveData.value = CommonUtils.ProgressDialog.dismissDialog

                        val model: ResponseModel? = response.body() as ResponseModel?
                        try {
                            if (model?.statusCode == 200) {
                                if (response.body() != null) {


                                    lifestyleNewsLiveData.value = model.newsList

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



    private val alertLiveData: MutableLiveData<AlertModel> = MutableLiveData<AlertModel>()
    private val progressLiveData = MutableLiveData<Int>()

    fun getProgressState(): MutableLiveData<Int>? {
        return progressLiveData
    }

    fun getAlertViewState(): MutableLiveData<AlertModel>? {
        return alertLiveData
    }

}
