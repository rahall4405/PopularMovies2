package nanodegree.rahall.popularmovies2.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rahall on 8/23/15.
 */
public class Movie implements Parcelable {
    private String backdrop_path;
    private String id;
    private String original_language;
    private String title;
    private String release_date;
    private String poster_path;
    private String overview;
    private String vote_average;
    private String vote_count;

    public Movie() {
    }

    public Movie(String backdrop_path, String id, String original_language, String title, String release_date, String poster_path, String overview, String vote_average, String vote_count) {
        this.backdrop_path = backdrop_path;
        this.id = id;
        this.original_language = original_language;
        this.title = title;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.overview = overview;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    public Movie(Parcel in) {
        backdrop_path = in.readString();
        id = in.readString();
        title = in.readString();
        release_date = in.readString();
        poster_path = in.readString();
        overview = in.readString();
        vote_average = in.readString();
        vote_count = in.readString();


    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public void setOriginal_language(String oriiginal_language) {
        this.original_language = oriiginal_language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(backdrop_path);
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(release_date);
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeString(vote_average);
        dest.writeString(vote_count);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }


    };


    @Override
    public String toString() {
        return "Movie{" +
                "backdrop_path='" + backdrop_path + '\'' +
                ", id='" + id + '\'' +
                ", original_language='" + original_language + '\'' +
                ", title='" + title + '\'' +
                ", release_date='" + release_date + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", overview='" + overview + '\'' +
                ", vote_average='" + vote_average + '\'' +
                ", vote_count='" + vote_count + '\'' +
                '}';
    }
}
