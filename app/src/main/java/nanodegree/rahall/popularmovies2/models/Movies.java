package nanodegree.rahall.popularmovies2.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by rahall on 8/23/15.
 */
public class Movies  implements Parcelable {
    private ArrayList<Movie> movies;

    public Movies() {
        movies = new ArrayList<>();
    }

    public Movies(ArrayList<Movie> movies) {
        this.movies = movies;
    }
    public  Movies(Parcel in) {

        movies = in.readArrayList(Movie.class.getClassLoader());
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }
    public void addMovie(Movie movie) {
        movies.add(movie);
    }
    public int getSize() {
        return movies.size();
    }

    public void deleteMovie(Movie movie) {
        movies.remove(movie);
    }

    public Movie getMovie(int i) {
        return movies.get(i);
    }
    public void clearAll() {
        movies.clear();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(movies);

    }



    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }


    };
}
