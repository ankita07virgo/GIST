package com.aadya.gist.breakingnews.ui

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings.PluginState
import android.widget.MediaController
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.aadya.aadyanews.R
import com.aadya.aadyanews.databinding.FragmentVideosBinding
import com.aadya.gist.utils.CommonUtils


/**
 * Use the VideosFragment
 * to show videos in adapter
 * and fragment getting the videos path
 */
class BreakingNewsVideosFragment : Fragment() {
    private lateinit var mBinding : FragmentVideosBinding
    private var videopath : String = ""
    private lateinit var mCommonUtils: CommonUtils


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_videos, container, false)
        intializeMembers()
        playVideo()
        return mBinding.root
    }

    private fun playVideo() {
        val mediaController = MediaController(activity)
        mediaController.setAnchorView(mBinding.videoView)

        val uri = Uri.parse(videopath)
        mBinding.videoView.setVideoURI(uri)
        mBinding.videoView.requestFocus()
        mBinding.videoView.start()
        mBinding.videoView.setOnErrorListener(object : MediaPlayer.OnErrorListener {
            override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
                Log.d("video", "setOnErrorListener ")
                return true
            }
        })
    }


    private fun intializeMembers() {
        mCommonUtils = CommonUtils()
        }

    companion object {
        fun newInstance(videopath: String): Fragment {
            val fragment = BreakingNewsVideosFragment()
            val args = Bundle()
            args.putString("videopath", videopath)
            fragment.arguments = args
            return fragment
        }}

    override fun onAttach(context: Context) {
        super.onAttach(context)
         videopath = arguments?.get("videopath") as String
    }


}