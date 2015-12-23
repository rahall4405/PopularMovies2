package nanodegree.rahall.popularmovies2.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import nanodegree.rahall.popularmovies2.MovieApplication;
import nanodegree.rahall.popularmovies2.R;
import nanodegree.rahall.popularmovies2.activities.ErrorHandlerActivity;
import nanodegree.rahall.popularmovies2.activities.MovieDetails;
import nanodegree.rahall.popularmovies2.adaptors.MovieAdapter;
import nanodegree.rahall.popularmovies2.models.Movie;
import nanodegree.rahall.popularmovies2.models.Movies;
import nanodegree.rahall.popularmovies2.utilities.CustomIntents;
import nanodegree.rahall.popularmovies2.utilities.Utilities;


public class PopularMoviesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static final int NUM_COLS_PORTRAIT = 2;
    public static final int NUM_COLS_LANDSCAPE = 4;
    public static final int NUM_COLS_LANDSCAPE_TABLET = 3;
    public static final int PORTRAIT = 1;
    public static final int LANDSCAPE = 2;
    private int savedPosition = 0;
    Context mContext;

    RecyclerView mMovieRecylcerView;
    MovieAdapter mMovieAdapter;
    Movies movies;

    private OnFragmentInteractionListener myListener;


    public static PopularMoviesFragment newInstance(String param1, String param2) {
        PopularMoviesFragment fragment = new PopularMoviesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PopularMoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override

    public void onResume() {
        super.onResume();

        getActivity().registerReceiver(receiver, new IntentFilter(
                CustomIntents.DOWNLOAD_COMPLETE));
        getActivity().registerReceiver(receiver_error, new IntentFilter(
                CustomIntents.DOWNLOAD_ERROR));
        if(MovieApplication.getInstance().getSortPreference().equals(getActivity().getString(R.string.favorites)) && !Utilities.isTablet(mContext)) {
            Utilities.getMovies(getActivity());
        }



    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
        getActivity().unregisterReceiver(receiver_error);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(savedInstanceState != null) {
            movies =  savedInstanceState.getParcelable("movies");
        }

        View movieView = inflater.inflate(R.layout.fragment_popular_movies, container, false);
        mMovieRecylcerView = (RecyclerView) movieView.findViewById(R.id.movie_image_recyclerview);
        mMovieAdapter = new MovieAdapter(getActivity());
        mMovieRecylcerView.setAdapter(mMovieAdapter);
        mMovieRecylcerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager;
        if (getActivity().getResources().getConfiguration().orientation == PORTRAIT && !Utilities.isTablet(getActivity())) {
            gridLayoutManager = new GridLayoutManager(getActivity(), NUM_COLS_PORTRAIT);
        } else if (getActivity().getResources().getConfiguration().orientation == PORTRAIT && Utilities.isTablet(getActivity())) {
            gridLayoutManager = new GridLayoutManager(getActivity(), NUM_COLS_PORTRAIT);
        } else {
            gridLayoutManager = new GridLayoutManager(getActivity(), NUM_COLS_LANDSCAPE_TABLET);
        }
        mMovieRecylcerView.setLayoutManager(gridLayoutManager);
        mMovieRecylcerView.setItemAnimator(new DefaultItemAnimator());
        mMovieAdapter.SetOnItemClickListener(new MovieAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v, Bundle b1) {


                if (Utilities.isTablet(mContext)) {
                    myListener.onFragmentInteractionListener(b1);
                    MovieApplication.getInstance().setPosition(b1.getInt(mContext.getString(R.string.position)));
                } else {
                    Intent intentDetail = new Intent(mContext, MovieDetails.class);
                    intentDetail.putExtras(b1);
                    mContext.startActivity(intentDetail);
                }
            }
        });
        if(movies == null) {

            Utilities.getMovies(getActivity());
        } else {
            mMovieAdapter.setMovies(movies);
            mMovieAdapter.notifyDataSetChanged();
            mMovieAdapter.notifyItemRangeInserted(0, movies.getSize()- 1);
        }




        return movieView;

    }
    @Override
    public void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        out.putParcelable("movies", movies);
    }
   /* @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
           movies =  savedInstanceState.getParcelable("movies");

        } catch (Exception e) {

        }
    }*/

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getActivity();
        setRetainInstance(true);

        try {
            myListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }



    public interface OnFragmentInteractionListener {
        public void onFragmentInteractionListener(Bundle b);

    }


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (CustomIntents.DOWNLOAD_COMPLETE.equals(action)) {
                ArrayList<Movie> movieArrayList = intent.<Movie>getParcelableArrayListExtra("nanodegree.rahall.popularmovies2.models.Movie");
                movies = new Movies();
                movies.setMovies(movieArrayList);

                mMovieAdapter.setMovies(movies);

                mMovieAdapter.notifyDataSetChanged();
                mMovieAdapter.notifyItemRangeInserted(0, movies.getSize()- 1);
                if (Utilities.isTablet(getActivity()) && movies.getSize() >0) {
                    Timer myTimer = new Timer();
                    myTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {

                            getActivity().runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                try {
                                                                    MovieApplication.getInstance().setPosition(0);
                                                                    mMovieRecylcerView.findViewHolderForAdapterPosition(MovieApplication.getInstance().getPosition()).itemView.performClick();
                                                                } catch (Exception e) {

                                                                }
                                                            }
                                                        }
                            );
                        }
                    }, 1000);


                    //reload all data
                    // displayData();


                }
            }
        }
    };

    BroadcastReceiver receiver_error = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (CustomIntents.DOWNLOAD_ERROR.equals(action)) {

                // start error intent
                gotoErrorActivity();


            }
        }
    };

    public void gotoErrorActivity() {
        Intent intent = new Intent(getActivity(), ErrorHandlerActivity.class);
        this.startActivity(intent);
    }

    @Override

    public void onDestroy() {
        super.onDestroy();
        MovieApplication.getInstance().setPosition(0);
    }


}
