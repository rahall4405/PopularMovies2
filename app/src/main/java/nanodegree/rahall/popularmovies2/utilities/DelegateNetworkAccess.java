package nanodegree.rahall.popularmovies2.utilities;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.JsonObjectRequest;

import nanodegree.rahall.popularmovies2.R;
import nanodegree.rahall.popularmovies2.MovieApplication;

/**
 * Created by rahall on 9/13/15.
 */
public class DelegateNetworkAccess {

    public static  void getMovies(Context context,String type) {
        if(MovieApplication.getInstance().getSortPreference().equals(context.getString(R.string.favorites))) {
            getMoviesWithAsyncTask(context,type);
        }
        else if (MovieApplication.getInstance().getRequestType().equals(context.getString(R.string.method_volley))) {

               getMoviesWithVolley(context,type);


        }
        else {
            getMoviesWithAsyncTask(context,type);
        }

    }
    public static  void getMovies(Context context,String type, String id) {
        if(MovieApplication.getInstance().getSortPreference().equals(context.getString(R.string.favorites))) {
            Conversion.convertDBtoMovieDetailModel(context,id);
        } else {
            if (MovieApplication.getInstance().getRequestType().equals(context.getString(R.string.method_volley))) {
                getMoviesWithVolley(context, type, id);
            } else if (MovieApplication.getInstance().getRequestType().equals(context.getString(R.string.method_asynctask))) {
                getMoviesWithAsyncTask(context, type, id);
            }
        }
    }

    public static void getMoviesWithVolley(Context context,String type) {

        VolleyNetworking volleyNetworking = new VolleyNetworking(context.getApplicationContext());

        JsonObjectRequest request = volleyNetworking.getMoviesReq(type);
        MovieApplication.getInstance().addToRequestQueue(request);
    }

    public static void getMoviesWithVolley(Context context,String type, String id) {

        VolleyNetworking volleyNetworking = new VolleyNetworking(context.getApplicationContext(),id);

        JsonObjectRequest request = volleyNetworking.getMoviesReq(context.getString(R.string.movie_detail));
        MovieApplication.getInstance().addToRequestQueue(request);
    }
    public static void getMoviesWithAsyncTask(Context context,String type) {
       AsyncJsonRequest asyncJsonRequest = new AsyncJsonRequest(type,context);
        asyncJsonRequest.execute();
    }
    public static void getMoviesWithAsyncTask(Context context,String type, String id) {
        AsyncJsonRequest asyncJsonRequest = new AsyncJsonRequest(context.getString(R.string.movie_detail),context,id);
        asyncJsonRequest.execute();
    }


    public static void sendDownloadCompleteMovies(Context context) {
        Intent intent = new Intent();
        intent.setAction(CustomIntents.DOWNLOAD_COMPLETE);
        context.sendBroadcast(intent);
    }
    public static void sendDownloadCompleteMovieDetail(Context context) {
        Intent intent = new Intent();
        intent.setAction(CustomIntents.DOWNLOAD_DETAIL_COMPLETE);
        context.sendBroadcast(intent);
    }
    public static void sendMovieRequestError(Context context) {
        Intent intent = new Intent();
        intent.setAction(CustomIntents.DOWNLOAD_ERROR);
        context.sendBroadcast(intent);
    }


}
