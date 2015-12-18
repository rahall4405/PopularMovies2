package nanodegree.rahall.popularmovies2.utilities;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import nanodegree.rahall.popularmovies2.MovieApplication;
import nanodegree.rahall.popularmovies2.databasemanager.DatabaseManager;
import nanodegree.rahall.popularmovies2.models.Movie;
import nanodegree.rahall.popularmovies2.models.MovieDetail;
import nanodegree.rahall.popularmovies2.models.Movies;
import nanodegree.rahall.popularmovies2.models.Review;
import nanodegree.rahall.popularmovies2.models.Reviews;
import nanodegree.rahall.popularmovies2.models.Video;
import nanodegree.rahall.popularmovies2.models.Videos;

/**
 * Created by rahall on 8/23/15.
 */
public class Conversion {
    private static final String REVENUE = "revenue";
    private static final String RUNTIME = "runtime";
    private static final String HOMEPAGE = "homepage";

    public static Movies convertJsonArrayToMovieModelArray(JSONArray jsonArrayMovies) throws org.json.JSONException {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Movies movies = new Movies();
        for (int i = 0; i < jsonArrayMovies.length(); i++) {
            JSONObject jsonMovie = jsonArrayMovies.getJSONObject(i);
            Movie movie = gson.fromJson(String.valueOf(jsonMovie),Movie.class);
            movies.addMovie(movie);
        }
        return movies;

    }

    public static MovieDetail convertJsonObjectToMovieDetailModel(JSONObject jsonMovieDetail) throws org.json.JSONException {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        JSONObject jsonObjectVideos = jsonMovieDetail.getJSONObject("videos");
        JSONArray jsonArrayVideos = jsonObjectVideos.getJSONArray("results");
        Videos videos = new Videos();
        for (int i = 0; i < jsonArrayVideos.length(); i++) {
            JSONObject jsonVideo = jsonArrayVideos.getJSONObject(i);
            Video video = gson.fromJson(String.valueOf(jsonVideo), Video.class);
            videos.addVideo(video);
        }
        MovieApplication.getInstance().setVideos(videos);
        JSONObject jsonObjectReviews = jsonMovieDetail.getJSONObject("reviews");
        JSONArray jsonArrayReviews = jsonObjectReviews.getJSONArray("results");
        Reviews reviews = new Reviews();
        for (int i = 0; i < jsonArrayReviews.length(); i++) {
            JSONObject jsonReview = jsonArrayReviews.getJSONObject(i);
            Review review = gson.fromJson(String.valueOf(jsonReview), Review.class);
            reviews.addReview(review);
        }
        MovieApplication.getInstance().setReviews(reviews);

        MovieDetail movieDetail = gson.fromJson(String.valueOf(jsonMovieDetail),MovieDetail.class);
        return movieDetail;

    }
    public static void convertDBtoMovieDetailModel(Context context, String id) {

        DatabaseManager dm = new DatabaseManager(context);
        MovieDetail movieDetail = null;
        //movieDetail = dm.getMovieDetailFromDb(id);
        // changed to content resolver
        movieDetail =Utilities.getMovieDetail(context,id);
        Videos videos = new Videos();
        //videos = dm.getMovieVideosFromDb(id);
        // changed to content resolver
        videos = Utilities.getVideos(context,id);
        Reviews reviews = new Reviews();
        //reviews = dm.getMovieReviewsFromDb(id);
        // changed to content resolver
        reviews = Utilities.getReviews(context,id);
        MovieApplication.getInstance().setVideos(videos);
        MovieApplication.getInstance().setReviews(reviews);


        MovieApplication.getInstance().setMovieDetail(movieDetail);
        DelegateNetworkAccess.sendDownloadCompleteMovieDetail(context);
    }


}
