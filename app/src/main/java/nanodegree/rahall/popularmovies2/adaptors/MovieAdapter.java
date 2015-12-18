package nanodegree.rahall.popularmovies2.adaptors;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

import nanodegree.rahall.popularmovies2.R;
import nanodegree.rahall.popularmovies2.MovieApplication;
import nanodegree.rahall.popularmovies2.models.Movie;
import nanodegree.rahall.popularmovies2.models.Movies;
import nanodegree.rahall.popularmovies2.utilities.HttpHelper;

/**
 * Created by rahall on 8/29/15.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final LayoutInflater inflator;
    private Movies movies;
    private Context context;
    private Bundle b1;
    OnItemClickListener mItemClickListener;

    public MovieAdapter(Context context) {
        this.context = context;
        inflator = LayoutInflater.from(context);
        movies = new Movies();

    }

    public void setMovies(Movies movies) {
        this.movies.getMovies().clear();
        this.movies.getMovies().addAll(movies.getMovies());
        this.notifyItemRangeInserted(0, movies.getMovies().size() - 1);


    }

    public Bundle getData() {
        return b1;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = inflator.from(context).inflate(R.layout.movie_layout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        Movie movie = movies.getMovies().get(position);

        if (((MovieViewHolder) holder).movieImage != null) {
            if (MovieApplication.getInstance().getSortPreference().equals(context.getString(R.string.favorites))) {
                File file = new File(MovieApplication.getApplicationDirectory() + "/image_files" + movie.getPoster_path());
                Picasso.with(context).load(file).fit().into(((MovieViewHolder) holder).movieImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("Picasso", "S loading file");
                    }

                    @Override
                    public void onError() {

                        Log.d("Picasso", "Error loading file");
                    }
                });



            } else {
                Picasso.with(context)
                        .load(HttpHelper.getImageSize185HttpRequest(movie.getPoster_path()))
                        .fit()
                        .into(((MovieViewHolder) holder).movieImage, new Callback() {
                            @Override
                            public void onSuccess() {
                                Log.d("Picasso", "S loading file");
                            }

                            @Override
                            public void onError() {

                                Log.d("Picasso", "Error loading file");
                            }
                        });
            }
        }


    }

    @Override
    public int getItemCount() {

        return movies.getMovies().size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView movieImage;
        FrameLayout movieImageFrameLayout;

        public MovieViewHolder(View view) {
            super(view);


            movieImageFrameLayout = (FrameLayout) view.findViewById(R.id.movie_image_framelayout);
            movieImageFrameLayout.setOnClickListener(this);

            movieImage = (ImageView) view.findViewById(R.id.movie_image);


        }


        @Override
        public void onClick(View view) {
            Movie currentMovie = movies.getMovies().get(getAdapterPosition());
            int currentPostion = getAdapterPosition();
            if (view.getId() == R.id.movie_image_framelayout) {

                b1 = new Bundle();
                b1.putString(context.getString(R.string.release_date), currentMovie.getRelease_date());
                b1.putString(context.getString(R.string.poster_image), currentMovie.getPoster_path());
                b1.putString(context.getString(R.string.title), currentMovie.getTitle());
                b1.putString(context.getString(R.string.vote_average), currentMovie.getVote_average());
                b1.putString(context.getString(R.string.vote_count), currentMovie.getVote_count());
                b1.putString(context.getString(R.string.id), currentMovie.getId());
                b1.putString(context.getString(R.string.overview), currentMovie.getOverview());
                b1.putInt(context.getString(R.string.position), currentPostion);


                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(view, b1);
                }


            }

        }
    }
    public interface OnItemClickListener {
        public void onItemClick(View view , Bundle b1);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
