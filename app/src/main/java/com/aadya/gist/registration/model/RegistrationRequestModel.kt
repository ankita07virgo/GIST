package com.aadya.gist.registration.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RegistrationRequestModel {



    @SerializedName("name")
    var name: String? = null

    @SerializedName("password")
    var password: String? = null

    @SerializedName("username")
    var username: String? = null

    @SerializedName("user_email")
    var user_email: String? = null

    @SerializedName("gender")
    var gender: String? = null

    @SerializedName("user_mobile")
    var user_mobile: String? = null

    @SerializedName("user_type")
    var user_type: String? = null

    @SerializedName("user_id")
    var user_id: String? = null

    @SerializedName("updated_date")
    var updated_date: String? = null

    @SerializedName("created_date")
    var created_date: String? = null

    @SerializedName("status")
    var status: Boolean = false


}