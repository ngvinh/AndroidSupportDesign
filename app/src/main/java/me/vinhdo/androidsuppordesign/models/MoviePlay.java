package me.vinhdo.androidsuppordesign.models;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by vinh on 10/15/15.
 */
public class MoviePlay {

    @SerializedName("MovieName")
    private String name;

    @SerializedName("LinkPlay")
    private String linkPlay;

    @SerializedName("LinkPlayBackup")
    private String linkPlayBackup;

    @SerializedName("SubtitleExt")
    private HashMap<String, SubModel> subs;

    @SerializedName("Episode")
    private int episode;

    @SerializedName("CurrentSession")
    private int currentSession;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkPlay() {
        return linkPlay;
    }

    public void setLinkPlay(String linkPlay) {
        this.linkPlay = linkPlay;
    }

    public String getLinkPlayBackup() {
        return linkPlayBackup;
    }

    public void setLinkPlayBackup(String linkPlayBackup) {
        this.linkPlayBackup = linkPlayBackup;
    }

    public HashMap<String, SubModel> getSubs() {
        return subs;
    }

    public void setSubs(HashMap<String, SubModel> subs) {
        this.subs = subs;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public int getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(int currentSession) {
        this.currentSession = currentSession;
    }
}
