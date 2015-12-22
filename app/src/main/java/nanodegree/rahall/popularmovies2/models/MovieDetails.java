package nanodegree.rahall.popularmovies2.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by rahall on 12/14/15.
 */
public class MovieDetails implements Parcelable{
    private ArrayList<MovieDetail> movieDetails;

    public MovieDetails() {
        movieDetails = new ArrayList<>();
    }

    public MovieDetails(Parcel in) {
        movieDetails = in.readArrayList(MovieDetail.class.getClassLoader());
    }
    public void addMovieDetail(MovieDetail movieDetail) {
        movieDetails.add(movieDetail);
    }
    public MovieDetail getMovieDetail(int i) {
        return movieDetails.get(i);
    }

    public ArrayList<MovieDetail> getMovieDetails() {
        return movieDetails;
    }

    public void setMovieDetails(ArrayList<MovieDetail> movieDetails) {
        this.movieDetails = movieDetails;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(movieDetails);

    }
    public static final Parcelable.Creator<MovieDetail> CREATOR = new Parcelable.Creator<MovieDetail>() {
        public MovieDetail createFromParcel(Parcel in) {
            return new MovieDetail(in);
        }

        public MovieDetail[] newArray(int size) {
            return new MovieDetail[size];
        }


    };

}
