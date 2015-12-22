package nanodegree.rahall.popularmovies2.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by rahall on 11/28/15.
 */
public class Reviews implements Parcelable{

    private ArrayList<Review> reviews;
    public Reviews() {
        reviews = new ArrayList<>();
    }
    public Reviews(Parcel in) {
        reviews = in.readArrayList(Review.class.getClassLoader());
    }
    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }
    public void addReview(Review review) {
        reviews.add(review);
    }

    public void deleteReview(Review review) {
        reviews.remove(review);
    }

    public Review getReview(int i) {
        return reviews.get(i);
    }

    public int getSize() {
        return reviews.size();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(reviews);
    }

    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        public Review[] newArray(int size) {
            return new Review[size];
        }


    };


}
