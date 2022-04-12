package com.aadya.gist.india.model

import com.google.gson.annotations.SerializedName

class ResponseModel {


    @SerializedName("status")
    var status: Boolean = false

    @SerializedName("statusCode")
    var statusCode: Int = 0

    @SerializedName("newsList")
    var newsList: List<NewsModel> = listOf()
}