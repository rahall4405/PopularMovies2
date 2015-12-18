package nanodegree.rahall.popularmovies2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import nanodegree.rahall.popularmovies2.R;
import nanodegree.rahall.popularmovies2.MovieApplication;

public class ErrorHandlerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_handler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_error_handler, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public void tryAgain(View view) {
        Intent intent = new Intent(this,PopularMovies.class);
        this.startActivity(intent);
        finish();
    }
    public void loadFavorites(View view) {
        MovieApplication.getInstance().setSortPreference(getString(R.string.favorites));
        Intent intent = new Intent(this,PopularMovies.class);
        this.startActivity(intent);
        finish();
    }
}
