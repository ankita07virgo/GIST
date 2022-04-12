package com.aadya.gist.utils

import android.content.Context
import android.content.SharedPreferences
import com.aadya.aadyanews.R
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.registration.model.RegistrationRequestModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class SessionManager {

    fun getAppColor(): Int? {
        return sharedPreferences?.getInt(TAG_SELECTEDCOLOR_PREF, R.color.appcolor)
    }

    fun setAppColor(color: Int?) {
        editor = sharedPreferences?.edit()
        color?.let { editor?.putInt(TAG_SELECTEDCOLOR_PREF, it) }
        editor?.apply()
    }


    fun getSelectedLanguage(): String? {
        return sharedPreferences?.getString(TAG_SELECTEDLANGUAGE_PREF, "")
    }

    fun setSelectedLanguage(authorization: String?) {
        editor = sharedPreferences?.edit()
        editor?.putString(TAG_SELECTEDLANGUAGE_PREF, authorization)
        editor?.apply()
    }

    fun setUserLoginDetailModel(userDetailModel: List<RegistrationRequestModel>) {
        val edit: SharedPreferences.Editor? = sharedPreferences?.edit()
        val gson = Gson()
        val paidObject = gson.toJson(userDetailModel)
        edit?.putString(TAG_USERDETAILMODLE_PREF, paidObject)
        edit?.commit()

    }

    fun getUserDetailLoginModel(): List<RegistrationRequestModel>? {
        val userObjectString: String? = sharedPreferences?.getString(
            TAG_USERDETAILMODLE_PREF, ""
        )

        val collectionType: Type = object : TypeToken<List<RegistrationRequestModel?>?>() {}.type
        val userobject: List<RegistrationRequestModel> ?= Gson()
            .fromJson(userObjectString, collectionType) as List<RegistrationRequestModel>?


        return userobject
    }

    fun setCategoryList(navigationMenuList: ArrayList<NavigationMenu>?) {
        val edit: SharedPreferences.Editor? = sharedPreferences?.edit()
        val gson = Gson()
        val paidObject = gson.toJson(navigationMenuList)
        edit?.putString(TAG_CATEGORYLIST_PREF, paidObject)
        edit?.commit()

    }

    fun getCategoryList(): ArrayList<NavigationMenu>? {
        val navigationObjectString: String? = sharedPreferences?.getString(
            TAG_CATEGORYLIST_PREF, ""
        )

        val collectionType: Type = object : TypeToken<List<NavigationMenu?>?>() {}.type


        return Gson()
            .fromJson(navigationObjectString, collectionType) as ArrayList<NavigationMenu>?
    }


    fun setDownloadedContentList(downloadedContentList: ArrayList<NewsModel>?) {
        val edit: SharedPreferences.Editor? = sharedPreferences?.edit()
        val gson = Gson()
        val paidObject = gson.toJson(downloadedContentList)
        edit?.putString(TAG_DOWNLOADEDCONTENTLIST_PREF, paidObject)
        edit?.commit()

    }

    fun getDownloadedContenList(): ArrayList<NewsModel>? {
        val downloadedContentObjectString: String? = sharedPreferences?.getString(
            TAG_DOWNLOADEDCONTENTLIST_PREF, ""
        )

        val collectionType: Type = object : TypeToken<List<NewsModel?>?>() {}.type


        return Gson()
            .fromJson(downloadedContentObjectString, collectionType) as ArrayList<NewsModel>?
    }

    companion object {
        const val PREF_NAME = "gist"
        const val TAG_USERDETAILMODLE_PREF = "UserLoginDetail"
        const val TAG_SELECTEDLANGUAGE_PREF = "SelectedLanguage"
        const val TAG_CATEGORYLIST_PREF = "CategoryList"
        const val TAG_SELECTEDCOLOR_PREF = "SelectedColor"
        const val TAG_DOWNLOADEDCONTENTLIST_PREF = "DownloadedContentList"
        private var sessionManager: SessionManager? = null
        private var sharedPreferences: SharedPreferences? = null
        private var context: Context? = null
        private var editor: SharedPreferences.Editor? = null
        fun getInstance(context1: Context?): SessionManager? {
            context = context1
            if (sessionManager == null) {
                sessionManager = SessionManager()
                sharedPreferences = context?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

            }
            return sessionManager
        }
    }

    fun clearpreference(tag: String?, mContext: Context) {
         var editor: SharedPreferences.Editor? = null
        var sharedPreferences: SharedPreferences? = null
        sharedPreferences = mContext.getSharedPreferences(
            PREF_NAME, Context.MODE_PRIVATE
        )
        editor = sharedPreferences.edit()
        editor.putString(tag, "")
        editor.remove(tag).apply()
    }
}