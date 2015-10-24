package me.vinhdo.androidsuppordesign.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vinh on 10/8/15.
 */
public class HomePageMovies {
    @SerializedName("Movies_Banners")
    private List<MovieModel> moviesBanner;

    @SerializedName("MoviesByCates")
    private List<CateModel> moviesCates;

    public List<MovieModel> getMoviesBanner() {
        return moviesBanner;
    }

    public void setMoviesBanner(List<MovieModel> moviesBanner) {
        this.moviesBanner = moviesBanner;
    }

    public List<CateModel> getMoviesCates() {
        return moviesCates;
    }

    public void setMoviesCates(List<CateModel> moviesCates) {
        this.moviesCates = moviesCates;
    }
}
