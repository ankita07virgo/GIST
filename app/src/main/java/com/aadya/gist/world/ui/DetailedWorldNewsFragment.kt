package com.aadya.gist.world.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.aadya.aadyanews.R
import com.aadya.aadyanews.databinding.DetailedfragHeaderBinding
import com.aadya.aadyanews.databinding.FragmentDetailedWorldnewsBinding
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.utils.*


class DetailedWorldNewsFragment : Fragment() {

    private lateinit var newModel: NewsModel
    private lateinit var mCommonUtils: CommonUtils
    private lateinit var mBinding: FragmentDetailedWorldnewsBinding
    private lateinit var mIncludedLayoutBinding: DetailedfragHeaderBinding
    private var videoURL: String = ""
    private lateinit var mSessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            newModel = it.getParcelable("model")!!

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        intializeMembers(inflater, container)
        setView()
        setIncludedLayout()
        handleClickListener()
        mCommonUtils.setHeaderColorOfApp(
            mSessionManager.getAppColor(),
            mIncludedLayoutBinding,
            requireContext()
        )
        setColorOfApp(
            mSessionManager.getAppColor(),
            mBinding
        )
        return mBinding.root
    }



    private fun setIncludedLayout() {
        mIncludedLayoutBinding = mBinding.includedDetailed
        mIncludedLayoutBinding.tvMainheader.text =
            mCommonUtils.getLocaleContext(requireContext(),mSessionManager).resources.getString(R.string.worldnews)
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
            intent.putExtra("modelBundle",newModel)
            activity?.startActivity(intent)
        }

    }

    private fun setView() {
        mBinding.tvDetailedtitle.text = newModel.news_title

        mBinding.tvDatetime.text =
            mCommonUtils.getDatIne_MMM_dd_yyyy_format(newModel.schedule_date)
        mBinding.webViewDetailednews.settings.javaScriptEnabled = true
        mBinding.webViewDetailednews.settings.domStorageEnabled = true
        mBinding.webViewDetailednews.settings.allowContentAccess = true;
        mBinding.webViewDetailednews.settings.allowFileAccess = true;
        val youtubeID= mCommonUtils.extractYoutubeVideoId(newModel.video_url)
        if(newModel.video_url?.isEmpty()!!){
            mBinding.rlWebview.visibility = View.GONE
            mBinding.tvDetaileddes.visibility = View.GONE
            mBinding.tvWithouturlDetaileddes.visibility = View.VISIBLE
            mBinding.tvWithouturlDetaileddes.text = newModel.description
        }
        else if(youtubeID == "error" ) {
            if(newModel.description?.isNotEmpty()!!) {
                mBinding.llNewsdes.visibility = View.VISIBLE
                mBinding.rlWebview.visibility = View.VISIBLE
                mBinding.tvDetaileddes.visibility = View.VISIBLE
                mBinding.tvWithouturlDetaileddes.visibility = View.GONE
                mBinding.tvWithouturlDetaileddes.text = newModel.description

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
            mBinding.webViewDetailednews.loadUrl(newModel.video_url!!)

        }
        else{

            if(newModel.description?.isNotEmpty()!!) {
                mBinding.llNewsdes.visibility = View.VISIBLE
                mBinding.rlWebview.visibility = View.VISIBLE
                mBinding.tvDetaileddes.visibility = View.VISIBLE
                mBinding.tvWithouturlDetaileddes.visibility = View.GONE
                mBinding.tvDetaileddes.text = newModel.description

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
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detailed_worldnews,
            container,
            false
        )
        mCommonUtils = CommonUtils()
        mSessionManager = SessionManager.getInstance(activity?.applicationContext)!!


    }


    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(worldNewsModel: NewsModel) =
            DetailedWorldNewsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("model", worldNewsModel)
                }
            }

    }

    private fun setColorOfApp(
        appColor: Int?,
        binding: FragmentDetailedWorldnewsBinding,

        ) {
        binding.tvDatetime.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                appColor!!
            )
        )

    }
}