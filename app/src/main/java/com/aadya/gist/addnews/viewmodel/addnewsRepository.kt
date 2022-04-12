package com.aadya.gist.addnews.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.aadya.aadyanews.R
import com.aadya.gist.addnews.model.AddNewsRequestModel
import com.aadya.gist.addnews.model.AddNewsResponseModel
import com.aadya.gist.registration.model.AlertModel
import com.aadya.gist.retrofit.APIResponseListener
import com.aadya.gist.retrofit.RetrofitService
import com.aadya.gist.utils.CommonUtils
import com.aadya.gist.utils.Connection
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import retrofit2.Response

class addnewsRepository(application: Application) {
    private var application : Application
    private lateinit var mCommonUtils : CommonUtils

    init {
        this.application = application
        mCommonUtils = CommonUtils()
    }
    private var addnewsLiveData = MutableLiveData<AddNewsResponseModel>()
    private val alertLiveData: MutableLiveData<AlertModel> = MutableLiveData<AlertModel>()
    private val progressLiveData = MutableLiveData<Int>()

    fun getProgressState(): MutableLiveData<Int>? {
        return progressLiveData
    }

    fun getAlertViewState(): MutableLiveData<AlertModel>? {
        return alertLiveData
    }


    fun getAddNewsState() : MutableLiveData<AddNewsResponseModel> {
        return  addnewsLiveData
    }

    fun addNews(addNewsRequestModel: AddNewsRequestModel) {
        if (Connection.getInstance().isNetworkAvailable(application)) {
            progressLiveData.value = CommonUtils.ProgressDialog.showDialog

mCommonUtils.LogPrint("TAG","REquestModel =>"+ addNewsRequestModel.toString())
            RetrofitService().addNews(addNewsRequestModel,
                object : APIResponseListener {
                    override fun onSuccess(response: Response<Any>) {

                        progressLiveData.value = CommonUtils.ProgressDialog.dismissDialog

                        try {
                            if(response.body() != null){
                                val model: AddNewsResponseModel? = response.body() as AddNewsResponseModel?

                                if (model?.status== 200) {
                                    addnewsLiveData.value = model

                                }
                            }
                            else  if(response.errorBody() != null){
                                val gson = Gson()
                                val adapter: TypeAdapter<AddNewsResponseModel> =
                                    gson.getAdapter(AddNewsResponseModel::class.java)


                                val addNewsResponse: AddNewsResponseModel =
                                    adapter.fromJson(
                                        response.errorBody()!!.string()
                                    )

                                if(addNewsResponse.status == 400) {
                                    setAlert(
                                        application.getString(R.string.Login_Error),
                                        addNewsResponse.message,
                                        false
                                    )
                                }

                            }
                            else{
                                Log.d("TAG","3"+response.body())
                            }

                          /*  if (model?.status == 200) {
                                addnewsLiveData.value = model

                            } else if (model?.status == 400) {
                                setAlert(
                                    application.getString(R.string.AddTag_error),
                                    application.getString(R.string.sorry_could_not_addtag),
                                    false
                                )
                            }*/


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
