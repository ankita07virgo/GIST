package com.aadya.gist.addtag.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.aadya.aadyanews.R
import com.aadya.gist.addnews.model.AddNewsResponseModel
import com.aadya.gist.addtag.model.AddTAGResponseModel
import com.aadya.gist.addtag.model.AddTagRequestModel
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.india.model.RequestModel
import com.aadya.gist.india.model.ResponseModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.registration.model.AlertModel
import com.aadya.gist.retrofit.APIResponseListener
import com.aadya.gist.retrofit.RetrofitService
import com.aadya.gist.utils.CommonUtils
import com.aadya.gist.utils.Connection
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import retrofit2.Response

class addtagRepository(application: Application) {
    private var application : Application

    init {
        this.application = application
    }
    private var addtagLiveData = MutableLiveData<AddTAGResponseModel>()
    private val alertLiveData: MutableLiveData<AlertModel> = MutableLiveData<AlertModel>()
    private val progressLiveData = MutableLiveData<Int>()

    fun getProgressState(): MutableLiveData<Int>? {
        return progressLiveData
    }

    fun getAlertViewState(): MutableLiveData<AlertModel>? {
        return alertLiveData
    }


    fun getAddTagState() : MutableLiveData<AddTAGResponseModel> {
        return  addtagLiveData
    }

    fun addTag(addTagRequestModel: AddTagRequestModel) {
        if (Connection.getInstance().isNetworkAvailable(application)) {
            progressLiveData.value = CommonUtils.ProgressDialog.showDialog

            RetrofitService().addTag(addTagRequestModel,
                object : APIResponseListener {
                    override fun onSuccess(response: Response<Any>) {

                        progressLiveData.value = CommonUtils.ProgressDialog.dismissDialog

                        try {
                            if(response.body() != null){
                                val model: AddTAGResponseModel? = response.body() as AddTAGResponseModel?

                                if (model?.status== 200) {
                                    addtagLiveData.value = model

                                }
                            }
                            else  if(response.errorBody() != null){
                                val gson = Gson()
                                val adapter: TypeAdapter<AddTAGResponseModel> =
                                    gson.getAdapter(AddTAGResponseModel::class.java)


                                val addTagResponseModel: AddTAGResponseModel =
                                    adapter.fromJson(
                                        response.errorBody()!!.string()
                                    )

                                if(addTagResponseModel.statusCode == 400) {
                                    setAlert(
                                        application.getString(R.string.Login_Error),
                                        addTagResponseModel.message,
                                        false
                                    )
                                }

                            }
                            else{
                                Log.d("TAG","3"+response.body())
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
