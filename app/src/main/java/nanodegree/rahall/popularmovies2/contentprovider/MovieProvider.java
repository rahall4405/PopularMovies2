package nanodegree.rahall.popularmovies2.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import nanodegree.rahall.popularmovies2.databasemanager.DatabaseManager;

/**
 * Created by rahall on 12/17/15.
 */
public class MovieProvider extends ContentProvider {
    DatabaseManager dm;
    private static final String AUTHORITY ="nanodegree.rahall.popularmovies2.contentprovider.MovieProvider";
    private static final String DETAILS = "details";
    private static final String REVIEWS_REQUEST="reviews";
    private static final String MOVIES_REQUEST="movies";
    private static final String VIDEO_REQUEST = "videos";
    private static final int MOVIE_QUERY = 1;
    private static final int DETAIL_QUERY = 2;
    private static final int REVIEWS = 3;
    private static final int VIDEOS =4;



    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + DETAILS);

    public static final Uri MOVIES_URI =
            Uri.parse("content://" + AUTHORITY + "/" + MOVIES_REQUEST);
    public static final Uri VIDEO_URI =
            Uri.parse("content://" + AUTHORITY + "/" + VIDEO_REQUEST);
    public static final Uri REVIEWS_URI =
            Uri.parse("content://" + AUTHORITY + "/" + REVIEWS_REQUEST);
    private static final UriMatcher URIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        URIMatcher.addURI(AUTHORITY,MOVIES_REQUEST, MOVIE_QUERY);
        URIMatcher.addURI(AUTHORITY,DETAILS, DETAIL_QUERY);
        URIMatcher.addURI(AUTHORITY,REVIEWS_REQUEST ,REVIEWS);
        URIMatcher.addURI(AUTHORITY,VIDEO_REQUEST,VIDEOS);
    }
    @Override
    public boolean onCreate() {

        dm = new DatabaseManager(getContext());


        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int uriType = URIMatcher.match(uri);
        Cursor cursor;
        switch(uriType) {
            case MOVIE_QUERY:
                cursor = dm.getAllMoviesCursor();
                break;
            case DETAIL_QUERY:
                cursor = dm.getMovieDetailCursor(selection);
                break;
            case VIDEOS:
                cursor = dm.getMovieVideosCursor(selection);
                break;
            case REVIEWS:
                cursor = dm.getMovieReviewsCursor(selection);
                break;


            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
