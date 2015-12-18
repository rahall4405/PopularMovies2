package nanodegree.rahall.popularmovies2;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.stetho.Stetho;

import nanodegree.rahall.popularmovies2.models.MovieDetail;
import nanodegree.rahall.popularmovies2.models.Movies;
import nanodegree.rahall.popularmovies2.models.Reviews;
import nanodegree.rahall.popularmovies2.models.Videos;

/**
 * Created by rahall on 8/30/15.
 */
public class MovieApplication  extends Application{
    private static MovieApplication mInstance;
    private static Context mContext;
    private static Movies mMovies;
    private static MovieDetail mMovieDetail;



    private static Videos videos;
    private static Reviews reviews;


    private RequestQueue mRequestQueue;

    public static final String SHARED_PREFERENCES = "movieappprefs";
    public static final String SORT_TYPE = "sortType";
    public static final String TAG = MovieApplication.class.getSimpleName();

    public static final String REQUEST_TYPE = "requestType";

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = getApplicationContext();
        Stetho.initializeWithDefaults(this);


    }

    public static Context getAppContext() {
        return mContext;
    }

    public static synchronized MovieApplication getInstance() {
        return mInstance;

    }

    public  Movies getMovies() {
        return mMovies;
    }

    public void setMovies(Movies mMovies) {
        MovieApplication.mMovies = mMovies;
    }


    public  String getSortPreference() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);


            return prefs.getString(SORT_TYPE, "ratings");

    }

    public void setSortPreference(String sortPreference) {
        SharedPreferences.Editor editor = this.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE).edit();
        editor.putString(SORT_TYPE,sortPreference);
        editor.apply();

    }
    RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> request) {
        request.setTag(TAG);
        getRequestQueue().add(request);
    }
    public static MovieDetail getMovieDetail() {
        return mMovieDetail;
    }

    public static void setMovieDetail(MovieDetail mMovieDetail) {
        MovieApplication.mMovieDetail = mMovieDetail;
    }

    public void setRequestType(String requestType) {
        SharedPreferences.Editor editor = this.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE).edit();
        editor.putString(REQUEST_TYPE,requestType);
        editor.apply();
    }
    public String getRequestType() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);


            return prefs.getString(REQUEST_TYPE, mContext.getString(R.string.method_volley));

    }

    public void setPosition(int position) {
        SharedPreferences.Editor editor = this.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE).edit();
        editor.putInt("position", position);
        editor.apply();
    }

    public int getPosition() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);


        return prefs.getInt("position", 0);

    }

    public static Videos getVideos() {
        return videos;
    }

    public static void setVideos(Videos videos) {
        MovieApplication.videos = videos;
    }

    public static Reviews getReviews() {
        return reviews;
    }

    public static void setReviews(Reviews reviews) {
        MovieApplication.reviews = reviews;
    }
    public static String getApplicationDirectory() {
        String destination;
        try {
            destination = getAppContext().getExternalFilesDir(null).toString();
        } catch(Exception e)
        {
            e.printStackTrace();
            destination = MovieApplication.getAppContext().getFilesDir().toString();
        }


        return destination;
    }
}
