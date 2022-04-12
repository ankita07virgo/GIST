package com.aadya.gist.addtag.model

import com.google.gson.annotations.SerializedName

class AddTAGResponseModel {

    @SerializedName("status")
    var status: Int = 0

    @SerializedName("category_id")
    var category_id: Int = 0

    @SerializedName("statusCode")
    var statusCode: Int = 0

    @SerializedName("message")
    var message: String = ""
}