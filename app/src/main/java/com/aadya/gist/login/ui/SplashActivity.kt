package com.aadya.gist.login.ui

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aadya.aadyanews.R
import com.aadya.aadyanews.databinding.SplashMainBinding
import com.aadya.gist.login.viewmodel.SplashViewModel
import com.aadya.gist.navigation.ui.NavigationActivity
import com.aadya.gist.utils.CommonUtils
import com.aadya.gist.utils.LocaleHelper
import com.aadya.gist.utils.SessionManager
import java.util.*


class SplashActivity : AppCompatActivity() {
    private lateinit var mBinding: SplashMainBinding
    private lateinit var mSplashViewModel: SplashViewModel
    private lateinit var mSessionManager: SessionManager
    private lateinit var mCommonUtils: CommonUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intializeMembers()
        handleObservers()
        mSplashViewModel.checkSplashActivityState()


    }

    private fun handleObservers() {
        mSplashViewModel.getSplashViewState()?.observe(this, Observer {
            if (it == null)
                return@Observer
            setLanguage(mSessionManager.getSelectedLanguage()!!)
        })
    }


    private fun setLanguage(selectedLanguage: String) {
        when (selectedLanguage) {
            "hindi" -> {
                setLocale("hi")
            }
            "english" -> {
                setLocale("en")
            }
            "bengali" -> {
                setLocale("bn")
            }
            "marathi" -> {
                setLocale("mr")
            }
            "punjabi" -> {
                setLocale("pa")
            }
            "gujarati" -> {
                setLocale("gu")
            }
            else -> {
                setLocale("hi")
            }


        }
    }


    private fun intializeMembers() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.splash_main)
        mSplashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        mSessionManager = SessionManager.getInstance(application)!!
        mCommonUtils = CommonUtils()
        mCommonUtils.LogPrint("TAG","AppColor "+mSessionManager.getAppColor())
        if(mSessionManager.getAppColor() == 0)
            mSessionManager.setAppColor(R.color.appcolor)
        setColorOfApp(mSessionManager.getAppColor())

        if (mSessionManager.getSelectedLanguage() == null)
            mSessionManager.setSelectedLanguage("hindi")
        var context = LocaleHelper.setLocale(this@SplashActivity.applicationContext,
            mSessionManager.getSelectedLanguage()?.let {
                mCommonUtils.setLanguage(
                    it
                )
            })
        mBinding.tvHeader.text = context.resources.getString(R.string.app_name)


    }

    fun setLocale(localeName: String) {
        var myLocale = Locale(localeName)
        val res: Resources = resources
        val dm: DisplayMetrics = res.displayMetrics
        val conf: Configuration = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
        val refresh = Intent(this, NavigationActivity::class.java)
        refresh.putExtra(mSessionManager.getSelectedLanguage(), localeName)
        startActivity(refresh)
        this@SplashActivity.finish()


    }

    private fun setColorOfApp( selectedcolor: Int? ) {


        mCommonUtils.changeAppBarColor(selectedcolor!!,this@SplashActivity)
        mBinding.tvHeader.setTextColor(
            ContextCompat.getColor(
                applicationContext,
                selectedcolor
            )
        )
        when (selectedcolor) {
            R.color.option1 -> {

                mBinding.applogo.setImageResource((R.drawable.applogo_color1))
            }
            R.color.option2 -> {
                mBinding.applogo.setImageResource((R.drawable.applogo_color2))
            }
            R.color.option3 -> {
                mBinding.applogo.setImageResource((R.drawable.applogo_color3))
            }
            R.color.option4 -> {
                mBinding.applogo.setImageResource((R.drawable.applogo_color4))
            }
            R.color.option5 -> {
                mBinding.applogo.setImageResource((R.drawable.applogo_color5))
            }
            R.color.option6-> {
                mBinding.applogo.setImageResource((R.drawable.applogo_color6))
            }
            R.color.option7 -> {
                mBinding.applogo.setImageResource((R.drawable.applogo_color7))
            }
            R.color.option8 -> {
                mBinding.applogo.setImageResource((R.drawable.applogo_color8))
            }
            R.color.option9 -> {
                mBinding.applogo.setImageResource((R.drawable.applogo_color9))
            }
            R.color.option10 -> {
                mBinding.applogo.setImageResource((R.drawable.applogo_color10))

            }
            else -> {
                mBinding.tvHeader.setTextColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.appcolor
                    )
                )
                mBinding.applogo.setImageResource((R.drawable.applogo))
                mCommonUtils.changeAppBarColor(R.color.appcolor,this@SplashActivity)
            }
        }
    }




}