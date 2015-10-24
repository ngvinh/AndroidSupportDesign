package me.vinhdo.androidsuppordesign.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vinh on 10/8/15.
 */
public class MovieModel {
    @SerializedName("MovieID")
    private int id;

    @SerializedName("MovieName")
    private String name;

    @SerializedName("CategoryName")
    private String categoryName;

    @SerializedName("Cover")
    private String cover;

    @SerializedName("Link")
    private String link;

    @SerializedName("CategoryID")
    private String categoryID;

    @SerializedName("KnownAs")
    private String nameTV;

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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
}
