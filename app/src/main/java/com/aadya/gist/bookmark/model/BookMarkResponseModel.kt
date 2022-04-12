package com.aadya.gist.bookmark.model

import com.google.gson.annotations.SerializedName

class BookMarkResponseModel {


    @SerializedName("status")
    var status: Boolean = false

    @SerializedName("statusCode")
    var statusCode: Int = 0

    @SerializedName("message")
    var message: String? = null



}