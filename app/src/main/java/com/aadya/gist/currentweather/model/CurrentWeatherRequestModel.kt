package com.aadya.gist.currentweather.model

import com.google.gson.annotations.SerializedName

class CurrentWeatherRequestModel {



    @SerializedName("lat")
    var lat: Double? = 0.0

    @SerializedName("lon")
    var lon: Double? = 0.0

    @SerializedName("appid")
    var appid: String? = null





}