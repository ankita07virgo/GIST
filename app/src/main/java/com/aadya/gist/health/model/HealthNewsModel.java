package com.aadya.gist.health.model;

import android.os.Parcel;
import android.os.Parcelable;

public class HealthNewsModel implements Parcelable {

    private int ID;
    private String Title;

    public String getHealthname() {
        return healthname;
    }

    public void setHealthname(String healthname) {
        this.healthname = healthname;
    }

    private String healthname;
    private int drawable;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    private String detail;


    public HealthNewsModel(int ID, String Title, String healthname, int drawable, String detail) {

        this.ID = ID;
        this.Title = Title;
        this.healthname = healthname;
        this.drawable = drawable;
        this.detail = detail;
    }

    protected HealthNewsModel(Parcel in) {
        ID = in.readInt();
        Title = in.readString();
        healthname = in.readString();
        drawable = in.readInt();
        detail = in.readString();
    }

    public static final Creator<HealthNewsModel> CREATOR = new Creator<HealthNewsModel>() {
        @Override
        public HealthNewsModel createFromParcel(Parcel in) {
            return new HealthNewsModel(in);
        }

        @Override
        public HealthNewsModel[] newArray(int size) {
            return new HealthNewsModel[size];
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
        parcel.writeString(healthname);
        parcel.writeInt(drawable);
        parcel.writeString(detail);
    }
}
