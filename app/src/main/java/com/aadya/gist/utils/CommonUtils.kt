package com.aadya.gist.utils

import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.view.MotionEvent
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.aadya.aadyanews.R
import com.aadya.aadyanews.databinding.DetailedfragHeaderBinding
import com.aadya.aadyanews.databinding.MainHeaderBinding
import com.kaopiz.kprogresshud.KProgressHUD
import com.tapadoo.alerter.Alerter
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


class CommonUtils {

    private lateinit var kProgressHUD : KProgressHUD
    private lateinit var mSessionManager: SessionManager
    private lateinit var mCommonUtils: CommonUtils
    private var mediaPlayer : MediaPlayer? = null


    fun LogPrint(tag: String?, message: String?) {
        if (message != null) {
            Log.d(tag, message)
        }

    }

    fun ToastPrint(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    enum class NavigationMenuId(var value: Int) {
        IPL2020(1), Covid19(2), India(3), States(4), World(5), Sports(
            6
        ),
        Entertainment(7),Business(8),Health(9),Technology(10),Photos(11),Videos(12);
    }
    interface APPSTRINGS {




    }


    interface APIURL {

        companion object {
            const val INDIA_LIST: String ="getNewsByCategoryId"
            const val BASE_URL: String = "http://198.12.156.249:45001/gist/api/gist/"
            const val CREATE_USER: String = "createUser"
            const val LOGIN_USER: String = "userLogin"
            const val CATEGORY_LIST: String = "getNewsCategory"
            const val BookMarked_List: String = "getBookmarkNewsList"
            const val BookMarkNews: String = "updateBookmarkNewsList"
            const val ADDTAG: String = "addNewsCategory"
            const val ADDNEWS: String = "addNews"
            const val CURRENTWEATHER: String = "https://api.openweathermap.org/data/2.5/weather?"
        }
    }

    interface ProgressDialog {
        companion object {
            const val showDialog = 10
            const val dismissDialog = 11
        }
    }

    fun showAlert(
        setDuration: Long,
        title: String?,
        text: String?,
        drawable: Int,
        color: Int,
        activity: Activity,
        mSessionManager: SessionManager
    ) {
        Alerter.create(activity)
            .setTitle(title)
            .setText(text)
            .setDuration(setDuration)
            .setIcon(drawable)
            .setBackgroundColorRes(mSessionManager.getAppColor()!!)
            .show()
    }

    fun showProgress(message: String?, context: Context, appColor: Int?) {
        mSessionManager = SessionManager.getInstance(context)!!
        mCommonUtils = CommonUtils()
        var mcontext = LocaleHelper.setLocale(context,
            mSessionManager.getSelectedLanguage()?.let {
                mCommonUtils.setLanguage(
                    it
                )
            })

        if(appColor == 0){
            kProgressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(mcontext.resources.getString(R.string.pleasewait))
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setBackgroundColor(ContextCompat.getColor(context, R.color.appcolor))
                .show()
        }
        else {
            kProgressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(mcontext.resources.getString(R.string.pleasewait))
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setBackgroundColor(ContextCompat.getColor(context, appColor!!))
                .show()
        }
    }

    fun dismissProgress() {
        if (kProgressHUD != null && kProgressHUD.isShowing()) kProgressHUD.dismiss()
    }

    fun hideKeyboard(activity: Context) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    fun getUserTypeList(
        context: Context,
        mCommonUtils: CommonUtils,
        mSessionManager: SessionManager
    ): List<String>? {
        val tempList:ArrayList<String> = java.util.ArrayList()
        tempList.add(mCommonUtils.getLocaleContext(context,mSessionManager).resources.getString(R.string.Select_UserType))
        tempList.add(mCommonUtils.getLocaleContext(context, mSessionManager).resources.getString(R.string.admin))
        tempList.add(mCommonUtils.getLocaleContext(context, mSessionManager).resources.getString(R.string.editor))
        tempList.add(mCommonUtils.getLocaleContext(context, mSessionManager).resources.getString(R.string.reader))
        return tempList
    }

    fun extractYoutubeVideoId(ytUrl: String?): String? {
        val pattern = "(?<=youtu.be/|watch\\?v=|embed\\/)[^#\\&\\?]*" //(?<=youtu.be/|watch\?v=|/videos/|embed\/)[^#\&\?]*
        val compiledPattern = Pattern.compile(pattern)
        val matcher = compiledPattern.matcher(ytUrl)
        return if (matcher.find()) {
            matcher.group()
        } else {
            "error"
        }
    }

     fun setLanguage(selectedLanguage: String): String {
        when (selectedLanguage) {
            "hindi" -> return "hi"
            "english" -> return "en"
            "bengali" -> return "bn"
            "marathi" -> return "mr"
            "punjabi" -> return "pa"
            "gujarati" -> return "gu"
            else ->  return "hi"
        }
    }
     fun changeAppBarColor(color: Int, activity: Activity) {
        val window: Window = activity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
         window.statusBarColor = ContextCompat.getColor(activity, color)


    }

     fun setColorOfApp(
         appColor: Int?,
         mIncludedLayoutBinding: MainHeaderBinding,
         requireContext: Context,
         requireActivity: FragmentActivity,
         mCommonUtils: CommonUtils,

         ) {
        this.mCommonUtils = mCommonUtils
         mIncludedLayoutBinding.mainheaderRel.setBackgroundColor(
             ContextCompat.getColor(
                 requireContext,
                 appColor!!
             )
         )
         this.mCommonUtils.changeAppBarColor(appColor, requireActivity)
        when (appColor) {
            R.color.option1 -> {
                mIncludedLayoutBinding.imgDrawer.setImageResource((R.drawable.applogo_color1))

            }
            R.color.option2 -> {
                mIncludedLayoutBinding.imgDrawer.setImageResource((R.drawable.applogo_color2))
            }

            R.color.option3 -> {
                mIncludedLayoutBinding.imgDrawer.setImageResource((R.drawable.applogo_color3))
            }
            R.color.option4 -> {
                mIncludedLayoutBinding.imgDrawer.setImageResource((R.drawable.applogo_color4))
            }
            R.color.option5 -> {
                mIncludedLayoutBinding.imgDrawer.setImageResource((R.drawable.applogo_color5))
            }

            R.color.option6 -> {
                mIncludedLayoutBinding.imgDrawer.setImageResource((R.drawable.applogo_color6))
            }

            R.color.option7 -> {
                mIncludedLayoutBinding.imgDrawer.setImageResource((R.drawable.applogo_color7))
            }

            R.color.option8 -> {
                mIncludedLayoutBinding.imgDrawer.setImageResource((R.drawable.applogo_color8))
            }

            R.color.option9 -> {
                mIncludedLayoutBinding.imgDrawer.setImageResource((R.drawable.applogo_color9))
            }

            R.color.option10 -> {
                mIncludedLayoutBinding.imgDrawer.setImageResource((R.drawable.applogo_color10))
            }

            else -> {
                mIncludedLayoutBinding.imgDrawer.setImageResource((R.drawable.applogo))
                mIncludedLayoutBinding.mainheaderRel.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext,
                        R.color.appcolor
                    )
                )
                this.mCommonUtils.changeAppBarColor(R.color.appcolor, requireActivity)
            }

        }

    }

     fun setHeaderColorOfApp(
         appColor: Int?,
         mIncludedLayoutBinding: DetailedfragHeaderBinding,
         requireContext: Context

     ) {
         mIncludedLayoutBinding.rlDetailfrag.setBackgroundColor(
             ContextCompat.getColor(
                 requireContext,
                 appColor!!
             )
         )

    }

    fun getCurrentDate_dd_MM_yyyy(): String {
        val c: Calendar = Calendar.getInstance()
        val df = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        return df.format(c.time)
    }

    fun getDatIne_MMM_dd_yyyy_format(dateString: String?): String {
        try {
            val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val date = fmt.parse(dateString!!)

            val fmtOut = SimpleDateFormat("MMM, dd-yyyy", Locale.US)
            return fmtOut.format(date!!)
        }
        catch(e:Exception){
            e.printStackTrace()
        }
        return ""
    }


    fun getLocaleContext(context: Context, mSessionManager: SessionManager) : Context {
        var context = LocaleHelper.setLocale(context,
            mSessionManager.getSelectedLanguage()?.let {
                setLanguage(
                    it
                )
            })
        return context
    }

    fun playAudioFile(context: Context, actionDown: Int) {
        when(actionDown) {
            MotionEvent.ACTION_DOWN -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.sample)
                mediaPlayer ?. setOnPreparedListener {

                }
                mediaPlayer ?.start()
            }
            MotionEvent.ACTION_DOWN ->{
                if (mediaPlayer != null && mediaPlayer?.isPlaying!!) {
                    mediaPlayer?.reset()
                    mediaPlayer?.prepare()
                    mediaPlayer?.stop()
                    mediaPlayer?.release()
                    mediaPlayer=null
                }
                else
                    ToastPrint(context,"Media player is null")
            }

        }
        }





}