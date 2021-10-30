package com.example.tubesp3b;
import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String title;
    private String synopsis;
    private String poster;
    private String status;
    private float rating;
    private String review;

    public Movie(String title, String synopsis, String poster, String status, float rating, String review){
        this.title = title;
        this.synopsis = synopsis;
        this.poster = poster;
        this.status = status;
        this.rating = rating;
        this.review = review;
    }

    protected Movie(Parcel in) {
        title = in.readString();
        synopsis = in.readString();
        poster = in.readString();
        status = in.readString();
        rating = in.readFloat();
        review = in.readString();
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
    public void setPoster(String poster){
        this.poster = poster;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public void setRating(float status){
        this.rating = rating;
    }
    public void setReview(String review){
        this.review = review;
    }
    public String getTitle(){
        return this.title;
    }
    public String getSynopsis(){
        return this.synopsis;
    }
    public String getPoster(){
        return this.poster;
    }
    public String getStatus(){
        return this.status;
    }
    public float getRating(){
        return this.rating;
    }
    public String getReview(){
        return this.review;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(synopsis);
        parcel.writeString(poster);
        parcel.writeString(status);
        parcel.writeFloat(rating);
        parcel.writeString(review);
    }
}
