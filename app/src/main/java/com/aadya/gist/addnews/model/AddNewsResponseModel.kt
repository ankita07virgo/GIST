package com.aadya.gist.addnews.model

import com.google.gson.annotations.SerializedName

class AddNewsResponseModel {

    @SerializedName("status")
    var status: Int = 0

    @SerializedName("news_id")
    var news_id: Int = 0

    @SerializedName("message")
    var message: String = ""

    @SerializedName("statusCode")
    var statusCode: Int = 0


}