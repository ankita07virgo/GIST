package com.aadya.gist.navigation.model

import com.aadya.gist.registration.model.RegistrationRequestModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NavigationResponseModel {

    @SerializedName("status")
    var status: Boolean = false

    @SerializedName("statusCode")
    var statusCode: Int = 0

    @SerializedName("newsCategory")
    var newsCategory: List<NavigationMenu> = listOf()


}