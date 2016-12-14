package com.example.danielatienza.farmdropapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import java.util.List;

/**
 * Created by danielatienza on 14/12/2016.
 */
public class Producer implements Parcelable {

    int id;

    String wholesaler_name;

    String updated_at;

    String short_description;

    String permalink;

    String location;

    String description;

    String name;

    String via_wholesaler;

    List<Images> images;

    String created_at;

    public Producer() {
    }

    public int getId() {
        return id;
    }

    public String getWholesaler_name() {
        return wholesaler_name;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getShort_description() {
        return short_description;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getVia_wholesaler() {
        return via_wholesaler;
    }

    public List<Images> getImages() {
        return images;
    }

    public String getCreated_at() {
        return created_at;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.wholesaler_name);
        dest.writeString(this.updated_at);
        dest.writeString(this.short_description);
        dest.writeString(this.permalink);
        dest.writeString(this.location);
        dest.writeString(this.description);
        dest.writeString(this.name);
        dest.writeString(this.via_wholesaler);
        dest.writeTypedList(this.images);
        dest.writeString(this.created_at);
    }

    protected Producer(Parcel in) {
        this.id = in.readInt();
        this.wholesaler_name = in.readString();
        this.updated_at = in.readString();
        this.short_description = in.readString();
        this.permalink = in.readString();
        this.location = in.readString();
        this.description = in.readString();
        this.name = in.readString();
        this.via_wholesaler = in.readString();
        this.images = in.createTypedArrayList(Images.CREATOR);
        this.created_at = in.readString();
    }

    public static final Creator<Producer> CREATOR = new Creator<Producer>() {
        @Override
        public Producer createFromParcel(Parcel source) {
            return new Producer(source);
        }

        @Override
        public Producer[] newArray(int size) {
            return new Producer[size];
        }
    };
}