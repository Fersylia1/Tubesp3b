package com.example.tubesp3b;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String title;
    private String synopsis;

    public Movie(String title, String synopsis){
        this.title = title;
        this.synopsis = synopsis;
    }

    protected Movie(Parcel in) {
        title = in.readString();
        synopsis = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public void setTitle(String title){
        this.title = title;
    }
    public void setSynopsis(String synopsis){
        this.synopsis = synopsis;
    }
    public String getTitle(){
        return this.title;
    }
    public String getSynopsis(){
        return this.synopsis;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(synopsis);
    }
}
