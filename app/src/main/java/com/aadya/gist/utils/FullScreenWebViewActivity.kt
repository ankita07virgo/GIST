package com.aadya.gist.utils

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.aadya.aadyanews.R
import com.aadya.aadyanews.databinding.ActivityFullScreenWebViewBinding
import com.aadya.aadyanews.databinding.DetailedfragHeaderBinding
import com.aadya.gist.india.model.NewsModel

class FullScreenWebViewActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityFullScreenWebViewBinding
    private lateinit var mCommonUtils: CommonUtils
    private lateinit var mSessionManager: SessionManager
    private lateinit var mIncludedLayoutBinding: DetailedfragHeaderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intializeMembers()
        mCommonUtils.changeAppBarColor(
            mSessionManager.getAppColor()!!, this@FullScreenWebViewActivity
        )
        handleClickListner()

    }

    private fun handleClickListner() {
        mIncludedLayoutBinding.imgBack.setOnClickListener {
            this@FullScreenWebViewActivity.finish()
        }
    }

    private fun playVideo(newsModel: NewsModel?) {
        val youtubeID = mCommonUtils.extractYoutubeVideoId(newsModel?.video_url)
        mBinding.webViewDetailednews.settings.javaScriptEnabled = true
        mBinding.webViewDetailednews.settings.domStorageEnabled = true
        if (newsModel?.video_url?.isEmpty()!!) {
            mBinding.webViewDetailednews.visibility = View.GONE
        } else if (youtubeID == "error") {

            mBinding.webViewDetailednews.visibility = View.VISIBLE
            val webViewClient = WebViewClient()
            mBinding.webViewDetailednews.webViewClient = webViewClient

            mBinding.webViewDetailednews.loadUrl(newsModel.video_url!!)

        } else {
            mBinding.webViewDetailednews.visibility = View.VISIBLE
            var videoURL =
                "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/$youtubeID\"+?rel=\"0\"+ frameborder=\"0\" allowfullscreen></iframe>"
            mBinding.webViewDetailednews.loadData(videoURL, "text/html", "utf-8")
        }
    }


    private fun intializeMembers() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_full_screen_web_view)
        mIncludedLayoutBinding = mBinding.includedDetailed
        mCommonUtils = CommonUtils()
        mSessionManager =
            SessionManager.getInstance(this@FullScreenWebViewActivity.applicationContext)!!
        mIncludedLayoutBinding.tvMainheader.text = mCommonUtils.getLocaleContext(
            this@FullScreenWebViewActivity,
            mSessionManager
        ).resources.getString(R.string.breakingnews)
        mCommonUtils.setHeaderColorOfApp(
            mSessionManager.getAppColor(),
            mIncludedLayoutBinding,
            this@FullScreenWebViewActivity
        )


        var bundle: Bundle? = intent.extras
        var model: NewsModel? = bundle?.getParcelable("modelBundle")
        setTitle(model)
        playVideo(model)
    }

    private fun setTitle(model: NewsModel?) {
        when (model?.category_id) {
            "1" -> mIncludedLayoutBinding.tvMainheader.text = mCommonUtils.getLocaleContext(
                this@FullScreenWebViewActivity,
                mSessionManager
            ).resources.getString(R.string.indianews)
            "2" -> mIncludedLayoutBinding.tvMainheader.text = mCommonUtils.getLocaleContext(
                this@FullScreenWebViewActivity,
                mSessionManager
            ).resources.getString(R.string.worldnews)
            "3" -> mIncludedLayoutBinding.tvMainheader.text = mCommonUtils.getLocaleContext(
                this@FullScreenWebViewActivity,
                mSessionManager
            ).resources.getString(R.string.politicsnews)
            "4" ->
                mIncludedLayoutBinding.tvMainheader.text = mCommonUtils.getLocaleContext(
                    this@FullScreenWebViewActivity,
                    mSessionManager
                ).resources.getString(R.string.businessnews)
            "5" ->mIncludedLayoutBinding.tvMainheader.text =mCommonUtils.getLocaleContext(this@FullScreenWebViewActivity,mSessionManager).resources.getString(R.string.sportsnews)
            "6"-> mIncludedLayoutBinding.tvMainheader.text =mCommonUtils.getLocaleContext(this@FullScreenWebViewActivity,mSessionManager).resources.getString(R.string.breakingnews)
            "7" -> mIncludedLayoutBinding.tvMainheader.text =mCommonUtils.getLocaleContext(this@FullScreenWebViewActivity,mSessionManager).resources.getString(R.string.Localnews)
            "8"-> mIncludedLayoutBinding.tvMainheader.text =mCommonUtils.getLocaleContext(this@FullScreenWebViewActivity,mSessionManager).resources.getString(R.string.healthnews)
            "9"-> mIncludedLayoutBinding.tvMainheader.text =mCommonUtils.getLocaleContext(this@FullScreenWebViewActivity,mSessionManager).resources.getString(R.string.covidnews)
            "10"->mIncludedLayoutBinding.tvMainheader.text =mCommonUtils.getLocaleContext(this@FullScreenWebViewActivity,mSessionManager).resources.getString(R.string.entertainmentnews)
            "11"-> mIncludedLayoutBinding.tvMainheader.text =mCommonUtils.getLocaleContext(this@FullScreenWebViewActivity,mSessionManager).resources.getString(R.string.Lifestylenews)
            "12"-> mIncludedLayoutBinding.tvMainheader.text =mCommonUtils.getLocaleContext(this@FullScreenWebViewActivity,mSessionManager).resources.getString(R.string.technologynews)
            else -> {
                mIncludedLayoutBinding.tvMainheader.text =mCommonUtils.getLocaleContext(this@FullScreenWebViewActivity,mSessionManager).resources.getString(R.string.breakingnews)
            }
            }
    }

}