package nanodegree.rahall.popularmovies2.databasemanager;

import android.content.Context;

import nanodegree.rahall.popularmovies2.MovieApplication;
import nanodegree.rahall.popularmovies2.models.Movies;
import nanodegree.rahall.popularmovies2.utilities.DelegateNetworkAccess;
import nanodegree.rahall.popularmovies2.utilities.Utilities;

/**
 * Created by rahall on 12/14/15.
 */
public class GetMoviesWithDB {
    public  GetMoviesWithDB(Context context) {
        //DatabaseManager dm = new DatabaseManager(context);
        Movies movies = new Movies();
        movies = Utilities.getMoviesCR(context);
       // MovieApplication.getInstance().getMovies().clearAll();
        MovieApplication.getInstance().setMovies(movies);

        DelegateNetworkAccess.sendDownloadCompleteMovies(context);


    }
}
