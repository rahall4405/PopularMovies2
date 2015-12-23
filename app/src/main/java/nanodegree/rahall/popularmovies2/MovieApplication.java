package nanodegree.rahall.popularmovies2;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.stetho.Stetho;

/**
 * Created by rahall on 8/30/15.
 */
public class MovieApplication  extends Application{
    private static MovieApplication mInstance;
    private static Context mContext;



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


    public static String getApplicationDirectory() {
        String destination;

        boolean isSdPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if(isSdPresent) {

            return getAppContext().getExternalFilesDir(null).toString();
        } else {
            return null;
        }


    }
}
