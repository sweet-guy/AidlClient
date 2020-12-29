package com.wdcloud.aidlserver;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Info:
 * Created by Umbrella.
 * CreateTime: 2020/12/29 14:41
 */

public class Book implements Parcelable {
    private String name;

    public Book(String name) {
        this.name = name;
    }

    protected Book(Parcel in) {
        name = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name == null ? "" : name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
    }
    public void readFromParcel(Parcel dest) {
        name = dest.readString();
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                '}';
    }
}
