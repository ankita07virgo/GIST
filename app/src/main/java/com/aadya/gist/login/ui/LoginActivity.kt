package com.aadya.gist.login.ui

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aadya.aadyanews.R
import com.aadya.aadyanews.databinding.ActivityLoginBinding
import com.aadya.gist.login.model.LoginResponseModel
import com.aadya.gist.login.viewmodel.LoginFactory
import com.aadya.gist.login.viewmodel.LoginViewModel
import com.aadya.gist.navigation.ui.NavigationActivity
import com.aadya.gist.registration.model.AlertModel
import com.aadya.gist.registration.ui.RegistrationActivity
import com.aadya.gist.utils.CommonUtils
import com.aadya.gist.utils.SessionManager


class LoginActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityLoginBinding
    private lateinit var  loginViewModel:  LoginViewModel
    private lateinit var mCommonUtils : CommonUtils
    private lateinit var mSessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intializeMembers()
        handleClickListner()
        handleObservers()
        changeAppColorTheme()
    }

    private fun changeAppColorTheme() {
        mCommonUtils.changeAppBarColor(mSessionManager.getAppColor()!!, this@LoginActivity)
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(
                ContextCompat.getColor(
                    this@LoginActivity,
                    mSessionManager.getAppColor()!!
                ),
                ContextCompat.getColor(
                    this@LoginActivity,
                    mSessionManager.getAppColor()!!
                )
            )
        )
        gradientDrawable.cornerRadius = 20f

        mBinding.btnLogin.background = gradientDrawable

        mBinding.tvNewregistration.setTextColor(
            ContextCompat.getColor(
                this@LoginActivity,
                mSessionManager.getAppColor()!!
            )
        )

        setHintColor(mBinding.etPassword)
        setHintColor(mBinding.etUserName)

        val gd = GradientDrawable()
        gd.setStroke(1, mSessionManager.getAppColor()!!)
        gd.cornerRadius = 10F
        mBinding.etUserName.setBackgroundDrawable(gd)
        mBinding.etPassword.setBackgroundDrawable(gd)

       setDrawableTintColor(
           mBinding.etUserName,
           ResourcesCompat.getDrawable(resources, R.drawable.login_user_icon_drawable, null)
       )
        setDrawableTintColor(
            mBinding.etPassword,
            ResourcesCompat.getDrawable(resources, R.drawable.login_password_icon_drawable, null)
        )


        when (mSessionManager.getAppColor()!!){
            R.color.option1 -> mBinding.applogo.setImageResource((R.drawable.applogo_color1))
            R.color.option2 -> mBinding.applogo.setImageResource((R.drawable.applogo_color2))
            R.color.option3 -> mBinding.applogo.setImageResource((R.drawable.applogo_color3))
            R.color.option4 -> mBinding.applogo.setImageResource((R.drawable.applogo_color4))
            R.color.option5 -> mBinding.applogo.setImageResource((R.drawable.applogo_color5))
            R.color.option6 -> mBinding.applogo.setImageResource((R.drawable.applogo_color6))
            R.color.option7 -> mBinding.applogo.setImageResource((R.drawable.applogo_color7))
            R.color.option8 -> mBinding.applogo.setImageResource((R.drawable.applogo_color8))
            R.color.option9 -> mBinding.applogo.setImageResource((R.drawable.applogo_color9))
            R.color.option10 -> mBinding.applogo.setImageResource((R.drawable.applogo_color10))
            else
            -> mBinding.applogo.setImageResource((R.drawable.applogo))
        }

        mBinding.etUserName.hint = mCommonUtils.getLocaleContext(this@LoginActivity,mSessionManager).resources.getString(R.string.username)
        mBinding.etPassword.hint = mCommonUtils.getLocaleContext(this@LoginActivity,mSessionManager).resources.getString(R.string.password)
        mBinding.btnLogin.text = mCommonUtils.getLocaleContext(this@LoginActivity,mSessionManager).resources.getString(R.string.login)
        mBinding.tvNewregistration.text = mCommonUtils.getLocaleContext(this@LoginActivity,mSessionManager).resources.getString(R.string.newuser)
    }

    private fun setDrawableTintColor(et: EditText, et_drawable: Drawable?) {
        et_drawable?.colorFilter =
            PorterDuffColorFilter(
                ContextCompat.getColor(et.context, mSessionManager.getAppColor()!!),
                PorterDuff.Mode.SRC_IN
            )
    }

    private fun setHintColor(edt: EditText) {
        edt.setHintTextColor(
            ContextCompat.getColor(
                this@LoginActivity,
                mSessionManager.getAppColor()!!
            )
        )

    }

    private fun handleClickListner() {
        mBinding.btnLogin.setOnClickListener {
         loginViewModel.checkAuthentication(
             this@LoginActivity,
             mBinding.etUserName.text.toString(),
             mBinding.etPassword.text.toString()
         )
        }

        mBinding.tvNewregistration.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun intializeMembers() {
        mSessionManager = SessionManager.getInstance(this@LoginActivity)!!
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginViewModel = ViewModelProvider(this, LoginFactory(application)).get(
            LoginViewModel::class.java
        )
        mCommonUtils = CommonUtils()
    }


    private fun handleObservers() {
        loginViewModel.getLoginViewState()?.observe(this,
            object : Observer<LoginResponseModel?> {
                override fun onChanged(loginResponseModel: LoginResponseModel?) {
                    mCommonUtils.LogPrint("Login", "" + loginResponseModel.toString())
                    if (loginResponseModel == null) return
                    val intent = Intent(this@LoginActivity, NavigationActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            })


        loginViewModel.getAlertViewState()?.observe(this,
            object : Observer<AlertModel?> {
                override fun onChanged(alertModel: AlertModel?) {


                    if (alertModel == null) return
                    mCommonUtils.showAlert(
                        alertModel.duration,
                        alertModel.title,
                        alertModel.message,
                        alertModel.drawable,
                        alertModel.color,
                        this@LoginActivity,
                        mSessionManager
                    )
                }
            })


        loginViewModel.getProgressState()?.observe(this,
            object : Observer<Int?> {
                override fun onChanged(progressState: Int?) {
                    if (progressState == null) return
                    if (progressState === CommonUtils.ProgressDialog.showDialog)
                        mCommonUtils.showProgress(
                            mCommonUtils.getLocaleContext(this@LoginActivity,mSessionManager).resources.getString(R.string.pleasewait),
                            this@LoginActivity,
                            mSessionManager.getAppColor()
                        )
                    else if (progressState === CommonUtils.ProgressDialog.dismissDialog)
                        mCommonUtils.dismissProgress()
                }
            })
    }
}