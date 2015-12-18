package nanodegree.rahall.popularmovies2.utilities;

import android.content.Context;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import nanodegree.rahall.popularmovies2.MovieApplication;
import nanodegree.rahall.popularmovies2.R;
import nanodegree.rahall.popularmovies2.models.MovieDetail;
import nanodegree.rahall.popularmovies2.models.Movies;

/**
 * Created by rahall on 8/23/15.
 */
public class VolleyNetworking {
    Context mContext;
    String id;

    public VolleyNetworking(Context context) {
        mContext = context;
        this.id = "";
    }

    public VolleyNetworking(Context context, String id) {
        mContext = context;
        this.id = id;
    }

    public JsonObjectRequest getMoviesReq(String type) {
        String url;
        if (id.equals("")) {
            if (type.equals(mContext.getString(R.string.ratings))) {
                url = HttpHelper.getSortByRatingsHttpRequest();
            } else {
                url = HttpHelper.getSortByPopularHttpRequest();

            }
        } else {

            url = HttpHelper.getMovieDetailInfo(id);
        }

        return new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    if (response.toString().contains("revenue")) {
                        MovieDetail movieDetail = new MovieDetail();
                        movieDetail = Conversion.convertJsonObjectToMovieDetailModel(response);
                        MovieApplication.getInstance().setMovieDetail(movieDetail);
                        DelegateNetworkAccess.sendDownloadCompleteMovieDetail(mContext);


                    } else {

                        JSONArray moviesJsonArray = response.getJSONArray("results");
                        Movies movies = Conversion.convertJsonArrayToMovieModelArray(moviesJsonArray);
                        MovieApplication.getInstance().setMovies(movies);
                        DelegateNetworkAccess.sendDownloadCompleteMovies(mContext);

                    }

                } catch (JSONException e) {
                    DelegateNetworkAccess.sendMovieRequestError(mContext);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }

        });

    }

    public JsonArrayRequest getMoviesReqRA(final Context context) {
        String url;
        JsonArray responseData;
        if (MovieApplication.getInstance().getSortPreference().equals("ratings")) {
            url = HttpHelper.getSortByRatingsHttpRequest();
        } else {
            url = HttpHelper.getSortByPopularHttpRequest();

        }
        return new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {


                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                            Movies movies = Conversion.convertJsonArrayToMovieModelArray(response);
                            MovieApplication.getInstance().setMovies(movies);
                            Intent intent = new Intent();

                            intent.setAction(CustomIntents.DOWNLOAD_COMPLETE);
                            context.sendBroadcast(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });


    }
}
