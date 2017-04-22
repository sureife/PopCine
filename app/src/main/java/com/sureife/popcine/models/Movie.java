package com.sureife.popcine.models;

import java.io.Serializable;

/**
 * Created by sureife on 16/04/2017.
 */

public class Movie implements Serializable {
    private String originalTitle;

    private String moviePosterThumbnailURL;

    private String plotSynopsis;

    private double userRating;

    private String releaseDate;

    public Movie(String originalTitle, String moviePosterThumbnailURL, String plotSynopsis, double userRating, String releaseDate) {
        this.originalTitle = originalTitle;
        this.moviePosterThumbnailURL = moviePosterThumbnailURL;
        this.plotSynopsis = plotSynopsis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getMoviePosterThumbnailURL() {
        return moviePosterThumbnailURL;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public double getUserRating() {
        return userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
