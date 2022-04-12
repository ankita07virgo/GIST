package com.aadya.gist.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.recyclerview.widget.RecyclerView
import com.aadya.aadyanews.R
import com.aadya.gist.breakingnews.model.youTubeVideos


class VideoAdapter internal constructor(youtubeVideoList: List<youTubeVideos>) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
    private val youtubeVideoList: List<youTubeVideos>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.webview, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        youtubeVideoList[position].videoUrl?.let { holder.videoWeb.loadData(it, "text/html", "utf-8") }
    }

    override fun getItemCount(): Int {
        return youtubeVideoList.size
    }

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var videoWeb: WebView

        init {
            videoWeb = itemView.findViewById(R.id.webView)
            videoWeb.settings.javaScriptEnabled = true
            videoWeb.webChromeClient = object : WebChromeClient() {}
        }
    }

    init {
        this.youtubeVideoList = youtubeVideoList
    }
}