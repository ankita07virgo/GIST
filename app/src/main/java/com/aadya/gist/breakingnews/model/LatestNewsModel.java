package com.aadya.gist.breakingnews.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

public class LatestNewsModel implements Parcelable {

    private int ID;
    private String Title;
    private String date;
    private String time;
    private int drawable;

    public boolean isBookMarked() {
        return isBookMarked;
    }

    public void setBookMarked(boolean bookMarked) {
        isBookMarked = bookMarked;
    }

    private boolean isBookMarked;

    public LatestNewsModel(int ID, String Title, String date, String time, int drawable,boolean isBookMarked) {

        this.ID = ID;
        this.Title = Title;
        this.date = date;
        this.time = time;
        this.drawable = drawable;
        this.isBookMarked = isBookMarked;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected LatestNewsModel(Parcel in) {
        ID = in.readInt();
        Title = in.readString();
        date = in.readString();
        time = in.readString();
        drawable = in.readInt();
        isBookMarked = in.readBoolean();
    }

    public static final Creator<LatestNewsModel> CREATOR = new Creator<LatestNewsModel>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public LatestNewsModel createFromParcel(Parcel in) {
            return new LatestNewsModel(in);
        }

        @Override
        public LatestNewsModel[] newArray(int size) {
            return new LatestNewsModel[size];
        }
    };

    public LatestNewsModel() {

    }

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

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(ID);
        parcel.writeString(Title);
        parcel.writeString(date);
        parcel.writeString(time);
        parcel.writeInt(drawable);
        parcel.writeBoolean(isBookMarked);
    }
}
