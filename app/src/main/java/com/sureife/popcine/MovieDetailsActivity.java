package com.sureife.popcine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sureife.popcine.models.Movie;
import com.sureife.popcine.utils.Constants;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String MOVIE_EXTRA = "movie";

    ImageView movieImageView;

    TextView titleTextView;

    TextView releaseDateTextView;

    TextView userRatingTextView;

    TextView movieSynopsisTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        bindViewsToFields();

        Movie movie = getExtra();

        if(movie != null){
            displayMovie(movie);
        }

    }

    void bindViewsToFields(){
        movieImageView = (ImageView)findViewById(R.id.iv_movie_thumbnail);
        titleTextView = (TextView)findViewById(R.id.tv_movie_title);
        movieSynopsisTextView = (TextView)findViewById(R.id.tv_plot_synopsis);
        releaseDateTextView = (TextView)findViewById(R.id.tv_release_date);
        userRatingTextView = (TextView)findViewById(R.id.tv_user_ratings);
    }

    Movie getExtra(){
        Movie movie = null;

        Intent intent = getIntent();

        if(intent.hasExtra(MOVIE_EXTRA)){
            movie = (Movie) intent.getSerializableExtra(MOVIE_EXTRA);
        }

        return movie;
    }

    void displayMovie(Movie movie){
        Picasso.with(this).load(buildMovieThumbnailUrl(movie)).into(movieImageView);
        titleTextView.setText(movie.getOriginalTitle());
        movieSynopsisTextView.setText(movie.getPlotSynopsis());
        releaseDateTextView.setText(movie.getReleaseDate());
        userRatingTextView.setText(String.valueOf(movie.getUserRating()));
    }

    String buildMovieThumbnailUrl(Movie movie){
        return Constants.MOVIE_DB_IMAGE_BASE_URL + "w185/" + movie.getMoviePosterThumbnailURL();
    }
}
