package com.aadya.gist.registration.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class RegistrationResponseModel {

    @SerializedName("status")
    @Expose
    private var status: Int = 0

    @SerializedName("statusCode")
    @Expose
    private var statusCode: Int = 0

    @SerializedName("message")
    @Expose
    private var message: String = ""

    @SerializedName("userId")
    @Expose
    private var userId: Int = 0


    fun getuserId(): Int {
        return userId
    }


    fun setuserId(status: Int) {
        this.userId = userId
    }

    fun getstatus(): Int {
        return status
    }


    fun setstatus(status: Int) {
        this.status = status
    }
    fun getstatusCode(): Int {
        return statusCode
    }


    fun setstatusCode(statusCode: Int) {
       this.statusCode = statusCode
    }


    fun getmessage(): String {
        return message
    }


    fun setmessage(message: String) {
        this.message = message
    }
}