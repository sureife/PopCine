package com.sureife.popcine;

import android.os.AsyncTask;

import com.sureife.popcine.models.Movie;
import com.sureife.popcine.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sureife on 16/04/2017.
 */

public class MovieDBService {

    interface MoviesCallback{
        void success(List<Movie> movies);
        void failure(Throwable err);
    }

    static void getPopularMovies(MoviesCallback callback){
        URL popularMoviesUrl = NetworkUtils.buildPopularityUrl();
        new MovieDBQueryTask(callback).execute(popularMoviesUrl);
    }

    static void getTopRatedMovies(MoviesCallback callback){
        URL topRatedUrl = NetworkUtils.buildTopRatedUrl();
        new MovieDBQueryTask(callback).execute(topRatedUrl);
    }

    private static class MovieDBQueryTask extends AsyncTask<URL,Void,String> {
        MoviesCallback callback;

        MovieDBQueryTask(MoviesCallback callback) {
            this.callback = callback;
        }


        @Override
        protected String doInBackground(URL... params) {
            URL queryUrl = params[0];

            String results = null;
            try {
                results = NetworkUtils.getResponseFromHttpUrl(queryUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return results;
        }

        @Override
        protected void onPostExecute(String results) {

            if (results == null){
                callback.failure(new Throwable("Error getting results"));
                return;
            }

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(results);
                JSONArray resultsArr = jsonObject.getJSONArray("results");

                List<Movie> movies = new ArrayList<>();

                for (int i = 0; i < resultsArr.length(); i++) {
                    JSONObject movieJSON = (JSONObject) resultsArr.get(i);

                    String posterPath = movieJSON.getString("poster_path");
                    String synopsis =  movieJSON.getString("overview");
                    String title = movieJSON.getString("original_title");
                    String releaseDate =  movieJSON.getString("release_date");
                    double userRating = movieJSON.getDouble("vote_average");

                    Movie movie = new Movie(title,posterPath,synopsis,userRating,releaseDate);
                    movies.add(movie);
                }

                callback.success(movies);
            }catch (JSONException e){
                callback.failure(e);
            }
        }
    }


}
