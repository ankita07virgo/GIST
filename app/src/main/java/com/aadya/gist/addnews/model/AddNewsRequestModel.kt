package com.aadya.gist.addnews.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AddNewsRequestModel {

    @SerializedName("category_id")
    var category_id: String? = null

    @SerializedName("news_title")
    var news_title: String? = null

    @SerializedName("short_description")
    var short_description: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("video_url")
    var video_url: String? = null


    @SerializedName("added_by")
    var added_by: String? = "1"

    @SerializedName("is_bookmark")
    var is_bookmark: String? = "0"

    @SerializedName("language_id")
    var language_id: String? = null

    @SerializedName("schedule_date")
    var schedule_date: String? = null
}