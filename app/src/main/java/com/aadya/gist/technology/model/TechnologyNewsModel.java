package com.aadya.gist.technology.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TechnologyNewsModel implements Parcelable {

    private int ID;
    private String Title;

    public String getTechnologyname() {
        return technologyname;
    }

    public void setTechnologyname(String technologyname) {
        this.technologyname = technologyname;
    }

    private String technologyname;
    private int drawable;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    private String detail;


    public TechnologyNewsModel(int ID, String Title, String technologyname, int drawable, String detail) {

        this.ID = ID;
        this.Title = Title;
        this.technologyname = technologyname;
        this.drawable = drawable;
        this.detail = detail;
    }

    protected TechnologyNewsModel(Parcel in) {
        ID = in.readInt();
        Title = in.readString();
        technologyname = in.readString();
        drawable = in.readInt();
        detail = in.readString();
    }

    public static final Creator<TechnologyNewsModel> CREATOR = new Creator<TechnologyNewsModel>() {
        @Override
        public TechnologyNewsModel createFromParcel(Parcel in) {
            return new TechnologyNewsModel(in);
        }

        @Override
        public TechnologyNewsModel[] newArray(int size) {
            return new TechnologyNewsModel[size];
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
        parcel.writeString(technologyname);
        parcel.writeInt(drawable);
        parcel.writeString(detail);
    }
}
