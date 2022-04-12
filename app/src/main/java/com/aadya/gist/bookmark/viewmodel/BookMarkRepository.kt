package com.aadya.gist.bookmark.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.aadya.aadyanews.R
import com.aadya.gist.addtag.model.AddTagRequestModel
import com.aadya.gist.bookmark.model.BookMarkRequestModel
import com.aadya.gist.bookmark.model.BookMarkResponseModel
import com.aadya.gist.registration.model.AlertModel
import com.aadya.gist.retrofit.APIResponseListener
import com.aadya.gist.retrofit.RetrofitService
import com.aadya.gist.utils.CommonUtils
import com.aadya.gist.utils.Connection
import retrofit2.Response

class BookMarkRepository(application: Application) {
    private var application : Application

    init {
        this.application = application
    }
    private val bookmarkLiveData = MutableLiveData<List<BookMarkResponseModel>>()
    private val alertLiveData: MutableLiveData<AlertModel> = MutableLiveData<AlertModel>()
    private val progressLiveData = MutableLiveData<Int>()

    fun getProgressState(): MutableLiveData<Int>? {
        return progressLiveData
    }

    fun getAlertViewState(): MutableLiveData<AlertModel>? {
        return alertLiveData
    }


    fun bookMarkNewsState(): MutableLiveData<List<BookMarkResponseModel>> {
        return  bookmarkLiveData
    }

    fun BookMarkNews(bookMarkRequestModel: BookMarkRequestModel) {
        if (Connection.getInstance().isNetworkAvailable(application)) {
            progressLiveData.value = CommonUtils.ProgressDialog.showDialog

            RetrofitService().BookMarkNews(bookMarkRequestModel,
                object : APIResponseListener {
                    override fun onSuccess(response: Response<Any>) {

                        progressLiveData.value = CommonUtils.ProgressDialog.dismissDialog

                        val model: BookMarkResponseModel? = response.body() as BookMarkResponseModel?
                        try {
                            if (model?.statusCode == 200) {
                                if (response.body() != null) {

                                    setAlert(
                                        application.getString(R.string.Success),
                                        model?.message ,
                                        true
                                    )

                                }

                            } else if (model?.statusCode == 400) {

                                setAlert(
                                    application.getString(R.string.AddTag_error),
                                    application.getString(R.string.sorry_could_not_addtag),
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
    private fun setAlert(title: String, message: String?, isSuccess: Boolean) {
        val drawable: Int = if (isSuccess) R.drawable.correct_icon else R.drawable.wrong_icon
        val color: Int = if (isSuccess) R.color.notiSuccessColor else R.color.notiFailColor
        alertLiveData.value = AlertModel(2000, title, message, drawable, color)
    }

}
