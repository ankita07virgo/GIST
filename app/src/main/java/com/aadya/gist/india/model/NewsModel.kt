package com.aadya.gist.india.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

 class NewsModel() : Parcelable {
    @SerializedName("news_id")
    var news_id: String? = null

    @SerializedName("news_title")
    var news_title: String? = null

    @SerializedName("short_description")
    var short_description: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("added_by")
    var added_by: String? = null

    @SerializedName("category_id")
    var category_id: String? = null

    @SerializedName("created_date")
    var created_date: String? = null

    @SerializedName("category_name")
    var category_name: String? = null

    @SerializedName("is_bookmark")
    var is_bookmark: String? = null

    @SerializedName("video_url")
    var video_url: String? = null

    @SerializedName("schedule_date")
    var schedule_date: String? = null

    constructor(parcel: Parcel) : this() {
        news_id = parcel.readString()
        news_title = parcel.readString()
        short_description = parcel.readString()
        description = parcel.readString()
        added_by = parcel.readString()
        category_id = parcel.readString()
        created_date = parcel.readString()
        category_name = parcel.readString()
        is_bookmark = parcel.readString()
        video_url = parcel.readString()
        schedule_date = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(news_id)
        parcel.writeString(news_title)
        parcel.writeString(short_description)
        parcel.writeString(description)
        parcel.writeString(added_by)
        parcel.writeString(category_id)
        parcel.writeString(created_date)
        parcel.writeString(category_name)
        parcel.writeString(is_bookmark)
        parcel.writeString(video_url)
        parcel.writeString(schedule_date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsModel> {
        override fun createFromParcel(parcel: Parcel): NewsModel {
            return NewsModel(parcel)
        }

        override fun newArray(size: Int): Array<NewsModel?> {
            return arrayOfNulls(size)
        }
    }


}