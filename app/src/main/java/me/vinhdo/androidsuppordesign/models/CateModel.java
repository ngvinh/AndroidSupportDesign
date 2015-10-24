package me.vinhdo.androidsuppordesign.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vinh on 10/8/15.
 */
public class CateModel {
    @SerializedName("CategoryName")
    private String name;

    @SerializedName("CategoryID")
    private String id;

    @SerializedName("Movies")
    private List<MovieModel> movies;

    private boolean isExpan = false;

    public boolean isExpan() {
        return isExpan;
    }

    public int getNumMoviesShow(){
        if(movies == null) return 0;
        return (isExpan || movies.size() <= 6) ? movies.size() : 6;
    }
    public void setIsExpan(boolean isExpan) {
        this.isExpan = isExpan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<MovieModel> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieModel> movies) {
        this.movies = movies;
    }
}
