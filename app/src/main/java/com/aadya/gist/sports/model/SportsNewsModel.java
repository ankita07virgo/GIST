package com.aadya.gist.sports.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SportsNewsModel implements Parcelable {

    private int ID;
    private String Title;

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

    private String date;
    private int drawable;
    private String detail;
    private String time;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }




    public SportsNewsModel(int ID, String Title, int drawable, String detail,String date,String time) {

        this.ID = ID;
        this.Title = Title;
        this.date = date;
        this.drawable = drawable;
        this.detail = detail;
        this.time = time;
    }

    protected SportsNewsModel(Parcel in) {
        ID = in.readInt();
        Title = in.readString();
        date = in.readString();
        time = in.readString();
        drawable = in.readInt();
        detail = in.readString();
    }

    public static final Creator<SportsNewsModel> CREATOR = new Creator<SportsNewsModel>() {
        @Override
        public SportsNewsModel createFromParcel(Parcel in) {
            return new SportsNewsModel(in);
        }

        @Override
        public SportsNewsModel[] newArray(int size) {
            return new SportsNewsModel[size];
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
        parcel.writeInt(drawable);
        parcel.writeString(detail);
        parcel.writeString(time);
    }
}
