package com.aadya.gist.entertainment.model;

import android.os.Parcel;
import android.os.Parcelable;

public class EntertainmentNewsModel implements Parcelable {

    private int ID;
    private String Title;

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    private String actor;
    private int drawable;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    private String detail;


    public EntertainmentNewsModel(int ID, String Title, String actor, int drawable, String detail) {

        this.ID = ID;
        this.Title = Title;
        this.actor = actor;
        this.drawable = drawable;
        this.detail = detail;
    }

    protected EntertainmentNewsModel(Parcel in) {
        ID = in.readInt();
        Title = in.readString();
        actor = in.readString();
        drawable = in.readInt();
        detail = in.readString();
    }

    public static final Creator<EntertainmentNewsModel> CREATOR = new Creator<EntertainmentNewsModel>() {
        @Override
        public EntertainmentNewsModel createFromParcel(Parcel in) {
            return new EntertainmentNewsModel(in);
        }

        @Override
        public EntertainmentNewsModel[] newArray(int size) {
            return new EntertainmentNewsModel[size];
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
        parcel.writeString(actor);
        parcel.writeInt(drawable);
        parcel.writeString(detail);
    }
}
