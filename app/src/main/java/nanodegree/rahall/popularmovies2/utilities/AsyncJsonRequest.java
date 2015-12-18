package nanodegree.rahall.popularmovies2.utilities;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import nanodegree.rahall.popularmovies2.MovieApplication;
import nanodegree.rahall.popularmovies2.R;
import nanodegree.rahall.popularmovies2.databasemanager.GetMoviesWithDB;
import nanodegree.rahall.popularmovies2.models.MovieDetail;
import nanodegree.rahall.popularmovies2.models.Movies;

/**
 * Created by rahall on 9/13/15.
 */
public class AsyncJsonRequest extends AsyncTask<String,Void,Void> {
private String mType;
    private String mId;
    boolean returnDataOk;
    Context mContext;
AsyncJsonRequest(String type,Context context) {
    this.mType = type;
    this.mId = "";
    returnDataOk = true;
    this.mContext = context;


}

    AsyncJsonRequest(String type,Context context,String id) {
        this.mType = type;
        this.mId = id;
        returnDataOk = true;
        this.mContext = context;


    }

    @Override
    protected Void doInBackground(String... params) {
        if (MovieApplication.getInstance().getSortPreference().equals(mContext.getString(R.string.favorites))) {
            GetMoviesWithDB getMoviesWithDB = new GetMoviesWithDB(mContext);
        } else {
            String urlString;
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String movieString = "";


            if (mId.equals("")) {
                if (mType.equals(mContext.getString(R.string.ratings))) {
                    urlString = HttpHelper.getSortByRatingsHttpRequest();
                } else {
                    urlString = HttpHelper.getSortByPopularHttpRequest();

                }
            } else {

                urlString = HttpHelper.getMovieDetailInfo(mId);
            }

            try {
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    movieString = null;
                    returnDataOk = false;

                } else {
                    movieString = buffer.toString();
                    JSONObject jsonObject = new JSONObject(movieString);
                    if (jsonObject.toString().contains("revenue")) {
                        MovieDetail movieDetail = new MovieDetail();
                        movieDetail = Conversion.convertJsonObjectToMovieDetailModel(jsonObject);
                        MovieApplication.getInstance().setMovieDetail(movieDetail);
                        DelegateNetworkAccess.sendDownloadCompleteMovieDetail(mContext);
                    } else {
                        JSONArray moviesJsonArray = jsonObject.getJSONArray("results");
                        Movies movies = Conversion.convertJsonArrayToMovieModelArray(moviesJsonArray);
                        MovieApplication.getInstance().setMovies(movies);
                        DelegateNetworkAccess.sendDownloadCompleteMovies(mContext);
                    }

                }


            } catch (MalformedURLException e) {
                returnDataOk = false;

                e.printStackTrace();
            } catch (IOException e) {
                returnDataOk = false;
                e.printStackTrace();
            } catch (JSONException e) {
                returnDataOk = false;
                e.printStackTrace();
            } finally {
                if (!returnDataOk) {
                    DelegateNetworkAccess.sendMovieRequestError(mContext);
                }
            }


        }
        return null;
    }
}
