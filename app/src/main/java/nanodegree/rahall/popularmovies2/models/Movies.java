package nanodegree.rahall.popularmovies2.models;

import java.util.ArrayList;

/**
 * Created by rahall on 8/23/15.
 */
public class Movies {
    private ArrayList<Movie> movies;

    public Movies() {
        movies = new ArrayList<>();
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
}
