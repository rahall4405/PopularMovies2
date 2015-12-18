package nanodegree.rahall.popularmovies2.fragments;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.NumberFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nanodegree.rahall.popularmovies2.R;
import nanodegree.rahall.popularmovies2.MovieApplication;
import nanodegree.rahall.popularmovies2.activities.MovieDetails;
import nanodegree.rahall.popularmovies2.customviews.ReviewView;
import nanodegree.rahall.popularmovies2.customviews.TrailorView;
import nanodegree.rahall.popularmovies2.databasemanager.DatabaseManager;
import nanodegree.rahall.popularmovies2.models.MovieDetail;
import nanodegree.rahall.popularmovies2.models.Reviews;
import nanodegree.rahall.popularmovies2.models.Videos;
import nanodegree.rahall.popularmovies2.utilities.CustomIntents;
import nanodegree.rahall.popularmovies2.utilities.DelegateNetworkAccess;
import nanodegree.rahall.popularmovies2.utilities.HttpHelper;
import nanodegree.rahall.popularmovies2.utilities.Utilities;


public class MovieDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    @Bind(R.id.movie_title)
    TextView mMovieTitle;
    @Bind(R.id.movie_image)
    ImageView mMovieImage;
    @Bind(R.id.movie_length)
    TextView mMovieLength;
    @Bind(R.id.revenue)
    TextView mRevenue;
    @Bind(R.id.home_page)
    TextView mHomePage;
    @Bind(R.id.release_date)
    TextView mReleaseDate;
    @Bind(R.id.vote_average)
    TextView mVoteAverage;
    @Bind(R.id.vote_count)
    TextView mVoteCount;
    @Bind(R.id.synopsys)
    TextView mSynopsys;
    @Bind(R.id.image_favorite_layout)
            LinearLayout mImageFavoriteLayout;
    @Bind(R.id.image_favorite)
            ImageView mImageFavorite;

    @Bind(R.id.reviews_title_linear_layout)
    LinearLayout mReviewTitle;

    boolean isFavorite = false;

    Context mContext;



    private Videos mVideos;
    private Reviews mReviews;
    private Bundle mBundle;

    DatabaseManager dm;

@OnClick(R.id.image_favorite)
public void clickFavorite(View view) {
    if (isFavorite) {
       mImageFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_black_36dp));
        isFavorite = false;

    } else {
        mImageFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_green_36dp));
        isFavorite = true;

    }
    String message;


    if (!isFavorite)  {
        message= mMovieTitle.getText() + " " + getResources().getString(R.string.delete_favorite);
        dm.deleteEntry(mBundle);
        Utilities.deleteImageFile(mBundle.getString("poster_image"));
    } else {
        message= mMovieTitle.getText() + " " + getResources().getString(R.string.add_favorite);
        dm.addEntry(mContext,mBundle,mReviews,mVideos);
        Utilities.downloadImageFile(mContext, mBundle.getString("poster_image"));
    }
    if(Utilities.isTablet(mContext)&& MovieApplication.getInstance().getSortPreference().equals(getString(R.string.favorites))) {
        Utilities.getMovies(getActivity());
    }

    Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .show();
}
    public static MovieDetailsFragment newInstance() {
        MovieDetailsFragment fragment = new MovieDetailsFragment();

        return fragment;
    }

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        mContext = getActivity();
        mContext.registerReceiver(receiver, new IntentFilter(
                CustomIntents.DOWNLOAD_DETAIL_COMPLETE));

        mContext.registerReceiver(receiver_error, new IntentFilter(
                CustomIntents.DOWNLOAD_ERROR));

        if (!Utilities.isTablet(getActivity())) {

            if (mBundle != null) {
                setDisplay(mBundle);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mContext.unregisterReceiver(receiver);

        mContext.unregisterReceiver(receiver_error);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View movieDetail = inflater.inflate(R.layout.fragment_movie_details, container, false);

        ButterKnife.bind(this, movieDetail);
        mBundle = MovieDetails.getExtras();


        return movieDetail;
    }


    public void dispPick(Bundle b) {

        setDisplay(b);
        mBundle = b;
        checkIfFavorite(b);

    }

    public void setDisplay(Bundle b) {
        mMovieTitle.setText(b.getString(mContext.getString(R.string.title)));
        mReleaseDate.setText(getString(R.string.release_date_string) + b.getString(mContext.getString(R.string.release_date), ""));
        mVoteAverage.setText(getString(R.string.average_rating) + b.getString(mContext.getString(R.string.vote_average), ""));
        mVoteCount.setText(getString(R.string.reviews) + b.getString(mContext.getString(R.string.vote_count), ""));
        mSynopsys.setText(b.getString(mContext.getString(R.string.overview), ""));

        String id = b.getString(mContext.getString(R.string.id), "");
        if (MovieApplication.getInstance().getSortPreference().equals(mContext.getString(R.string.favorites))) {
            File file = new File(MovieApplication.getApplicationDirectory() + "/image_files" + b.getString(mContext.getString(R.string.poster_image)));
            Picasso.with(mContext)
                    .load(file)
                    .fit()
                    .centerInside()
                    .into(mMovieImage);
        } else {
            Picasso.with(mContext)
                    .load(HttpHelper.getImageSize185HttpRequest(b.getString(mContext.getString(R.string.poster_image), "")))
                    .fit()
                    .centerInside()
                    .into(mMovieImage);

        }
        DelegateNetworkAccess.getMovies(mContext.getApplicationContext(), MovieApplication.getInstance().getSortPreference(), id);
        checkIfFavorite(b);

    }


    @Override
    public void onDetach() {
        super.onDetach();

    }


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


                String action = intent.getAction();

            if (CustomIntents.DOWNLOAD_DETAIL_COMPLETE.equals(action)) {

                MovieDetail movieDetail = MovieApplication.getInstance().getMovieDetail();
                mMovieLength.setText(movieDetail.getRuntime() + getString(R.string.minutes));
                long revenueLong = Long.parseLong(movieDetail.getRevenue());
                NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
                String revenueNumberString = numberFormat.format(revenueLong);
                revenueNumberString = revenueNumberString.substring(0, revenueNumberString.lastIndexOf("."));
                String revenue = getString(R.string.movie_revenue) + revenueNumberString;
                mVideos = MovieApplication.getInstance().getVideos();
                mReviews = MovieApplication.getInstance().getReviews();
                ViewGroup viewTrailor = (LinearLayout) getActivity().findViewById(R.id.trailor_linear_layout);
                viewTrailor.removeAllViewsInLayout();
                for (int i = 0; i < mVideos.getSize(); i++) {
                    TrailorView trailorView = new TrailorView(getActivity(), mVideos.getVideo(i).getKey(),
                            mVideos.getVideo(i).getName());
                    viewTrailor.addView(trailorView);
                }
                ViewGroup viewReview = (LinearLayout) getActivity().findViewById(R.id.reviewLinearLayout);
                viewReview.removeAllViewsInLayout();
                for (int i = 0; i < mReviews.getSize(); i++) {
                    ReviewView reviewView = new ReviewView(getActivity(), mReviews.getReview(i).getAuthor(),
                            mReviews.getReview(i).getContent());
                    viewReview.addView(reviewView);
                }
                if(mReviews.getSize() == 0) {
                    mReviewTitle.setVisibility(View.GONE);
                } else {
                    mReviewTitle.setVisibility(View.VISIBLE);
                }

                mRevenue.setText(revenue);
                mHomePage.setText(movieDetail.getHomePage());


            }
        }
    };


    BroadcastReceiver receiver_error = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (CustomIntents.DOWNLOAD_ERROR.equals(action)) {


                mRevenue.setVisibility(View.GONE);

                mHomePage.setVisibility(View.GONE);


            }
        }
    };

    public void checkIfFavorite(Bundle b) {
        dm = new DatabaseManager(getActivity());
        if(dm.getMovieFromDb(b.getString("id")) != null) {
            isFavorite = true;
            mImageFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_green_36dp));


        } else {
            isFavorite = false;
            mImageFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_black_36dp));
        }
    }

}
