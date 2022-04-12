package com.aadya.gist.bookmark.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BookMarkRequestModel {



    @SerializedName("news_id")
    var news_id: String? = null

    @SerializedName("is_bookmark")
    var is_bookmark: String? = null



}