package com.aadya.gist.sports.ui

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
import com.aadya.aadyanews.databinding.FragmentDetailedSportBinding
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.utils.*


class DetailedSportsFragment : Fragment() {

    private lateinit var newsModel: NewsModel
    private lateinit var mCommonUtils: CommonUtils
    private lateinit var mBinding : FragmentDetailedSportBinding
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
        setIncludedLayout()
        setView()
        mCommonUtils.setHeaderColorOfApp(
            mSessionManager.getAppColor(),
            mIncludedLayoutBinding,
            requireContext()
        )
        setColorOfApp(
            mSessionManager.getAppColor(),
            mBinding
        )
        handleClickListener()
        return mBinding.root
    }
    private fun setIncludedLayout() {
        mIncludedLayoutBinding.tvMainheader.text =mCommonUtils.getLocaleContext(requireContext(),mSessionManager).resources.getString(R.string.sportsnews)

        if(mSessionManager.getUserDetailLoginModel()?.get(0)?.status == true)
            mIncludedLayoutBinding.imgLoggedIn.visibility = View.VISIBLE
        else
            mIncludedLayoutBinding.imgLoggedIn.visibility = View.GONE
    }

    private fun handleClickListener() {
        mIncludedLayoutBinding.imgBack.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack()
        }

        mBinding.imgviewFullscreen.setOnClickListener {
            mCommonUtils.LogPrint("TAG","Clicked")
            val intent = Intent (activity, FullScreenWebViewActivity::class.java)
            intent.putExtra("modelBundle",newsModel)
            activity?.startActivity(intent)
        }

    }
    /*private lateinit var kProgressHUD : KProgressHUD
    fun showProgress(message: String?, context: Context) {
        kProgressHUD = KProgressHUD.create(context)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel(message)
            .setCancellable(true)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
            .setBackgroundColor(ContextCompat.getColor(context, R.color.appcolor))
            .show()
    }

    fun dismissProgress() {
        if (kProgressHUD != null && kProgressHUD.isShowing()) kProgressHUD.dismiss()
    }*/

    private fun setView() {
        mBinding.tvDetailedtitle.text = newsModel.news_title

        mBinding.tvDatetime.text =
            mCommonUtils.getDatIne_MMM_dd_yyyy_format(newsModel.schedule_date)
        mBinding.webViewDetailednews.settings.javaScriptEnabled = true
        mBinding.webViewDetailednews.settings.domStorageEnabled = true
        mBinding.webViewDetailednews.settings.allowContentAccess = true;
        mBinding.webViewDetailednews.settings.allowFileAccess = true;
        val youtubeID= mCommonUtils.extractYoutubeVideoId(newsModel.video_url)
        if(newsModel.video_url?.isEmpty()!!){
            mBinding.rlWebview.visibility = View.GONE
            mBinding.tvDetaileddes.visibility = View.GONE
            mBinding.tvWithouturlDetaileddes.visibility = View.VISIBLE
            mBinding.tvWithouturlDetaileddes.text = newsModel.description
        }
        else if(youtubeID == "error" ) {
            if(newsModel.description?.isNotEmpty()!!) {
                mBinding.llNewsdes.visibility = View.VISIBLE
                mBinding.rlWebview.visibility = View.VISIBLE
                mBinding.tvDetaileddes.visibility = View.VISIBLE
                mBinding.tvWithouturlDetaileddes.visibility = View.GONE
                mBinding.tvWithouturlDetaileddes.text = newsModel.description

                val physicalWidthPx = requireContext().physicalScreenRectPx.width()
                val physicalHeightPx = requireContext().physicalScreenRectPx.height()

                var height : Int  = ((physicalHeightPx*(80.0f/100.0f)).toInt())
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
            mBinding.webViewDetailednews.webViewClient = webViewClient
            mBinding.webViewDetailednews.loadUrl(newsModel.video_url!!)

        }
        else{

            if(newsModel.description?.isNotEmpty()!!) {
                mBinding.llNewsdes.visibility = View.VISIBLE
                mBinding.rlWebview.visibility = View.VISIBLE
                mBinding.tvDetaileddes.visibility = View.VISIBLE
                mBinding.tvWithouturlDetaileddes.visibility = View.GONE
                mBinding.tvDetaileddes.text = newsModel.description

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
            mBinding.webViewDetailednews.loadData(videoURL, "text/html", "utf-8")

        }

    }

    private fun intializeMembers(inflater: LayoutInflater, container: ViewGroup?) {
        mCommonUtils = CommonUtils()
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detailed_sport,
            container,
            false
        )
        mSessionManager = SessionManager.getInstance(activity?.applicationContext)!!
        mIncludedLayoutBinding = mBinding.includedDetailed
    }

    companion object {
        @JvmStatic
        fun newInstance(newsModel: NewsModel) =
            DetailedSportsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("newsModel", newsModel)
                }
            }


    }

    private fun setColorOfApp(
        appColor: Int?,
        binding: FragmentDetailedSportBinding,

        ) {
        binding.tvDatetime.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                appColor!!
            )
        )

    }

}