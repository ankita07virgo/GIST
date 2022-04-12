package com.aadya.gist.utils

import com.aadya.gist.india.model.NewsModel

interface playAudioClickListner {
    fun onAudioClick( newsModel: NewsModel)
}