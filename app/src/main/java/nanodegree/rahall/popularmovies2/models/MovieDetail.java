package nanodegree.rahall.popularmovies2.models;

import java.io.Serializable;

/**
 * Created by rahall on 9/6/15.
 */
public class MovieDetail implements Serializable {
    private String revenue;
    private String runtime;
    private String homepage;
    private String release_date;


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


}
