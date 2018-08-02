package com.example.learnretrofit.LearnMoreProcess;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 杨豪 on 2018/7/31.
 */

public class Apple implements Parcelable {

    public int size;
    public String color;

    public Apple(int size, String color) {
        this.size = size;
        this.color = color;
    }

    protected Apple(Parcel in) {
        size = in.readInt();
        color = in.readString();
    }

    public static final Creator<Apple> CREATOR = new Creator<Apple>() {
        @Override
        public Apple createFromParcel(Parcel in) {
            return new Apple(in);
        }

        @Override
        public Apple[] newArray(int size) {
            return new Apple[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(size);
        dest.writeString(color);
    }
}
