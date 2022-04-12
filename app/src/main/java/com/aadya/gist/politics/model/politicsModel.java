package com.aadya.gist.politics.model;

import android.os.Parcel;
import android.os.Parcelable;

public class politicsModel implements Parcelable {

    private int ID;
    private String Title;
    private String date;
    private String time;
    private int drawable;

    public politicsModel(int ID, String Title, String date, String time, int drawable) {

        this.ID = ID;
        this.Title = Title;
        this.date = date;
        this.time = time;
        this.drawable = drawable;
    }

    protected politicsModel(Parcel in) {
        ID = in.readInt();
        Title = in.readString();
        date = in.readString();
        time = in.readString();
        drawable = in.readInt();
    }

    public static final Creator<politicsModel> CREATOR = new Creator<politicsModel>() {
        @Override
        public politicsModel createFromParcel(Parcel in) {
            return new politicsModel(in);
        }

        @Override
        public politicsModel[] newArray(int size) {
            return new politicsModel[size];
        }
    };

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String navigationText) {
        this.Title = navigationText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(ID);
        parcel.writeString(Title);
        parcel.writeString(date);
        parcel.writeString(time);
        parcel.writeInt(drawable);
    }
}
