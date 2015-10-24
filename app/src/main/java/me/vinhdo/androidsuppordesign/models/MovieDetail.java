package me.vinhdo.androidsuppordesign.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vinh on 10/15/15.
 */
public class MovieDetail {
    @SerializedName("MovieID")
    private int id;

    @SerializedName("MovieName")
    private String name;

    @SerializedName("Cover")
    private String cover;

    @SerializedName("Link")
    private String link;

    @SerializedName("CategoryID")
    private String categoryID;

    @SerializedName("KnownAs")
    private String nameTV;

    @SerializedName("Backdrop")
    private String backdrop;

    @SerializedName("Poster")
    private String poster;

    @SerializedName("ImdbRating")
    private String imdbRating;

    @SerializedName("PlotVI")
    private String PlotVI;

    @SerializedName("PlotEN")
    private String plotEN;

    @SerializedName("Sequence")
    private int sequence;

    @SerializedName("Episode")
    private int episode;

    @SerializedName("Country")
    private String country;

    @SerializedName("HD")
    private String hd;

    @SerializedName("Cast")
    private String cast;

    @SerializedName("CurrentSeason")
    private int currentSeason;

    @SerializedName("Poster100x149")
    private String poster100x149;

    @SerializedName("IsAds")
    private boolean isAds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getPoster100x149() {
        return poster100x149;
    }

    public void setPoster100x149(String poster100x149) {
        this.poster100x149 = poster100x149;
    }

    public String getNameTV() {
        return nameTV;
    }

    public void setNameTV(String nameTV) {
        this.nameTV = nameTV;
    }

    public boolean isAds() {
        return isAds;
    }

    public void setIsAds(boolean isAds) {
        this.isAds = isAds;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getPlotVI() {
        return PlotVI;
    }

    public void setPlotVI(String plotVI) {
        PlotVI = plotVI;
    }

    public String getPlotEN() {
        return plotEN;
    }

    public void setPlotEN(String plotEN) {
        this.plotEN = plotEN;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHd() {
        return hd;
    }

    public void setHd(String hd) {
        this.hd = hd;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public int getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(int currentSeason) {
        this.currentSeason = currentSeason;
    }
}
