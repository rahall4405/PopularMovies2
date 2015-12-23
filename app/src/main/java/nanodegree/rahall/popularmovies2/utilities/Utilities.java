package nanodegree.rahall.popularmovies2.utilities;

import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.File;

import nanodegree.rahall.popularmovies2.MovieApplication;
import nanodegree.rahall.popularmovies2.R;
import nanodegree.rahall.popularmovies2.contentprovider.MovieProvider;
import nanodegree.rahall.popularmovies2.models.Movie;
import nanodegree.rahall.popularmovies2.models.MovieDetail;
import nanodegree.rahall.popularmovies2.models.Movies;
import nanodegree.rahall.popularmovies2.models.Review;
import nanodegree.rahall.popularmovies2.models.Reviews;
import nanodegree.rahall.popularmovies2.models.Video;
import nanodegree.rahall.popularmovies2.models.Videos;

/**
 * Created by rahall on 11/26/15.
 */
public class Utilities {
    private static final String REVENUE = "revenue";
    private static final String RUNTIME = "runtime";
    private static final String HOMEPAGE = "homepage";
    private static final String VIDEO_ID = "id";
    private static final String VIDEO_KEY = "video_key";
    private static final String VIDEO_NAME = "video_name";
    private static final String REVIEW_ID = "id";
    private static final String REVIEW_AUTHOR = "review_author";
    private static final String REVIEW_CONTENT = "review_content";
    private static final String MOVIE_ID = "id";
    private static final String TITLE = "title";
    private static final String POSTER_PATH = "poster_path";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String VOTE_COUNT = "vote_count";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


    public static void getMovies(Context context) {
        DelegateNetworkAccess.getMovies(context, MovieApplication.getInstance().getSortPreference());

    }

    public static void gotoLink(Context context,String url) {


        Uri webPageUri = Uri.parse(url);

        Intent intent = new Intent(Intent.ACTION_VIEW, webPageUri);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    private static final int UNBOUNDED = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

    // To calculate the total height of all items in ListView call with items = adapter.getCount()
    public static int getHeightofListView(ListView listView, int size, Context context) {
        ListAdapter adapter = listView.getAdapter();

        int totalHeight = 0;
        for (int i = 0; i < size; i++) {
            View childView = adapter.getView(i, null, listView);
            childView.measure(UNBOUNDED, UNBOUNDED);
            totalHeight += childView.getMeasuredHeight();
        }
        return dpToPx(totalHeight, context);
    }

    public static int pxToDp(int px, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public static int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static void downloadImageFile(Context context, String file) {

        String urlString = HttpHelper.getImageSize185HttpRequest(file);
        DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(urlString));
        if (MovieApplication.getApplicationDirectory() != null) {
            File pathDir = new File(MovieApplication.getApplicationDirectory() + "/image_files");
            pathDir.mkdirs();
            int postionLastSlash = urlString.lastIndexOf('/');
            int postitionfileType = urlString.length() - 3;
            String fileExtension = urlString.substring(postitionfileType, urlString.length());
            String fileName = urlString.substring(postionLastSlash, urlString.length());
            String outputFile = "/" + "/image_files" + fileName;
            request.setDestinationInExternalFilesDir(context, "", outputFile);
            long enqueue = dm.enqueue(request);
        }
    }

    public static void deleteImageFile(String file) {
        if(MovieApplication.getApplicationDirectory() != null) {
            File imageFile = new File(MovieApplication.getApplicationDirectory() + "/image_files" + file);
            imageFile.delete();
        }
    }

    public static void sendShareIntent(Bundle b, Context context,String videoString) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        String urlString = "https://www.youtube.com/watch?v=";
        String videoLink = urlString + videoString;
        sharingIntent.setType("text/plain");

        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, context.getString(R.string.trailer_for) + b.getString("title"));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, videoLink);
        context.startActivity(Intent.createChooser(sharingIntent, "Share first trailer using"));
    }
    public static MovieDetail getMovieDetail(Context context, String id) {
        MovieDetail movieDetail = null;
        ContentResolver cr = context.getContentResolver();
        String selection = id;
        Uri uri = MovieProvider.CONTENT_URI;
        Uri detailUri = ContentUris.withAppendedId(uri,2);


        Cursor cursor = cr.query(MovieProvider.CONTENT_URI,null,selection,null,null);
        cursor.moveToFirst();
        if (cursor != null) {
            movieDetail = setMovieDetail(cursor);

        }
        return movieDetail;

    }
    public static Videos getVideos(Context context, String id) {
        ContentResolver cr = context.getContentResolver();
        Videos videos = new Videos();
        String selection = id;
        Uri uri = MovieProvider.VIDEO_URI;
        //Uri videoUri = ContentUris.withAppendedId(uri,4);
        Cursor cursor = cr.query(uri, null, selection, null, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                Video video = new Video();
                video = setVideo(cursor);
                videos.addVideo(video);


            } while (cursor.moveToNext());
        }
            return videos;
    }

    public static Reviews getReviews(Context context, String id) {
        ContentResolver cr = context.getContentResolver();
        Reviews reviews = new Reviews();
        String selection = id;
        Uri uri = MovieProvider.REVIEWS_URI;

        Cursor cursor = cr.query(uri, null, selection, null, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                Review review = new Review();
                review = setReview(cursor);
                reviews.addReview(review);


            } while (cursor.moveToNext());
        }
        return reviews;
    }

    public static Movies getMoviesCR(Context context) {
        ContentResolver cr = context.getContentResolver();
        Movies movies = new Movies();

        Uri uri = MovieProvider.MOVIES_URI;
        Cursor cursor = cr.query(uri, null, null, null, null);
        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {
                Movie movie = new Movie();
                movie = setMovieValues(cursor);
                movies.addMovie(movie);


            } while (cursor.moveToNext());
        }

        return movies;
    }


    public static Movie setMovieValues(Cursor cursor) {
        Movie movie = new Movie();
        movie.setId(cursor.getString(cursor
                .getColumnIndexOrThrow(MOVIE_ID)));
        movie.setTitle(cursor.getString(cursor
                .getColumnIndexOrThrow(TITLE)));
        movie.setPoster_path(cursor.getString(cursor
                .getColumnIndexOrThrow(POSTER_PATH)));
        movie.setVote_average(cursor.getString(cursor
                .getColumnIndexOrThrow(VOTE_AVERAGE)));
        movie.setVote_count(cursor.getString(cursor
                .getColumnIndexOrThrow(VOTE_COUNT)));
        movie.setOverview(cursor.getString(cursor
                .getColumnIndexOrThrow(OVERVIEW)));
        movie.setRelease_date(cursor.getString(cursor
                .getColumnIndexOrThrow(RELEASE_DATE)));


        return movie;
    }

    public static Review setReview(Cursor cursor) {
        Review review = new Review();
        review.setId(cursor.getString(cursor
                .getColumnIndexOrThrow(REVIEW_ID)));
        review.setAuthor(cursor.getString(cursor
                .getColumnIndexOrThrow(REVIEW_AUTHOR)));
        review.setContent(cursor.getString(cursor
                .getColumnIndexOrThrow(REVIEW_CONTENT)));
        return review;
    }


    public static Video setVideo(Cursor cursor) {
        Video video = new Video();
        video.setId(cursor.getString(cursor
                .getColumnIndexOrThrow(VIDEO_ID)));
        video.setKey(cursor.getString(cursor
                .getColumnIndexOrThrow(VIDEO_KEY)));
        video.setName(cursor.getString(cursor
                .getColumnIndexOrThrow(VIDEO_NAME)));
        return video;

    }


    public static MovieDetail setMovieDetail(Cursor cursor) {

        MovieDetail movieDetail = new MovieDetail();
        movieDetail.setHomePage(cursor.getString(cursor
                .getColumnIndexOrThrow(HOMEPAGE)));
        movieDetail.setRevenue(cursor.getString(cursor
                .getColumnIndexOrThrow(REVENUE)));
        movieDetail.setRuntime(cursor.getString(cursor
                .getColumnIndexOrThrow(RUNTIME)));
        return movieDetail;
    }


}
