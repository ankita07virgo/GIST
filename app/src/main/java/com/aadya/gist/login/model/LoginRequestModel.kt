package com.aadya.gist.login.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginRequestModel {



    @SerializedName("username")
    var username: String? = null

    @SerializedName("password")
    var password: String? = null




}