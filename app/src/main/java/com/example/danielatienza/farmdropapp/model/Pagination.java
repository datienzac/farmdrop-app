package com.example.danielatienza.farmdropapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by danielatienza on 14/12/2016.
 */

public class Pagination implements Parcelable {

    int current;
    int previous;
    int next;
    int per_page;
    int pages;
    int count;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.current);
        dest.writeInt(this.previous);
        dest.writeInt(this.next);
        dest.writeInt(this.per_page);
        dest.writeInt(this.pages);
        dest.writeInt(this.count);
    }

    public Pagination() {
    }

    protected Pagination(Parcel in) {
        this.current = in.readInt();
        this.previous = in.readInt();
        this.next = in.readInt();
        this.per_page = in.readInt();
        this.pages = in.readInt();
        this.count = in.readInt();
    }

    public static final Parcelable.Creator<Pagination> CREATOR = new Parcelable.Creator<Pagination>() {
        @Override
        public Pagination createFromParcel(Parcel source) {
            return new Pagination(source);
        }

        @Override
        public Pagination[] newArray(int size) {
            return new Pagination[size];
        }
    };
}
