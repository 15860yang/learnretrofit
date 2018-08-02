package com.example.learnretrofit.LearnMoreProcess;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by 杨豪 on 2018/7/30.
 */

public class ParcelableTest implements Parcelable {

    public int id;
    public String name;

    public boolean isMale;

    public Book mBook;

    public ParcelableTest(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private ParcelableTest(Parcel in) {
        id = in.readInt();
        name = in.readString();
        isMale = in.readInt() == 1;
        mBook = in.readParcelable(Thread.currentThread().getContextClassLoader());
    }

    public static final Creator<ParcelableTest> CREATOR = new Creator<ParcelableTest>() {
        @Override
        public ParcelableTest createFromParcel(Parcel in) {
            return new ParcelableTest(in);
        }

        @Override
        public ParcelableTest[] newArray(int size) {
            return new ParcelableTest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt( isMale ? 1 : 0);
        dest.writeParcelable(mBook,0);
    }


}
class Book implements Parcelable{

    public int id;

    public Book(int id) {
        this.id = id;
    }

    private Book(Parcel in) {
        id = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
    }
}