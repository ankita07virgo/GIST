package com.aadya.gist.registration.ui

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aadya.aadyanews.R
import com.aadya.aadyanews.databinding.ActivityRegistrationBinding
import com.aadya.aadyanews.databinding.DetailedfragHeaderBinding
import com.aadya.gist.login.ui.LoginActivity
import com.aadya.gist.registration.model.AlertModel
import com.aadya.gist.registration.model.RegistrationResponseModel
import com.aadya.gist.registration.viewmodel.RegistrationFactory
import com.aadya.gist.registration.viewmodel.RegistrationViewModel
import com.aadya.gist.utils.CommonUtils
import com.aadya.gist.utils.SessionManager
import com.aadya.gist.utils.UserTypeSpinnerAdapter


class RegistrationActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityRegistrationBinding
    private lateinit var  registrationViewModel : RegistrationViewModel
    private lateinit var mCommonUtils : CommonUtils
    private  var checkedGenderRadioButton : String = ""
    private  var checkedUserTypeRadioButton : String =" "
    private lateinit var mIncludedLayoutBinding: DetailedfragHeaderBinding
    private lateinit var mSessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intializeMembers()
        handleObservers()
        handleclickListner()
        setAdapter()
        changeAppColorTheme()
    }

    private fun setAdapter() {
        val list =  mCommonUtils.getUserTypeList(this@RegistrationActivity,mCommonUtils,mSessionManager)
        if(list != null) {

           /* val userTypeAdapter: ArrayAdapter<String> =
                ArrayAdapter<String>(this@RegistrationActivity, R.layout.row_spinner, list)
            userTypeAdapter.setDropDownViewResource(R.layout.row_spinner_dialog)
            mBinding.usertypeSpinner.adapter = userTypeAdapter*/

            val customSpinnerAdapter = UserTypeSpinnerAdapter(this@RegistrationActivity, list)
            mBinding.usertypeSpinner.adapter = customSpinnerAdapter



        }
    }

    private fun handleclickListner() {

        mIncludedLayoutBinding.imgBack.setOnClickListener {
            this@RegistrationActivity.finish()
        }


        mBinding.usertypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, pos: Int,
                id: Long
            ) {

                if(pos == 1)
                    checkedUserTypeRadioButton = "1"
                else if(pos == 2)
                    checkedUserTypeRadioButton = "2"
                else if(pos == 3)
                    checkedUserTypeRadioButton = "3"

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }


       /* mBinding.usertypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                view.text1.setTextColor(
                    ContextCompat.getColor(
                        this@RegistrationActivity,
                        mSessionManager.getAppColor()!!
                    )
                )

                    if(pos == 1)
                        checkedUserTypeRadioButton = "1"
                    else if(pos == 2)
                        checkedUserTypeRadioButton = "2"
                    else if(pos == 3)
                        checkedUserTypeRadioButton = "3"
            }

            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
                    ContextCompat.getColor(
                        this@RegistrationActivity,
                        mSessionManager.getAppColor()!!
                    )

            }

        }*/

        mBinding.radioGroupGender.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            when (radio) {
                mBinding.radioMale -> {
                    checkedGenderRadioButton = "Male"
                }
                mBinding.radioFemale -> {
                    checkedGenderRadioButton = "Female"
                }
            }
        }



        mBinding.btnRegistration.setOnClickListener {
            mCommonUtils.hideKeyboard(this@RegistrationActivity)
            registrationViewModel.createUser(
                mBinding.edUsername.text.toString().trim { it <= ' ' },
                mBinding.edPassword.text.toString().trim { it <= ' ' },
                checkedUserTypeRadioButton,
                mBinding.edName.text.toString().trim { it <= ' ' },
                mBinding.edEmail.text.toString().trim { it <= ' ' },
                mBinding.edMobile.text.toString().trim { it <= ' ' },
                checkedGenderRadioButton

            )

        }

    }

    private fun handleObservers() {
        registrationViewModel.getRegistrationViewState()?.observe(this,
            object : Observer<RegistrationResponseModel?> {
                override fun onChanged(registrationModel: RegistrationResponseModel?) {
                    mCommonUtils.LogPrint("Registration", "" + registrationModel)
                    if (registrationModel == null) return
                    val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            })


        registrationViewModel.getAlertViewState()?.observe(this,
            object : Observer<AlertModel?> {
                override fun onChanged(alertModel: AlertModel?) {
                    if (alertModel == null) return
                    mCommonUtils.showAlert(
                        alertModel.duration,
                        alertModel.title,
                        alertModel.message,
                        alertModel.drawable,
                        alertModel.color,
                        this@RegistrationActivity,
                        mSessionManager
                    )
                }
            })


        registrationViewModel.getProgressState()?.observe(this,
            object : Observer<Int?> {
                override fun onChanged(progressState: Int?) {
                    if (progressState == null) return
                    if (progressState === CommonUtils.ProgressDialog.showDialog)
                        mCommonUtils.showProgress(
                            mCommonUtils.getLocaleContext(this@RegistrationActivity,mSessionManager).resources.getString(R.string.pleasewait),
                            this@RegistrationActivity,
                            mSessionManager.getAppColor()
                        )
                    else if (progressState === CommonUtils.ProgressDialog.dismissDialog)
                        mCommonUtils.dismissProgress()
                }
            })
    }


    private fun intializeMembers() {
        mSessionManager = SessionManager.getInstance(this@RegistrationActivity)!!
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_registration)
        registrationViewModel = ViewModelProvider(this, RegistrationFactory(application)).get(
            RegistrationViewModel::class.java
        )
         mCommonUtils = CommonUtils()
        mIncludedLayoutBinding = mBinding.includedDetailed
    }






    private fun changeAppColorTheme() {
        mCommonUtils.setHeaderColorOfApp(
            mSessionManager.getAppColor(),
            mIncludedLayoutBinding,
            this@RegistrationActivity
        )
        mCommonUtils.changeAppBarColor(mSessionManager.getAppColor()!!, this@RegistrationActivity)
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(
                ContextCompat.getColor(
                    this@RegistrationActivity,
                    mSessionManager.getAppColor()!!
                ),
                ContextCompat.getColor(
                    this@RegistrationActivity,
                    mSessionManager.getAppColor()!!
                )
            )
        )
        gradientDrawable.cornerRadius = 20f

        mBinding.btnRegistration.background = gradientDrawable

        mBinding.tvUsertype.setTextColor(
            ContextCompat.getColor(
                this@RegistrationActivity,
                mSessionManager.getAppColor()!!
            )
        )

        setHintColor(mBinding.edUsername)
        setHintColor(mBinding.edPassword)
        setHintColor(mBinding.edEmail)

        val gd = GradientDrawable()
        gd.setStroke(1, mSessionManager.getAppColor()!!)
        gd.cornerRadius = 10F
        mBinding.llMain.setBackgroundDrawable(gd)



        when (mSessionManager.getAppColor()!!){
            R.color.option1 -> mBinding.imageviewLogo.setImageResource((R.drawable.applogo_color1))
            R.color.option2 -> mBinding.imageviewLogo.setImageResource((R.drawable.applogo_color2))
            R.color.option3 -> mBinding.imageviewLogo.setImageResource((R.drawable.applogo_color3))
            R.color.option4 -> mBinding.imageviewLogo.setImageResource((R.drawable.applogo_color4))
            R.color.option5 -> mBinding.imageviewLogo.setImageResource((R.drawable.applogo_color5))
            R.color.option6 -> mBinding.imageviewLogo.setImageResource((R.drawable.applogo_color6))
            R.color.option7 -> mBinding.imageviewLogo.setImageResource((R.drawable.applogo_color7))
            R.color.option8 -> mBinding.imageviewLogo.setImageResource((R.drawable.applogo_color8))
            R.color.option9 -> mBinding.imageviewLogo.setImageResource((R.drawable.applogo_color9))
            R.color.option10 -> mBinding.imageviewLogo.setImageResource((R.drawable.applogo_color10))
            else
            -> mBinding.imageviewLogo.setImageResource((R.drawable.applogo))
        }

        mBinding.edUsername.hint = mCommonUtils.getLocaleContext(this@RegistrationActivity,mSessionManager).resources.getString(R.string.enter_username)
        mBinding.edPassword.hint = mCommonUtils.getLocaleContext(this@RegistrationActivity,mSessionManager).resources.getString(R.string.enter_password)
        mBinding.edEmail.hint = mCommonUtils.getLocaleContext(this@RegistrationActivity,mSessionManager).resources.getString(R.string.enter_email)
        mBinding.tvUsertype.hint = mCommonUtils.getLocaleContext(this@RegistrationActivity,mSessionManager).resources.getString(R.string.usertype)
        mIncludedLayoutBinding.tvMainheader.text = mCommonUtils.getLocaleContext(this@RegistrationActivity,mSessionManager).resources.getString(R.string.registration)
    }


    private fun setHintColor(mBinding: EditText) {
        mBinding.setHintTextColor(
            ContextCompat.getColor(
                this@RegistrationActivity,
                mSessionManager.getAppColor()!!
            )
        )
    }



}