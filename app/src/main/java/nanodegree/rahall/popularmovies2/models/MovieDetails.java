package nanodegree.rahall.popularmovies2.models;

import java.util.ArrayList;

/**
 * Created by rahall on 12/14/15.
 */
public class MovieDetails {
    private ArrayList<MovieDetail> movieDetails;

    public MovieDetails() {
        movieDetails = new ArrayList<>();
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
}
