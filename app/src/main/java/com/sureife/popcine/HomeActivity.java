package com.sureife.popcine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sureife.popcine.models.Movie;
import com.sureife.popcine.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public static final String TAG = HomeActivity.class.getSimpleName();

    private RecyclerView mMoviesRecyclerView;

    private RecyclerView.LayoutManager mMoviesLayoutManager;

    private MoviesAdapter movieAdapter;

    private static final int POPULARITY = 0;

    private static final int TOP_RATED = 1;

    private int sortOrder = POPULARITY; // 0 - Popularity , 1 - TopRated

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        progressBar = (ProgressBar)findViewById(R.id.pb_loading_indicator);
        setupRecyclerView();

        toggleProgressBar();  // Set it to visible

        if (NetworkUtils.isConnected(this)){
            MovieDBService.getPopularMovies(moviesCallback);
        }else {
            toggleProgressBar();
            Toast.makeText(this, R.string.no_network_msg,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_popularity) {
            changeSortOrder(POPULARITY);
            return true;
        }else if (itemThatWasClickedId == R.id.action_top_rated){
            changeSortOrder(TOP_RATED);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void changeSortOrder(int sortOrder){
        if(this.sortOrder != sortOrder){
            switch (sortOrder){
                case POPULARITY:
                    sortOrder = POPULARITY;
                    if (NetworkUtils.isConnected(this)){
                        toggleProgressBar();
                        MovieDBService.getPopularMovies(moviesCallback);
                    }else {
                        Toast.makeText(this, R.string.no_network_msg,Toast.LENGTH_SHORT).show();
                    }

                case TOP_RATED:
                    sortOrder = TOP_RATED;
                    if (NetworkUtils.isConnected(this)){
                        toggleProgressBar();
                        MovieDBService.getTopRatedMovies(moviesCallback);
                    }else {
                        Toast.makeText(this,R.string.no_network_msg,Toast.LENGTH_SHORT).show();
                    }

            }
        }
    }

    void toggleProgressBar(){
        if(progressBar.getVisibility() == View.VISIBLE){
            progressBar.setVisibility(View.INVISIBLE);
        }else{
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    void toggleRecyclerViewVisibility(){
        if(mMoviesRecyclerView.getVisibility() == View.VISIBLE){
            mMoviesRecyclerView.setVisibility(View.INVISIBLE);
        }else{
            mMoviesRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    void setupRecyclerView(){
        mMoviesRecyclerView = (RecyclerView)findViewById(R.id.rv_movies);
        mMoviesLayoutManager = new StaggeredGridLayoutManager(2,1);
        movieAdapter = new MoviesAdapter(new ArrayList<Movie>(0));
        mMoviesRecyclerView.setLayoutManager(mMoviesLayoutManager);
        mMoviesRecyclerView.setAdapter(movieAdapter);
    }

    MovieDBService.MoviesCallback moviesCallback = new MovieDBService.MoviesCallback() {
        @Override
        public void success(List<Movie> movies) {
            toggleProgressBar();
            movieAdapter.replaceData(movies);
        }

        @Override
        public void failure(Throwable err) {
            Log.d(TAG, err.getMessage());
            toggleProgressBar();
            Toast.makeText(HomeActivity.this, R.string.error_message,Toast.LENGTH_SHORT).show();
        }
    };



}
