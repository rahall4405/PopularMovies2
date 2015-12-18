package nanodegree.rahall.popularmovies2.databasemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import nanodegree.rahall.popularmovies2.R;
import nanodegree.rahall.popularmovies2.MovieApplication;
import nanodegree.rahall.popularmovies2.models.Movie;
import nanodegree.rahall.popularmovies2.models.MovieDetail;
import nanodegree.rahall.popularmovies2.models.MovieDetails;
import nanodegree.rahall.popularmovies2.models.Movies;
import nanodegree.rahall.popularmovies2.models.Review;
import nanodegree.rahall.popularmovies2.models.Reviews;
import nanodegree.rahall.popularmovies2.models.Video;
import nanodegree.rahall.popularmovies2.models.Videos;
import nanodegree.rahall.popularmovies2.utilities.Utilities;

/**
 * Created by rahall on 12/10/15.
 */
public class DatabaseManager {
    private SQLiteDatabase db;
    private Context context;
    private static final String DB_NAME = "movies";
    private static final int VERSION = 1;
    private static final String MOVIES_TABLE_NAME = "movies_table";
    private static final String REVIEWS_TABLE_NAME = "reviews_table";
    private static final String VIDEOS_TABLE_NAME = "videos_table";

    // columns in the table
    private static final String MOVIE_ID = "id";
    private static final String POSTER_PATH = "poster_path";
    private static final String TITLE = "title";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String VOTE_COUNT = "vote_count";
    private static final String OVERVIEW = "overview";
    private static final String REVENUE = "revenue";
    private static final String RUNTIME = "runtime";
    private static final String HOMEPAGE = "homepage";
    private static final String RELEASE_DATE = "release_date";
    private static final String REVIEW_ID = "id";
    private static final String REVIEW_MOVIE_ID = "review_id";
    private static final String REVIEW_AUTHOR = "review_author";
    private static final String REVIEW_CONTENT = "review_content";
    private static final String VIDEO_ID = "id";
    private static final String VIDEO_MOVIE_ID = "video_id";
    private static final String VIDEO_KEY = "video_key";
    private static final String VIDEO_NAME = "video_name";


    public DatabaseManager(Context context) {
        this.context = context;

        // create or open the database
        DBHelper helper = new DBHelper(context);
        this.db = helper.getWritableDatabase();

    }


    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DB_NAME, null, VERSION);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
            if (!db.isReadOnly()) {
                // Enable foreign key constraints
                db.execSQL("PRAGMA foreign_keys=ON;");
            }
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String MovieTableCreateQuery = "create table " + MOVIES_TABLE_NAME + " ("
                    + MOVIE_ID + " primary_key text not null,"
                    + TITLE + " text not null,"
                    + POSTER_PATH + " text not null,"
                    + VOTE_AVERAGE + " text,"
                    + VOTE_COUNT + " text,"
                    + OVERVIEW + " text,"
                    + REVENUE + " text,"
                    + RUNTIME + " text,"
                    + HOMEPAGE + " text,"
                    + RELEASE_DATE + " text" + ")";
            db.execSQL(MovieTableCreateQuery);

            String ReviewTableQueryCreate = "create table " + REVIEWS_TABLE_NAME + " ("
                    + REVIEW_ID + " primary_key text not null,"
                    + REVIEW_AUTHOR + " text not null,"
                    + REVIEW_CONTENT + " text not null,"
                    + REVIEW_MOVIE_ID + " text not null)";

            db.execSQL(ReviewTableQueryCreate);
            String VideoTableQueryCreate = "create table " + VIDEOS_TABLE_NAME + " ("
                    + VIDEO_ID + " primary_key text not null,"
                    + VIDEO_KEY + " text not null,"
                    + VIDEO_NAME + " text not null,"
                    + VIDEO_MOVIE_ID + " text not null)";

            db.execSQL(VideoTableQueryCreate);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            // LATER, WE WOULD SPECIFIY HOW TO UPGRADE THE DATABASE
            // FROM OLDER VERSIONS.
            String DROP_MOVIE_TABLE = "DROP TABLE IF EXISTS " + MOVIES_TABLE_NAME;
            db.execSQL(DROP_MOVIE_TABLE);
            String DROP_REVIEW_TABLE = "DROP TABLE IF EXISTS " + REVIEWS_TABLE_NAME;
            db.execSQL(DROP_REVIEW_TABLE);
            String DROP_VIDEO_TABLE = "DROP TABLE IF EXISTS " + VIDEOS_TABLE_NAME;
            db.execSQL(DROP_VIDEO_TABLE);
            onCreate(db);

        }


    }

    public void addEntry(Context context, Bundle b, Reviews reviews, Videos videos) {
        MovieDetail movieDetail = MovieApplication.getInstance().getMovieDetail();
        ContentValues values = new ContentValues();

        values.put(MOVIE_ID, b.getString(context.getString(R.string.id)));
        values.put(TITLE, b.getString(context.getString(R.string.title)));
        values.put(RELEASE_DATE, b.getString(context.getString(R.string.release_date)));
        values.put(VOTE_AVERAGE, b.getString(context.getString(R.string.vote_average)));
        values.put(VOTE_COUNT, b.getString(context.getString(R.string.vote_count)));
        values.put(OVERVIEW, b.getString(context.getString(R.string.overview)));
        values.put(POSTER_PATH, b.getString(context.getString(R.string.poster_image)));
        if (movieDetail != null) {
            values.put(RUNTIME, movieDetail.getRuntime());
            values.put(REVENUE, movieDetail.getRevenue());
            values.put(HOMEPAGE, movieDetail.getHomePage());
        }
        db.insert(MOVIES_TABLE_NAME, null, values);

        if (reviews != null) {
            for (int i = 0; i < reviews.getSize(); i++) {
                ContentValues valuesReviews = new ContentValues();
                valuesReviews.put(REVIEW_ID, reviews.getReview(i).getId());
                valuesReviews.put(REVIEW_AUTHOR, reviews.getReview(i).getAuthor());
                valuesReviews.put(REVIEW_CONTENT, reviews.getReview(i).getContent());
                valuesReviews.put(REVIEW_MOVIE_ID, b.getString(context.getString(R.string.id)));
                try {
                    db.insert(REVIEWS_TABLE_NAME, null, valuesReviews);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

        if (videos != null) {
            for (int i = 0; i < videos.getSize(); i++) {
                ContentValues valuesVideos = new ContentValues();
                valuesVideos.put(VIDEO_ID, videos.getVideo(i).getId());
                valuesVideos.put(VIDEO_KEY, videos.getVideo(i).getKey());
                valuesVideos.put(VIDEO_NAME, videos.getVideo(i).getName());
                valuesVideos.put(VIDEO_MOVIE_ID, b.getString(context.getString(R.string.id)));
                try {
                    db.insert(VIDEOS_TABLE_NAME, null, valuesVideos);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }


    }

    public void deleteEntry(Bundle b) {
        db.delete(MOVIES_TABLE_NAME, MOVIE_ID + "=" + b.getString(context.getString(R.string.id)), null);
        db.delete(REVIEWS_TABLE_NAME, REVIEW_MOVIE_ID + "=" + b.getString(context.getString(R.string.id)), null);
        db.delete(VIDEOS_TABLE_NAME, VIDEO_MOVIE_ID + "=" + b.getString(context.getString(R.string.id)), null);

    }

    public Movie getMovieFromDb(String id) {
        Cursor cursor;
        Movie movie = null;
        try {
            String queryStatement = "SELECT * FROM " + MOVIES_TABLE_NAME + " WHERE "
                    + MOVIE_ID + "=?";

            cursor = db.rawQuery(queryStatement,
                    new String[]{String.valueOf(id)});
            cursor.moveToFirst();
            if (cursor != null) {
                movie = setMovieValues(cursor);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movie;
    }
    public Cursor getMovieDetailCursor(String id) {
        Cursor cursor = null;

        try {
            String queryStatement = "SELECT * FROM " + MOVIES_TABLE_NAME + " WHERE "
                    + MOVIE_ID + "=?";

            cursor = db.rawQuery(queryStatement,
                    new String[]{String.valueOf(id)});

        } catch (Exception e) {
            e.printStackTrace();
            }
        return cursor;
    }

    public MovieDetail getMovieDetailFromDb(String id) {
        Cursor cursor;
        MovieDetail movieDetail = null;
        try {
            String queryStatement = "SELECT * FROM " + MOVIES_TABLE_NAME + " WHERE "
                    + MOVIE_ID + "=?";

            cursor = db.rawQuery(queryStatement,
                    new String[]{String.valueOf(id)});
            cursor.moveToFirst();
            if (cursor != null) {
                movieDetail = Utilities.setMovieDetail(cursor);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieDetail;
    }
    public Cursor getMovieVideosCursor(String id) {
        Cursor cursor = null;
        Videos videos = new Videos();
        try {
            String queryStatement = "SELECT * FROM " + VIDEOS_TABLE_NAME + " WHERE "
                    + VIDEO_MOVIE_ID + "=?";

            cursor = db.rawQuery(queryStatement,
                    new String[]{String.valueOf(id)});

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }

    public Videos getMovieVideosFromDb(String id) {
        Cursor cursor;
        Videos videos = new Videos();
        try {
            String queryStatement = "SELECT * FROM " + VIDEOS_TABLE_NAME + " WHERE "
                    + VIDEO_MOVIE_ID + "=?";

            cursor = db.rawQuery(queryStatement,
                    new String[]{String.valueOf(id)});
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    Video video = new Video();
                    video = setVideo(cursor);
                    videos.addVideo(video);


                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videos;
    }

    public Cursor getMovieReviewsCursor(String id) {
        Cursor cursor = null;
        Reviews reviews = new Reviews();
        try {
            String queryStatement = "SELECT * FROM " + REVIEWS_TABLE_NAME + " WHERE "
                    + REVIEW_MOVIE_ID + "=?";

            cursor = db.rawQuery(queryStatement,
                    new String[]{String.valueOf(id)});

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }

    public Reviews getMovieReviewsFromDb(String id) {
        Cursor cursor;
        Reviews reviews = new Reviews();
        try {
            String queryStatement = "SELECT * FROM " + REVIEWS_TABLE_NAME + " WHERE "
                    + REVIEW_MOVIE_ID + "=?";

            cursor = db.rawQuery(queryStatement,
                    new String[]{String.valueOf(id)});
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    Review review = new Review();
                    review = setReview(cursor);
                    reviews.addReview(review);


                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public Cursor getAllMoviesCursor() {
        Cursor cursor = null;
        Movies movies = new Movies();
        String queryStatement = "SELECT" + " * " + "FROM " + MOVIES_TABLE_NAME;
        cursor = db.rawQuery(queryStatement, null);


        return cursor;

    }

    public Movies getAllMoviesFromDb() {
        Cursor cursor;
        Movies movies = new Movies();
        String queryStatement = "SELECT" + " * " + "FROM " + MOVIES_TABLE_NAME;
        cursor = db.rawQuery(queryStatement, null);
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
    public MovieDetails getAllMoviesDetailsFromDb() {
        Cursor cursor;
        MovieDetails movieDetails = null;
        String queryStatement = "SELECT" + " * " + "FROM " + MOVIES_TABLE_NAME;
        cursor = db.rawQuery(queryStatement, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                MovieDetail movieDetail = new MovieDetail();
                movieDetail = Utilities.setMovieDetail(cursor);
                movieDetails.addMovieDetail(movieDetail);


            } while (cursor.moveToNext());
        }

        return movieDetails;

    }

    public Reviews getAllReviewsFromDb() {
        Cursor cursor;
        Reviews reviews = null;
        String queryStatement = "SELECT" + " * " + "FROM " + REVIEWS_TABLE_NAME;
        cursor = db.rawQuery(queryStatement, null);
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

    public Videos getAllVideosFromDb() {
        Cursor cursor;
        Videos videos = null;
        String queryStatement = "SELECT" + " * " + "FROM " + VIDEOS_TABLE_NAME;
        cursor = db.rawQuery(queryStatement, null);
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

    public Movie setMovieValues(Cursor cursor) {
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

  /*  public MovieDetail setMovieDetail(Cursor cursor) {
        MovieDetail movieDetail = new MovieDetail();
        movieDetail.setHomePage(cursor.getString(cursor
                .getColumnIndexOrThrow(HOMEPAGE)));
        movieDetail.setRevenue(cursor.getString(cursor
                .getColumnIndexOrThrow(REVENUE)));
        movieDetail.setRuntime(cursor.getString(cursor
                .getColumnIndexOrThrow(RUNTIME)));
        return movieDetail;
    }*/

    public Review setReview(Cursor cursor) {
        Review review = new Review();
        review.setId(cursor.getString(cursor
                .getColumnIndexOrThrow(REVIEW_ID)));
        review.setAuthor(cursor.getString(cursor
                .getColumnIndexOrThrow(REVIEW_AUTHOR)));
        review.setContent(cursor.getString(cursor
                .getColumnIndexOrThrow(REVIEW_CONTENT)));
        return review;
    }

    public Video setVideo(Cursor cursor) {
        Video video = new Video();
        video.setId(cursor.getString(cursor
                .getColumnIndexOrThrow(VIDEO_ID)));
        video.setKey(cursor.getString(cursor
                .getColumnIndexOrThrow(VIDEO_KEY)));
        video.setName(cursor.getString(cursor
                .getColumnIndexOrThrow(VIDEO_NAME)));
        return video;

    }


}
