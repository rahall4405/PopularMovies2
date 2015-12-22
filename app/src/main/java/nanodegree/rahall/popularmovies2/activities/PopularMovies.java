package nanodegree.rahall.popularmovies2.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import nanodegree.rahall.popularmovies2.R;
import nanodegree.rahall.popularmovies2.MovieApplication;
import nanodegree.rahall.popularmovies2.adaptors.MovieAdapter;
import nanodegree.rahall.popularmovies2.fragments.MovieDetailsFragment;
import nanodegree.rahall.popularmovies2.fragments.PopularMoviesFragment;
import nanodegree.rahall.popularmovies2.utilities.Utilities;


public class PopularMovies extends AppCompatActivity implements PopularMoviesFragment.OnFragmentInteractionListener {
    RecyclerView mMovieRecylcerView;
    MovieAdapter mMovieAdapter;
    Context mContext;
    MenuItem mSendTrailor;
    Bundle b;
    String siteLink;
    String videoLink;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);
        mContext = this;

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_popular_movies, menu);
        mSendTrailor = menu.findItem(R.id.send_trailer);
        if (!Utilities.isTablet(mContext)) {

            mSendTrailor.setVisible(false);

        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        mSendTrailor = menu.findItem(R.id.send_trailer);
        if (!Utilities.isTablet(mContext)) {

            mSendTrailor.setVisible(false);

        }

        if (MovieApplication.getInstance().getSortPreference().equals(getString(R.string.popularity))) {
            menu.findItem(R.id.action_sort_by_popularity).setChecked(true);

        } else if (MovieApplication.getInstance().getSortPreference().equals(getString(R.string.ratings))) {
            menu.findItem(R.id.action_sort_by_rating).setChecked(true);
        } else {
            menu.findItem(R.id.action_myfavorites).setChecked(true);

        }

        if (MovieApplication.getInstance().getRequestType().equals(getString(R.string.method_asynctask))) {
            menu.findItem(R.id.use_asyntask).setChecked(true);
        } else {
            menu.findItem(R.id.use_volley).setChecked(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        switch (item.getItemId()) {
            case R.id.action_sort_by_popularity:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                    if (!MovieApplication.getInstance().getSortPreference().equals(getString(R.string.popularity))) {
                        MovieApplication.getInstance().setSortPreference(getString(R.string.popularity));
                        Utilities.getMovies(this);
                    }


                }


                return true;
            case R.id.action_sort_by_rating:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                    if (!MovieApplication.getInstance().getSortPreference().equals(getString(R.string.ratings))) {
                        MovieApplication.getInstance().setSortPreference(getString(R.string.ratings));
                        Utilities.getMovies(this);

                    }
                }
                return true;

            case R.id.action_myfavorites:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                    if (!MovieApplication.getInstance().getSortPreference().equals(getString(R.string.favorites))) {
                        MovieApplication.getInstance().setSortPreference(getString(R.string.favorites));
                        // MovieApplication.getInstance().setRequestType(getString(R.string.favorites));
                        Utilities.getMovies(this);

                    }
                }
                return true;

            case R.id.use_volley:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                    if (!MovieApplication.getInstance().getRequestType().equals(getString(R.string.method_volley))) {
                        MovieApplication.getInstance().setRequestType(getString(R.string.method_volley));
                    }


                }


                return true;
            case R.id.use_asyntask:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                    if (!MovieApplication.getInstance().getRequestType().equals(getString(R.string.method_asynctask))) {
                        MovieApplication.getInstance().setRequestType(getString(R.string.method_asynctask));


                    }
                }
                return true;
                case R.id.send_trailer:
            {

                Utilities.sendShareIntent(b,mContext,videoLink);



            }
                return true;


            default:
                return super.onOptionsItemSelected(item);

        }


    }




    @Override
    public void onFragmentInteractionListener(Bundle b) {

        MovieDetailsFragment movieDetailsFragment = (MovieDetailsFragment) getFragmentManager().findFragmentById(R.id.moviedetails_fragment);
        movieDetailsFragment.dispPick(b);
        this.b = b;

    }

    public void gotoLink(View view) {
        Utilities.gotoLink(mContext,siteLink);
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();


    }
    public void setRequiredLinks(String siteLink, String videoLink) {
        this.siteLink = siteLink;
        this.videoLink = videoLink;
    }

}
