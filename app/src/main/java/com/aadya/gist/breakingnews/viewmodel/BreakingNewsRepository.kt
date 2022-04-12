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

class BreakingNewsRepository(application: Application) {
    private var application : Application


    init {
        this.application = application
    }
    private val breakingNewsLiveData = MutableLiveData<List<NewsModel>>()

    fun getBreakingNewsListState() : MutableLiveData<List<NewsModel>> {
        return  breakingNewsLiveData
    }

    fun getBreakingNewsList(navigationmenu: NavigationMenu) {
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


                                    breakingNewsLiveData.value = model.newsList

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

    private var  breakingVideoLiveData = MutableLiveData<List<String>>()
    fun getBreakingVideoListState() : MutableLiveData<List<String>>{
        return  breakingVideoLiveData
    }

    fun getbreakingVideoList(navigationmenu: NavigationMenu) {
        var  mVideoPathList = ArrayList<String>()
       /* mVideoPathList.add("android.resource://" + application.packageName + "/" + R.raw.sample1)
        mVideoPathList.add("android.resource://" + application.packageName + "/" + R.raw.sample1)*/
        mVideoPathList.add("rtsp://r2---sn-a5m7zu76.c.youtube.com/Ck0LENy73wIaRAnTmlo5oUgpQhMYESARFEgGUg5yZWNvbW1lbmRhdGlvbnIhAWL2kyn64K6aQtkZVJdTxRoO88HsQjpE1a8d1GxQnGDmDA==/0/0/0/video.3gp")
        breakingVideoLiveData.value = mVideoPathList
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
