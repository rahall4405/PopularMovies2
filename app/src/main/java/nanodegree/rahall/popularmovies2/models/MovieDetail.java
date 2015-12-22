package nanodegree.rahall.popularmovies2.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rahall on 9/6/15.
 */
public class MovieDetail implements Parcelable {
    private String revenue;
    private String runtime;
    private String homepage;
    private String release_date;

    public MovieDetail() {


    }
    MovieDetail(Parcel in) {
        revenue = in.readString();
        runtime = in.readString();
        homepage = in.readString();
        release_date = in.readString();

    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getHomePage() {
        return homepage;
    }

    public void setHomePage(String homepage) {
        this.homepage = homepage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(revenue);
        dest.writeString(runtime);
        dest.writeString(homepage);
        dest.writeString(release_date);
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
