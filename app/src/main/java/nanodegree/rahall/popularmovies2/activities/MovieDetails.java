package nanodegree.rahall.popularmovies2.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import nanodegree.rahall.popularmovies2.R;
import nanodegree.rahall.popularmovies2.utilities.Utilities;

public class MovieDetails extends AppCompatActivity {

    Context mContext;
    MenuItem mSendTrailor;
    String siteLink;
    String videoLink;

     static  Bundle mExtras = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (Utilities.isTablet(this)) {
            finish();
            return;
        }
        mContext = this;
        mExtras = getIntent().getExtras();
      setContentView(R.layout.activity_movie_detail);

    }
    public static Bundle getExtras() {
        return mExtras;
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
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);

        mSendTrailor = menu.findItem(R.id.send_trailer);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.send_trailer) {


            Utilities.sendShareIntent(getExtras(),mContext,videoLink);
        }

        return super.onOptionsItemSelected(item);
    }



    public void gotoLink(View view) {
        Utilities.gotoLink(mContext,siteLink);
    }

    public void setRequiredLinks(String siteLink, String videoLink) {
        this.siteLink = siteLink;
        this.videoLink = videoLink;
    }





}
