package com.example.highlightapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class GlobalCity implements Parcelable {
    private int id;
    private String cityName;// Toronto , On,Canada

    public GlobalCity(String city){
        this.cityName = city;
    }

    protected GlobalCity(Parcel in) {
        id = in.readInt();
        cityName = in.readString();
    }

    public static final Creator<GlobalCity> CREATOR = new Creator<GlobalCity>() {
        @Override
        public GlobalCity createFromParcel(Parcel in) {
            return new GlobalCity(in);
        }

        @Override
        public GlobalCity[] newArray(int size) {
            return new GlobalCity[size];
        }
    };

    public GlobalCity() {

    }

    public int getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(cityName);
    }
}
