package com.aadya.gist.covid19.ui

import com.aadya.gist.india.model.NewsModel

interface NewsListItemClickListner {
    fun onItemClick(newsModel: NewsModel)
}