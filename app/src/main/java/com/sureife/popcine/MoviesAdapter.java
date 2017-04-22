package com.sureife.popcine;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.sureife.popcine.models.Movie;
import com.sureife.popcine.utils.Constants;

import java.util.Collections;
import java.util.List;

/**
 * Created by sureife on 16/04/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> movies;

    public MoviesAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pop_movies_list_item, null);
        return new MovieViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    public void replaceData(List<Movie> movies){
        this.movies = movies;
        notifyDataSetChanged();
    }

    private List<Movie> getMovies(){
        return movies;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{
        ImageView movieImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movieImageView = (ImageView) itemView.findViewById(R.id.iv_mov);
            movieImageView.setOnClickListener(movieClickListener);
        }

        // bind data from adapter to ViewHolder
        void bind(Movie movie){

            // Load Image into ImageView
            Context context = movieImageView.getContext();
            Picasso.with(context)
                    .load(buildMovieThumbnailUrl(movie))
                    .error(R.drawable.image_load_err)
                    .into(movieImageView);
        }

        View.OnClickListener movieClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetailsForThisMovie();
            }
        };

        void showDetailsForThisMovie(){
            Context context = movieImageView.getContext();
            Intent intent = new Intent(context,MovieDetailsActivity.class);
            intent.putExtra(MovieDetailsActivity.MOVIE_EXTRA,getMovies().get(getAdapterPosition()));
            context.startActivity(intent);
        }


        String buildMovieThumbnailUrl(Movie movie){
            String url = Constants.MOVIE_DB_IMAGE_BASE_URL + "w342" + movie.getMoviePosterThumbnailURL();
            return url;
        }
    }
}
