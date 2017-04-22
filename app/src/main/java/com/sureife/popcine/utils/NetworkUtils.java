package com.sureife.popcine.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static com.sureife.popcine.utils.Config.API_KEY;

/**
 * Created by sureife on 16/04/2017.
 */

public class NetworkUtils {


    private final static String MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/";

    private static final String PARAM_API_KEY = "api_key";

    public static URL  buildPopularityUrl() {
        Uri builtUri =
                Uri.parse(MOVIE_DB_BASE_URL).buildUpon()
                        .appendPath("movie")
                        .appendPath("popular")
                        .appendQueryParameter(PARAM_API_KEY,API_KEY)
                        .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL  buildTopRatedUrl() {
        Uri builtUri =
                Uri.parse(MOVIE_DB_BASE_URL).buildUpon()
                        .appendPath("movie")
                        .appendPath("top_rated")
                        .appendQueryParameter(PARAM_API_KEY,API_KEY)
                        .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


    public static boolean isConnected(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


}
