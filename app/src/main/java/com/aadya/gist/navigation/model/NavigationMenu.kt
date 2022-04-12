package com.aadya.gist.navigation.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class NavigationMenu() : Parcelable {


    @SerializedName("category_id")
    var category_id: String? = null

    @SerializedName("category_name")
    var category_name: String? = null



    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
        category_id = parcel.readString()
        category_name = parcel.readString()
    }

    constructor(category_id: String?, category_name: String?) : this(){
this.category_id = category_id
        this.category_name = category_name
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(category_id)
        parcel.writeString(category_name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NavigationMenu> {
        override fun createFromParcel(parcel: Parcel): NavigationMenu {
            return NavigationMenu(parcel)
        }

        override fun newArray(size: Int): Array<NavigationMenu?> {
            return arrayOfNulls(size)
        }
    }
}