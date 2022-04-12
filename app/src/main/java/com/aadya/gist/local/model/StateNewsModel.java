package com.aadya.gist.local.model;

import android.os.Parcel;
import android.os.Parcelable;

public class StateNewsModel implements Parcelable {

    private int ID;
    private String Title;
    private String state;
    private int drawable;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    private String detail;


    public StateNewsModel(int ID, String Title, String state, int drawable,String detail) {

        this.ID = ID;
        this.Title = Title;
        this.state = state;
        this.drawable = drawable;
        this.detail = detail;
    }

    protected StateNewsModel(Parcel in) {
        ID = in.readInt();
        Title = in.readString();
        state = in.readString();
        drawable = in.readInt();
        detail = in.readString();
    }

    public static final Creator<StateNewsModel> CREATOR = new Creator<StateNewsModel>() {
        @Override
        public StateNewsModel createFromParcel(Parcel in) {
            return new StateNewsModel(in);
        }

        @Override
        public StateNewsModel[] newArray(int size) {
            return new StateNewsModel[size];
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

    public String getState() {
        return state;
    }

    public void setState(String date) {
        this.state = state;
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
        parcel.writeString(state);
        parcel.writeInt(drawable);
        parcel.writeString(detail);
    }
}
