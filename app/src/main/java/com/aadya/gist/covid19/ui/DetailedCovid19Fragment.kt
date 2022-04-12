package com.aadya.gist.covid19.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.aadya.aadyanews.R
import com.aadya.aadyanews.databinding.DetailedfragHeaderBinding
import com.aadya.aadyanews.databinding.FragmentDetailedCovid19Binding
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.utils.CommonUtils
import com.aadya.gist.utils.FullScreenWebViewActivity
import com.aadya.gist.utils.SessionManager
import com.aadya.gist.utils.physicalScreenRectPx
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException


class DetailedCovid19Fragment : Fragment() {

    private lateinit var newsModel: NewsModel
    private lateinit var mCommonUtils: CommonUtils
    private lateinit var mBinding : FragmentDetailedCovid19Binding
    private lateinit var mIncludedLayoutBinding: DetailedfragHeaderBinding
    private var videoURL : String = ""
    private lateinit var mSessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            newsModel = it.getParcelable("newsModel")!!

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        intializeMembers(inflater, container)
        handleClickListener()
        setIncludedLayout()
        mCommonUtils.LogPrint("TAG", "Session color " + mSessionManager.getAppColor())
        mCommonUtils.setHeaderColorOfApp(
            mSessionManager.getAppColor(),
            mIncludedLayoutBinding,
            requireContext()
        )
        setColorOfApp(
            mSessionManager.getAppColor(),
            mBinding
        )
        setView()
        return mBinding.root
    }

    private fun setIncludedLayout() {
        mIncludedLayoutBinding.tvMainheader.text =mCommonUtils.getLocaleContext(
            requireContext(),
            mSessionManager
        ).resources.getString(R.string.covidnews)
        if(mSessionManager.getUserDetailLoginModel()?.get(0)?.status == true)
            mIncludedLayoutBinding.imgLoggedIn.visibility = View.VISIBLE
        else
            mIncludedLayoutBinding.imgLoggedIn.visibility = View.GONE
    }

    private fun setView() {
        mBinding.tvCoviddetailedtitle.text = newsModel.news_title

        mBinding.tvCoviddatetime.text =
            mCommonUtils.getDatIne_MMM_dd_yyyy_format(newsModel.schedule_date)
        mBinding.webViewDetailedcovidnews.settings.javaScriptEnabled = true
        mBinding.webViewDetailedcovidnews.settings.domStorageEnabled = true
        mBinding.webViewDetailedcovidnews.settings.allowContentAccess = true;
        mBinding.webViewDetailedcovidnews.settings.allowFileAccess = true;
        val youtubeID= mCommonUtils.extractYoutubeVideoId(newsModel.video_url)
        if(newsModel.video_url?.isEmpty()!!){
            mBinding.rlWebview.visibility = View.GONE
            mBinding.tvCoviddetaileddes.visibility = View.GONE
            mBinding.tvWithouturlCoviddetaileddes.visibility = View.VISIBLE
            mBinding.tvWithouturlCoviddetaileddes.text = newsModel.description
        }
        else if(youtubeID == "error" ) {
            if(newsModel.description?.isNotEmpty()!!) {
                mBinding.llNewsdes.visibility = View.VISIBLE
                mBinding.rlWebview.visibility = View.VISIBLE
                mBinding.tvCoviddetaileddes.visibility = View.VISIBLE
                mBinding.tvWithouturlCoviddetaileddes.visibility = View.GONE
                mBinding.tvCoviddetaileddes.text = newsModel.description

                val physicalWidthPx = requireContext().physicalScreenRectPx.width()
                val physicalHeightPx = requireContext().physicalScreenRectPx.height()

                var height : Int  = ((physicalHeightPx*(60.0f/100.0f)).toInt())
                Log.d(
                    "TAG",
                    "[PX] physical screen width: $physicalWidthPx , height: $physicalHeightPx , Height : $height"
                )
                mBinding.rlWebview.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    height
                )

            }

            else {
                mBinding.llNewsdes.visibility = View.GONE
                mBinding.rlWebview.visibility = View.VISIBLE
                mBinding.rlWebview.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
            }

            val webViewClient = WebViewClient()
            mBinding.webViewDetailedcovidnews.webViewClient = webViewClient
            mBinding.webViewDetailedcovidnews.loadUrl(newsModel.video_url!!)

        }
        else{

            if(newsModel.description?.isNotEmpty()!!) {
                mBinding.llNewsdes.visibility = View.VISIBLE
                mBinding.rlWebview.visibility = View.VISIBLE
                mBinding.tvCoviddetaileddes.visibility = View.VISIBLE
                mBinding.tvWithouturlCoviddetaileddes.visibility = View.GONE
                mBinding.tvCoviddetaileddes.text = newsModel.description

                val physicalWidthPx = requireContext().physicalScreenRectPx.width()
                val physicalHeightPx = requireContext().physicalScreenRectPx.height()

                var height : Int  = ((physicalHeightPx*(45.0f/100.0f)).toInt())
                Log.d(
                    "TAG",
                    "[PX] physical screen width: $physicalWidthPx , height: $physicalHeightPx , Height : $height"
                )
                mBinding.rlWebview.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    height
                )

            }

            else {
                mBinding.llNewsdes.visibility = View.GONE
                mBinding.rlWebview.visibility = View.VISIBLE
                mBinding.rlWebview.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
            }
            videoURL =
                "<iframe width=\"100%\" height=\"100%\" src=\"https://www" + ".youtube.com/embed/" + youtubeID + "\"+?rel=\"0\"+ frameborder=\"0\" allowfullscreen></iframe>"
            mBinding.webViewDetailedcovidnews.loadData(videoURL, "text/html", "utf-8")

        }

    }

    private fun intializeMembers(inflater: LayoutInflater, container: ViewGroup?) {
        mSessionManager = SessionManager.getInstance(activity?.applicationContext)!!
        mCommonUtils = CommonUtils()
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detailed_covid19,
            container,
            false
        )
        mIncludedLayoutBinding = mBinding.includedDetailed
    }

    private fun handleClickListener() {
        mIncludedLayoutBinding.imgBack.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack()
        }

        mBinding.imgviewFullscreen.setOnClickListener {
            mCommonUtils.LogPrint("TAG", "Clicked")
            val intent = Intent(activity, FullScreenWebViewActivity::class.java)
            intent.putExtra("modelBundle", newsModel)
            activity?.startActivity(intent)
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(newsModel: NewsModel) =
            DetailedCovid19Fragment().apply {
                arguments = Bundle().apply {
                    putParcelable("newsModel", newsModel)
                }
            }


    }

    private fun setColorOfApp(
        appColor: Int?,
        binding: FragmentDetailedCovid19Binding,

        ) {
        binding.tvCoviddatetime.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                appColor!!
            )
        )

    }
}


