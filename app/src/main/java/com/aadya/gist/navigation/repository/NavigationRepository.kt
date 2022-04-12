package com.aadya.gist.navigation.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.aadya.aadyanews.R
import com.aadya.gist.login.model.LoginResponseModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.navigation.model.NavigationResponseModel
import com.aadya.gist.registration.model.AlertModel
import com.aadya.gist.retrofit.APIResponseListener
import com.aadya.gist.retrofit.RetrofitService
import com.aadya.gist.utils.CommonUtils
import com.aadya.gist.utils.Connection
import com.aadya.gist.utils.SessionManager
import retrofit2.Response
import java.util.*

class NavigationRepository( application: Application) {
   // private  var  navigationMenuState = MutableLiveData<List<NavigationMenu>>()
    var mSessionManager: SessionManager? = SessionManager()
    private var application : Application = application
    private val alertLiveData: MutableLiveData<AlertModel> = MutableLiveData<AlertModel>()
    private val progressLiveData = MutableLiveData<Int>()
    private val navigationliveData: MutableLiveData<List<NavigationMenu>?> = MutableLiveData<List<NavigationMenu>?>()
    private val mCommonUtils = CommonUtils()

    fun getNavigationViewState(): MutableLiveData<List<NavigationMenu>?>? {
        return navigationliveData
    }


    fun getProgressState(): MutableLiveData<Int>? {
        return progressLiveData
    }

    fun getAlertViewState(): MutableLiveData<AlertModel>? {
        return alertLiveData
    }


    fun createNavigationMenu() {
        mSessionManager = SessionManager.getInstance(application)

        if (Connection.getInstance().isNetworkAvailable(application)) {
            progressLiveData.value = CommonUtils.ProgressDialog.showDialog
            RetrofitService().getCategoryList(
                object : APIResponseListener {
                    override fun onSuccess(response: Response<Any>) {

                        progressLiveData.value = CommonUtils.ProgressDialog.dismissDialog

                        mCommonUtils.LogPrint("TAG",""+response.body().toString())


                        try {

                            val navigationResponseModel =
                                response.body() as NavigationResponseModel

                            if (navigationResponseModel.statusCode == 200) {
                                if (response.body() != null) {


                                    navigationliveData.value = navigationResponseModel.newsCategory

                                }

                            } else if (navigationResponseModel.statusCode == 400) {
                                setAlert(
                                    application.getString(R.string.Navigation_error),
                                    application.getString(R.string.sorry_empty_category_list),
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




     /*   navigationMenus.add(NavigationMenu(CommonUtils.NavigationMenuId.IPL2020.value, "IPL2020"))
        navigationMenus.add(NavigationMenu(CommonUtils.NavigationMenuId.Covid19.value, "Covid19"))
        navigationMenus.add(NavigationMenu(CommonUtils.NavigationMenuId.India.value, "India"))
        navigationMenus.add(NavigationMenu(CommonUtils.NavigationMenuId.States.value, "States"))
        navigationMenus.add(NavigationMenu(CommonUtils.NavigationMenuId.World.value, "World"))
        navigationMenus.add(NavigationMenu(CommonUtils.NavigationMenuId.Sports.value, "Sports"))
        navigationMenus.add(
            NavigationMenu(
                CommonUtils.NavigationMenuId.Entertainment.value,
                "Entertainment"
            )
        )
        navigationMenus.add(NavigationMenu(CommonUtils.NavigationMenuId.Business.value, "Business"))
        navigationMenus.add(NavigationMenu(CommonUtils.NavigationMenuId.Health.value, "Health"))
        navigationMenus.add(NavigationMenu(CommonUtils.NavigationMenuId.Technology.value, "Technology"))
        navigationMenus.add(NavigationMenu(CommonUtils.NavigationMenuId.Photos.value, "Photos"))
        navigationMenus.add(NavigationMenu(CommonUtils.NavigationMenuId.Videos.value, "Videos"))
        navigationMenuState.value = navigationMenus*/
    }

    fun getNavigationMenuState() : MutableLiveData<List<NavigationMenu>?> {
        return navigationliveData
    }


    private fun setAlert(title: String, message: String, isSuccess: Boolean) {
        val drawable: Int = if (isSuccess) R.drawable.correct_icon else R.drawable.wrong_icon
        val color: Int = if (isSuccess) R.color.notiSuccessColor else R.color.notiFailColor
        alertLiveData.value = AlertModel(2000, title, message, drawable, color)
    }

}