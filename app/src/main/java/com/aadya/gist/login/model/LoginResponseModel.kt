package com.aadya.gist.login.model

import com.aadya.gist.registration.model.RegistrationRequestModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponseModel {


    /*@SerializedName("status")
    var status: String? = null

    @SerializedName("statusCode")
    var statusCode: Int? = 0

    @SerializedName("userData")
     var userData : RegistrationRequestModel = RegistrationRequestModel()*/


 /*   @SerializedName("status")
    val status: Boolean = false*/

    @SerializedName("statusCode")
    val statusCode: Int = 0

    @SerializedName("userData")
    val userData: List<RegistrationRequestModel> = listOf()
}