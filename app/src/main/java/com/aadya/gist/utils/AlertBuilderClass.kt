package com.aadya.gist.utils

import android.R.attr.button
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.aadya.aadyanews.R
import com.aadya.gist.addnews.model.AddNewsRequestModel
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_play_audio.*
import kotlinx.android.synthetic.main.activity_play_audio.view.*
import kotlinx.android.synthetic.main.activity_select_language.view.*
import kotlinx.android.synthetic.main.addnewsalert.view.*
import kotlinx.android.synthetic.main.addtagalert.view.*
import kotlinx.android.synthetic.main.addtagalert.view.tvheader
import kotlinx.android.synthetic.main.logoutalert.view.*
import kotlinx.android.synthetic.main.logoutalert.view.cancelBtn
import kotlinx.android.synthetic.main.logoutalert.view.okBtn
import kotlinx.android.synthetic.main.searchcategoryalert.*
import kotlinx.android.synthetic.main.searchcategoryalert.view.*
import java.util.*


class AlertBuilderClass {

    private val mCommonUtils: CommonUtils = CommonUtils()
    fun selectLanguageDialog(
        context: Context,
        alertDialogListener: AlertDialogListener.LanguageDialogButtonListener,
        mSessionManager: SessionManager
    ) {
        val inflater = LayoutInflater.from(context)
        val dialogLayout: View = inflater.inflate(R.layout.activity_select_language, null)
        val alertDialog = Dialog(context)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setCancelable(true)
        alertDialog.setContentView(dialogLayout)
        alertDialog.window?.setBackgroundDrawable(
            ResourcesCompat.getDrawable(
                context.resources,
                R.drawable.roundbg,
                null
            )
        )

dialogLayout.tv_title.text = mCommonUtils.getLocaleContext(context, mSessionManager)?.resources?.getString(
    R.string.please_select_lang
)
        dialogLayout.ll_english.setOnClickListener {
            alertDialogListener.onItemClick("english")
            alertDialog.dismiss()
        }
        dialogLayout.ll_hindi.setOnClickListener {
            alertDialogListener.onItemClick("hindi")
            alertDialog.dismiss()
        }
        dialogLayout.ll_hindi.background =
            ResourcesCompat.getDrawable(context.resources, R.drawable.roundbg, null)
        dialogLayout.tv_hindi.setTextColor(ContextCompat.getColor(context, R.color.appcolor))

        dialogLayout.ll_bengali.setOnClickListener {
            alertDialogListener.onItemClick("bengali")
            alertDialog.dismiss()
        }

        dialogLayout.ll_marathi.setOnClickListener {
            alertDialogListener.onItemClick("marathi")
            alertDialog.dismiss()
        }

        dialogLayout.ll_punjabi.setOnClickListener {
            alertDialogListener.onItemClick("punjabi")
            alertDialog.dismiss()
        }
        dialogLayout.ll_gujarati.setOnClickListener {
            alertDialogListener.onItemClick("gujarati")
            alertDialog.dismiss()
        }

        mCommonUtils.LogPrint("TAG", "Selected Lnaguage=>" + mSessionManager.getSelectedLanguage())
        when (mSessionManager.getSelectedLanguage()) {
            "english" -> setSelectedLanguageTab(
                dialogLayout,
                dialogLayout.img_eng,
                dialogLayout.ll_english,
                dialogLayout.tv_english,
                context, mSessionManager
            )
            "hindi" -> setSelectedLanguageTab(

                dialogLayout,
                dialogLayout.img_hindi,
                dialogLayout.ll_hindi,
                dialogLayout.tv_hindi,
                context,
                mSessionManager
            )
            "bengali" -> setSelectedLanguageTab(

                dialogLayout,
                dialogLayout.img_bengali,
                dialogLayout.ll_bengali,
                dialogLayout.tv_bengali,
                context,
                mSessionManager
            )
            "marathi" -> setSelectedLanguageTab(

                dialogLayout,
                dialogLayout.img_marathi,
                dialogLayout.ll_marathi,
                dialogLayout.tv_marathi,
                context,
                mSessionManager
            )
            "punjabi" -> setSelectedLanguageTab(

                dialogLayout,
                dialogLayout.img_punjabi,
                dialogLayout.ll_punjabi,
                dialogLayout.tv_punjabi,
                context,
                mSessionManager
            )
            "gujarati" -> setSelectedLanguageTab(

                dialogLayout,
                dialogLayout.img_gujarati,
                dialogLayout.ll_gujarati,
                dialogLayout.tv_gujarti,
                context,
                mSessionManager
            )

            else -> {
                setSelectedLanguageTab(

                    dialogLayout,
                    dialogLayout.img_hindi,
                    dialogLayout.ll_hindi,
                    dialogLayout.tv_hindi,
                    context,
                    mSessionManager
                )
            }
        }

        dialogLayout.tv_title.setTextColor(
            ContextCompat.getColor(
                context,
                mSessionManager.getAppColor()!!
            )
        )
        alertDialog.show()
    }

    private fun setSelectedLanguageTab(

        dialogLayout: View, imgview: ImageView,
        ll: LinearLayout,
        tv: TextView,
        context: Context,
        mSessionManager: SessionManager
    ) {

        tv.setTextColor(ContextCompat.getColor(context, R.color.white))
        changeUnselectedRowsColor(dialogLayout, mSessionManager, context)
        changeBackgroundColor(mSessionManager, ll, context, imgview)


    }

    private fun changeBackgroundColor(
        mSessionManager: SessionManager,
        ll: View,
        context: Context,
        imgview: ImageView?
    ) {

        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(
                ContextCompat.getColor(
                    context,
                    mSessionManager.getAppColor()!!
                ),
                ContextCompat.getColor(
                    context,
                    mSessionManager.getAppColor()!!
                )
            )
        )
        gradientDrawable.cornerRadius = 20f

        ll.background = gradientDrawable
        when (mSessionManager.getAppColor()) {
            R.color.option1 -> {
                imgview?.setImageResource((R.drawable.applogo_color1))

            }

            R.color.option2 -> {
                imgview?.setImageResource((R.drawable.applogo_color2))
            }
            R.color.option3 -> {
                imgview?.setImageResource((R.drawable.applogo_color3))
            }
            R.color.option4 -> {
                imgview?.setImageResource((R.drawable.applogo_color4))
            }

            R.color.option5 -> {
                imgview?.setImageResource((R.drawable.applogo_color5))
            }
            R.color.option6 -> {
                imgview?.setImageResource((R.drawable.applogo_color6))
            }

            R.color.option7 -> {
                imgview?.setImageResource((R.drawable.applogo_color7))
            }

            R.color.option8 -> {
                imgview?.setImageResource((R.drawable.applogo_color8))
            }

            R.color.option9 -> {
                imgview?.setImageResource((R.drawable.applogo_color9))
            }

            R.color.option10 -> {
                imgview?.setImageResource((R.drawable.applogo_color10))
            }
            else -> {
                imgview?.setImageResource((R.drawable.applogo))
            }
        }
    }

    private fun changeUnselectedRowsColor(
        dialogLayout: View,
        mSessionManager: SessionManager,
        context: Context
    ) {
            when(mSessionManager.getSelectedLanguage()){
                "english" -> {
                    changeIMageView_TextColor(
                        dialogLayout.img_hindi,
                        dialogLayout.tv_hindi,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_bengali,
                        dialogLayout.tv_bengali,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_marathi,
                        dialogLayout.tv_marathi,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_punjabi,
                        dialogLayout.tv_punjabi,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_gujarati,
                        dialogLayout.tv_gujarti,
                        context,
                        mSessionManager
                    )

                }
                "hindi" -> {
                    changeIMageView_TextColor(
                        dialogLayout.img_eng,
                        dialogLayout.tv_english,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_bengali,
                        dialogLayout.tv_bengali,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_marathi,
                        dialogLayout.tv_marathi,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_punjabi,
                        dialogLayout.tv_punjabi,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_gujarati,
                        dialogLayout.tv_gujarti,
                        context,
                        mSessionManager
                    )

                }
                "marathi" -> {
                    changeIMageView_TextColor(
                        dialogLayout.img_eng,
                        dialogLayout.tv_english,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_bengali,
                        dialogLayout.tv_bengali,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_hindi,
                        dialogLayout.tv_hindi,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_punjabi,
                        dialogLayout.tv_punjabi,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_gujarati,
                        dialogLayout.tv_gujarti,
                        context,
                        mSessionManager
                    )

                }

                "bengali" -> {
                    changeIMageView_TextColor(
                        dialogLayout.img_eng,
                        dialogLayout.tv_english,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_marathi,
                        dialogLayout.tv_marathi,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_hindi,
                        dialogLayout.tv_hindi,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_punjabi,
                        dialogLayout.tv_punjabi,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_gujarati,
                        dialogLayout.tv_gujarti,
                        context,
                        mSessionManager
                    )

                }

                "punjabi" -> {
                    changeIMageView_TextColor(
                        dialogLayout.img_eng,
                        dialogLayout.tv_english,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_marathi,
                        dialogLayout.tv_marathi,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_hindi,
                        dialogLayout.tv_hindi,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_bengali,
                        dialogLayout.tv_bengali,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_gujarati,
                        dialogLayout.tv_gujarti,
                        context,
                        mSessionManager
                    )

                }

                "gujarati" -> {
                    changeIMageView_TextColor(
                        dialogLayout.img_eng,
                        dialogLayout.tv_english,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_marathi,
                        dialogLayout.tv_marathi,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_hindi,
                        dialogLayout.tv_hindi,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_bengali,
                        dialogLayout.tv_bengali,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_punjabi,
                        dialogLayout.tv_punjabi,
                        context,
                        mSessionManager
                    )

                }
                else ->{
                    changeIMageView_TextColor(
                        dialogLayout.img_eng,
                        dialogLayout.tv_english,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_marathi,
                        dialogLayout.tv_marathi,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_gujarati,
                        dialogLayout.tv_gujarti,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_bengali,
                        dialogLayout.tv_bengali,
                        context,
                        mSessionManager
                    )
                    changeIMageView_TextColor(
                        dialogLayout.img_punjabi,
                        dialogLayout.tv_punjabi,
                        context,
                        mSessionManager
                    )

                }
            }
    }

    private fun changeIMageView_TextColor(
        imgView: ImageView,
        txtView: TextView,
        context: Context,
        mSessionManager: SessionManager
    ) {

        txtView.setTextColor(
            ContextCompat.getColor(
                context,
                mSessionManager.getAppColor()!!
            )
        )

        when (mSessionManager.getAppColor()!!){
            R.color.option1 -> imgView.setImageResource((R.drawable.applogo_color1))
            R.color.option2 -> imgView.setImageResource((R.drawable.applogo_color2))
            R.color.option3 -> imgView.setImageResource((R.drawable.applogo_color3))
            R.color.option4 -> imgView.setImageResource((R.drawable.applogo_color4))
            R.color.option5 -> imgView.setImageResource((R.drawable.applogo_color5))
            R.color.option6 -> imgView.setImageResource((R.drawable.applogo_color6))
            R.color.option7 -> imgView.setImageResource((R.drawable.applogo_color7))
            R.color.option8 -> imgView.setImageResource((R.drawable.applogo_color8))
            R.color.option9 -> imgView.setImageResource((R.drawable.applogo_color9))
            R.color.option10 -> imgView.setImageResource((R.drawable.applogo_color10))
            else
                -> imgView.setImageResource((R.drawable.applogo))
        }
    }


    fun showLogOutAlert(
        mContext: Context,
        alertDialogListener: AlertDialogListener.LanguageDialogButtonListener,
        mSessionManager: SessionManager, mCommonUtils: CommonUtils
    ) {
        val inflater = LayoutInflater.from(mContext)
        val dialogLayout: View = inflater.inflate(R.layout.logoutalert, null)
        val alertDialog = Dialog(mContext)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setCancelable(true)
        alertDialog.setContentView(dialogLayout)
        alertDialog.window?.setBackgroundDrawable(
            ResourcesCompat.getDrawable(
                mContext.resources,
                R.drawable.roundbg,
                null
            )
        )
        dialogLayout.logoutheader.text = mCommonUtils.getLocaleContext(mContext, mSessionManager).resources.getString(
            R.string.logouttag
        )
        dialogLayout.messageTtile.text =mCommonUtils.getLocaleContext(mContext, mSessionManager).resources.getString(
            R.string.logout
        )
        dialogLayout.okBtn.text = mCommonUtils.getLocaleContext(mContext, mSessionManager).resources.getString(
            android.R.string.ok
        )
        dialogLayout.cancelBtn.text = mCommonUtils.getLocaleContext(mContext, mSessionManager).resources.getString(
            android.R.string.cancel
        )

        setDialogueColorTheme(
            mSessionManager,
            dialogLayout.messageTtile,
            dialogLayout.logoutheader,
            dialogLayout.okBtn,
            dialogLayout.cancelBtn,
            mContext
        )

        dialogLayout.cancelBtn.setOnClickListener {
            alertDialog.dismiss()
        }
        dialogLayout.okBtn.setOnClickListener {
            mSessionManager.clearpreference(SessionManager.TAG_SELECTEDLANGUAGE_PREF, mContext)
            mSessionManager.clearpreference(SessionManager.TAG_USERDETAILMODLE_PREF, mContext)
            mSessionManager.clearpreference(SessionManager.TAG_CATEGORYLIST_PREF, mContext)
            alertDialog.dismiss()
            alertDialogListener.onItemClick("ok")
        }

        alertDialog.show()
    }

    private fun setDialogueColorTheme(
        mSessionManager: SessionManager,
        tv_message: TextView?, tv_title: TextView, okButton: Button, cancelButton: Button,
        mContext: Context
    ) {

        tv_message?.setTextColor(
            ContextCompat.getColor(
                mContext,
                mSessionManager.getAppColor()!!
            )
        )


        tv_title.setBackgroundColor(
            ContextCompat.getColor(
                mContext,
                mSessionManager.getAppColor()!!
            )
        )
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(
                ContextCompat.getColor(
                    mContext,
                    mSessionManager.getAppColor()!!
                ),
                ContextCompat.getColor(
                    mContext,
                    mSessionManager.getAppColor()!!
                )
            )
        )
        gradientDrawable.cornerRadius = 20f

        okButton.background = gradientDrawable
        cancelButton.setTextColor(
            ContextCompat.getColor(
                mContext,
                mSessionManager.getAppColor()!!
            )
        )
    }

    fun showAddTAGAlert(
        mSessionManager: SessionManager,
        mContext: Context,

        alertDialogListener: AlertDialogListener.LanguageDialogButtonListener,
        mCommonUtils: CommonUtils

    ) {
        val inflater = LayoutInflater.from(mContext)
        val dialogLayout: View = inflater.inflate(R.layout.addtagalert, null)
        val alertDialog = Dialog(mContext)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setCancelable(true)
        alertDialog.setContentView(dialogLayout)
        alertDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialogLayout.tvheader.text = mCommonUtils.getLocaleContext(mContext, mSessionManager).resources.getText(
            R.string.add_tag
        )
        dialogLayout.dialog_text_input_layout.hint = mCommonUtils.getLocaleContext(
            mContext,
            mSessionManager
        ).resources.getText(R.string.addtag_hint)
        dialogLayout.okBtn.text = mCommonUtils.getLocaleContext(mContext, mSessionManager).resources.getText(
            android.R.string.ok
        )
        dialogLayout.cancelBtn.text = mCommonUtils.getLocaleContext(mContext, mSessionManager).resources.getText(
            android.R.string.cancel
        )


        alertDialog.window?.setBackgroundDrawable(
            ResourcesCompat.getDrawable(
                mContext.resources,
                R.drawable.drawable_inset,
                null
            )
        )

        dialogLayout.cancelBtn.setOnClickListener {
            alertDialog.dismiss()
        }
        dialogLayout.okBtn.setOnClickListener {
           // alertDialog.dismiss()
            if ((dialogLayout.dialog_edit_text.text.toString().isEmpty())) {
                dialogLayout.dialog_text_input_layout.isErrorEnabled = true
                dialogLayout.dialog_text_input_layout.error = mCommonUtils.getLocaleContext(
                    mContext,
                    mSessionManager
                ).resources.getString(R.string.EMPTYTAG)
            } else {
                alertDialog.dismiss()
                alertDialogListener.onItemClick(dialogLayout.dialog_edit_text.text.toString())
            }
        }
        setDialogueColorTheme(
            mSessionManager,
            null,
            dialogLayout.tvheader,
            dialogLayout.okBtn,
            dialogLayout.cancelBtn,
            mContext
        )
        setTextInputCOlorTheme(dialogLayout.dialog_text_input_layout, mContext, mSessionManager)
        alertDialog.show()
    }

    private fun setTextInputCOlorTheme(
        dialogLayout: TextInputLayout?,
        context: Context,
        mSessionManager: SessionManager
    ) {
        dialogLayout?.defaultHintTextColor = ColorStateList.valueOf(
            ContextCompat.getColor(
                context,
                mSessionManager.getAppColor()!!
            )
        )
        dialogLayout?.boxStrokeColor =  ContextCompat.getColor(
            context,
            mSessionManager.getAppColor()!!
        )

        dialogLayout?.boxStrokeErrorColor =ColorStateList.valueOf(
            ContextCompat.getColor(
                context,
                mSessionManager.getAppColor()!!
            )
        )

        dialogLayout?.setErrorTextColor(
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    mSessionManager.getAppColor()!!
                )
            )
        )

        dialogLayout?.setErrorIconTintList(
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    mSessionManager.getAppColor()!!
                )
            )
        )
        dialogLayout?.setHintTextAppearance(
            ContextCompat.getColor(
                context,
                mSessionManager.getAppColor()!!
            )
        )
    }

    fun showSearchCategoryAlert(
        mContext: Context,
        alertDialogListener: AlertDialogListener.SearchButtonListener,
        mSessionManager: SessionManager, mCommonUtils: CommonUtils
    ) {
        val inflater = LayoutInflater.from(mContext)
        val dialogLayout: View = inflater.inflate(R.layout.searchcategoryalert, null)
        val alertDialog = Dialog(mContext)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setCancelable(true)
        alertDialog.setContentView(dialogLayout)
        alertDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val mList : ArrayList<NavigationMenu>? =  mSessionManager.getCategoryList()
        val addNavigationMenu : NavigationMenu = NavigationMenu()
        addNavigationMenu.category_id = "0"
        addNavigationMenu.category_name =   mCommonUtils.getLocaleContext(mContext, mSessionManager).resources.getString(
            R.string.please_select
        )
        mList?.add(0, addNavigationMenu)

        val customSpinnerAdapter = SpinnerAdapter(mContext, mList!!)
        alertDialog.search_spinner.adapter = customSpinnerAdapter
        var navigationMenu: NavigationMenu = NavigationMenu()

        alertDialog.window?.setBackgroundDrawable(
            ResourcesCompat.getDrawable(
                mContext.resources,
                R.drawable.drawable_inset,
                null
            )
        )

        dialogLayout.tv_header.text = mCommonUtils.getLocaleContext(mContext, mSessionManager).resources.getText(
            R.string.search_Category
        )
        dialogLayout.submitBtn.text = mCommonUtils.getLocaleContext(mContext, mSessionManager).resources.getText(
            R.string.submit
        )
        dialogLayout.cancelBtn.text = mCommonUtils.getLocaleContext(mContext, mSessionManager).resources.getText(
            android.R.string.cancel
        )

        setDialogueColorTheme(
            mSessionManager,
            null,
            dialogLayout.tv_header,
            dialogLayout.submitBtn,
            dialogLayout.cancelBtn,
            mContext
        )
       alertDialog.search_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
           override fun onItemSelected(
               parent: AdapterView<*>?, view: View?, position: Int,
               id: Long
           ) {
               navigationMenu = parent?.getItemAtPosition(position) as NavigationMenu

           }
           override fun onNothingSelected(p0: AdapterView<*>?) {}
       }

        dialogLayout.cancelBtn.setOnClickListener {
            alertDialog.dismiss()
        }
        dialogLayout.submitBtn.setOnClickListener {

            if (navigationMenu.category_id=="0") {
                val drawable: Int = R.drawable.wrong_icon
                val color: Int = R.color.notiFailColor

                mCommonUtils.showAlert(
                    2000,
                    mCommonUtils.getLocaleContext(
                        mContext,
                        mSessionManager
                    ).resources.getString(R.string.Navigation_error),
                    mCommonUtils.getLocaleContext(
                        mContext,
                        mSessionManager
                    ).resources.getString(R.string.please_select),
                    drawable,
                    color,
                    mContext as Activity,
                    mSessionManager
                )
            } else {
                alertDialog.dismiss()
                alertDialogListener.onSpinnerItemClick(navigationMenu)
            }
        }

        alertDialog.show()
    }

    fun showAddNewsAlert(

        context: Context,
        addNewsDialogListener: AlertDialogListener.AddNewsButtonListener,
        mSessionManager: SessionManager, mCommonUtils: CommonUtils
    ) {
        val calendar= Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val inflater = LayoutInflater.from(context)
        val dialogLayout: View = inflater.inflate(R.layout.addnewsalert, null)
        val alertDialog = Dialog(context)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setCancelable(true)
        alertDialog.setContentView(dialogLayout)
        alertDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialogLayout.tvheader.text = mCommonUtils.getLocaleContext(context, mSessionManager).resources.getString(
            R.string.add_news
        )
        val mList : ArrayList<NavigationMenu>? =  mSessionManager.getCategoryList()
        val addNavigationMenu : NavigationMenu = NavigationMenu()
        addNavigationMenu.category_id = "0"
        addNavigationMenu.category_name =   mCommonUtils.getLocaleContext(context, mSessionManager).resources.getString(
            R.string.please_select
        )
        mList?.add(0, addNavigationMenu)

        setDialogueColorTheme(
            mSessionManager,
            null,
            dialogLayout.tvheader,
            dialogLayout.okBtn,
            dialogLayout.cancelBtn,
            context
        )
        dialogLayout.schedule_ed?.setTextColor(
            ContextCompat.getColor(
                context,
                mSessionManager.getAppColor()!!
            )
        )
        dialogLayout.schedule_ed?.setHintTextColor(
            ContextCompat.getColor(
                context,
                mSessionManager.getAppColor()!!
            )
        )

        setTextInputCOlorTheme(dialogLayout.dialog_title_layout, context, mSessionManager)
        setTextInputCOlorTheme(dialogLayout.dialog_shortdes_layout, context, mSessionManager)
        setTextInputCOlorTheme(dialogLayout.dialog_des_input_layout, context, mSessionManager)
        setTextInputCOlorTheme(dialogLayout.dialog_des_url_layout, context, mSessionManager)
        setTextInputCOlorTheme(dialogLayout.dialog_des_url_layout, context, mSessionManager)
        val customSpinnerAdapter = SpinnerAdapter(context, mList!!)
        alertDialog.search_spinner.adapter = customSpinnerAdapter

        dialogLayout.dialog_title_layout.hint = mCommonUtils.getLocaleContext(
            context,
            mSessionManager
        ).resources.getString(R.string.addnews_title)
        dialogLayout.dialog_des_input_layout.hint = mCommonUtils.getLocaleContext(
            context,
            mSessionManager
        ).resources.getString(R.string.addnews_description)
        dialogLayout.dialog_des_url_layout.hint = mCommonUtils.getLocaleContext(
            context,
            mSessionManager
        ).resources.getString(R.string.addnews_url)
        dialogLayout.dialog_shortdes_layout.hint = mCommonUtils.getLocaleContext(
            context,
            mSessionManager
        ).resources.getString(R.string.addnews_shortdes)
        dialogLayout.okBtn.text = mCommonUtils.getLocaleContext(context, mSessionManager).resources.getString(
            android.R.string.ok
        )
        dialogLayout.cancelBtn.text = mCommonUtils.getLocaleContext(context, mSessionManager).resources.getString(
            android.R.string.cancel
        )
        dialogLayout.schedule_ed.hint = mCommonUtils.getLocaleContext(context, mSessionManager).resources.getString(
            R.string.schedule_news
        )
        var navigationMenu: NavigationMenu = NavigationMenu()

        alertDialog.search_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int,
                id: Long
            ) {
                navigationMenu = parent?.getItemAtPosition(position) as NavigationMenu

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        dialogLayout.imageview_calendar.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                context, DatePickerDialog.OnDateSetListener
                {

                        view, year, monthOfYear, dayOfMonth ->
                    dialogLayout.schedule_ed.setText(
                        "" + String.format("%02d", dayOfMonth) + "-" + String.format(
                            "%02d",
                            monthOfYear + 1
                        ) + "-" + String.format("%04d", year)
                    )
                }, year, month, day
            )
            datePickerDialog.show()
        }
        alertDialog.window?.setBackgroundDrawable(
            ResourcesCompat.getDrawable(
                context.resources,
                R.drawable.drawable_inset,
                null
            )
        )

        dialogLayout.cancelBtn.setOnClickListener {
            alertDialog.dismiss()
        }
        dialogLayout.okBtn.setOnClickListener {
            // alertDialog.dismiss()
            if (navigationMenu.category_id=="0") {
                val drawable: Int = R.drawable.wrong_icon
                val color: Int = R.color.notiFailColor

                mCommonUtils.showAlert(
                    2000,
                    mCommonUtils.getLocaleContext(
                        context,
                        mSessionManager
                    ).resources.getString(R.string.Navigation_error),
                    mCommonUtils.getLocaleContext(
                        context,
                        mSessionManager
                    ).resources.getString(R.string.please_select),
                    drawable,
                    color,
                    context as Activity, mSessionManager
                )
            }
           else if ((dialogLayout.dialog_title_text.text.toString().isEmpty())) {
                dialogLayout.dialog_title_layout.isErrorEnabled = true
                dialogLayout.dialog_title_layout.error = mCommonUtils.getLocaleContext(
                    context,
                    mSessionManager
                ).resources.getString(R.string.EMPTYTAG)
            }
            else {
                alertDialog.dismiss()
               var mAddNewsRequestModel : AddNewsRequestModel = AddNewsRequestModel()
                mAddNewsRequestModel.category_id = navigationMenu.category_id
                mAddNewsRequestModel.news_title = dialogLayout.dialog_title_text.text.toString()
                mAddNewsRequestModel.description = dialogLayout.dialog_des_text.text.toString()
                mAddNewsRequestModel.video_url = dialogLayout.dialog_url_text.text.toString()
                mAddNewsRequestModel.language_id = getLanguageId(mSessionManager.getSelectedLanguage())
                mAddNewsRequestModel.short_description = dialogLayout.dialog_shortdes_text.text.toString()
                mCommonUtils.LogPrint("TAG", "Text: " + (dialogLayout.schedule_ed.text.toString()))
                if(dialogLayout.schedule_ed.text.toString().isNotBlank())
                    mAddNewsRequestModel.schedule_date = dialogLayout.schedule_ed.text.toString()
                else {

                    mAddNewsRequestModel.schedule_date = mCommonUtils.getCurrentDate_dd_MM_yyyy()
                }
                addNewsDialogListener.onSubmit(mAddNewsRequestModel)
            }
        }

        alertDialog.show()
    }

    private fun getLanguageId(selectedLanguage: String?): String? {
        when(selectedLanguage){
            "hindi" -> return "1"
            "english" -> return "2"
            "bengali" -> return "3"
            "marathi" -> return "4"
            "punjabi" -> return "5"
            "gujarati" -> return "6"
            else -> return "1"
        }

    }

    fun showCustomizedAppAlert(
        mContext: Context,

        alertDialogListener: AlertDialogListener.LanguageDialogButtonListener,
        mSessionManager: SessionManager
    ) {
        val inflater = LayoutInflater.from(mContext)
        val dialogLayout: View = inflater.inflate(R.layout.customize_app, null)
        val alertDialog = Dialog(mContext)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setCancelable(true)
        alertDialog.setContentView(dialogLayout)
        alertDialog.window?.setBackgroundDrawable(
            ResourcesCompat.getDrawable(
                mContext.resources,
                R.drawable.roundbg,
                null
            )
        )
        dialogLayout.tv_title.text = mCommonUtils.getLocaleContext(mContext, mSessionManager).resources.getString(
            R.string.please_select_app_icon_app_name
        )

    }

    fun showAudioAppAlert(
        mContext: Context,

        newsModel: NewsModel,
        mSessionManager: SessionManager,
        mediaPlayer: MediaPlayer
    ) {

        val inflater = LayoutInflater.from(mContext)
        val dialogLayout: View = inflater.inflate(R.layout.activity_play_audio, null)
        val alertDialog = Dialog(mContext)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setCancelable(true)
        alertDialog.setContentView(dialogLayout)
        alertDialog.window?.setBackgroundDrawable(
            ResourcesCompat.getDrawable(
                mContext.resources,
                R.drawable.dialogue_inset,
                null
            )
        )

        alertDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialogLayout.tv_audio_play.text = newsModel.news_title


       var  mediaPlayer  = MediaPlayer.create(mContext, R.raw.sample)
        mediaPlayer.start()
        dialogLayout.img_audioplay.setImageDrawable(
            mContext.resources.getDrawable(
                R.drawable.pauseicon,
                null
            )
        )
        dialogLayout.img_audioplay.setOnClickListener {
            if( mediaPlayer.isPlaying){
                mediaPlayer.stop()
                dialogLayout.img_audioplay.setImageDrawable(
                    mContext.resources.getDrawable(
                        R.drawable.playicon,
                        null
                    )
                )

            }
            else {
                dialogLayout.img_audioplay.setImageDrawable(
                    mContext.resources.getDrawable(
                        R.drawable.pauseicon,
                        null
                    )
                )
                mediaPlayer  = MediaPlayer.create(mContext, R.raw.sample)
                mediaPlayer.start()
            }
        }
        alertDialog.show()

        alertDialog.setOnDismissListener {
            mediaPlayer.stop()
        }
    }


}