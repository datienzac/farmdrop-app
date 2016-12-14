package com.example.danielatienza.farmdropapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by danielatienza on 14/12/2016.
 */

public class Images implements Parcelable {

    private String position;

    private String path;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.position);
        dest.writeString(this.path);
    }

    public Images() {
    }

    protected Images(Parcel in) {
        this.position = in.readString();
        this.path = in.readString();
    }

    public String getPosition() {
        return position;
    }

    public String getPath() {
        return path;
    }

    public static final Parcelable.Creator<Images> CREATOR = new Parcelable.Creator<Images>() {
        @Override
        public Images createFromParcel(Parcel source) {
            return new Images(source);
        }

        @Override
        public Images[] newArray(int size) {
            return new Images[size];
        }
    };
}
