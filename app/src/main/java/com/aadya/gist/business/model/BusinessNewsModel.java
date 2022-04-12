package com.aadya.gist.business.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BusinessNewsModel implements Parcelable {

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
    private String time;
    private int drawable;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    private String detail;


    public BusinessNewsModel(int ID, String Title, String date, String time,int drawable, String detail) {

        this.ID = ID;
        this.Title = Title;
        this.date = date;
        this.time = time;
        this.drawable = drawable;
        this.detail = detail;
    }

    protected BusinessNewsModel(Parcel in) {
        ID = in.readInt();
        Title = in.readString();
        date = in.readString();
        time = in.readString();
        drawable = in.readInt();
        detail = in.readString();
    }

    public static final Creator<BusinessNewsModel> CREATOR = new Creator<BusinessNewsModel>() {
        @Override
        public BusinessNewsModel createFromParcel(Parcel in) {
            return new BusinessNewsModel(in);
        }

        @Override
        public BusinessNewsModel[] newArray(int size) {
            return new BusinessNewsModel[size];
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
        parcel.writeString(time);
        parcel.writeInt(drawable);
        parcel.writeString(detail);
    }
}
