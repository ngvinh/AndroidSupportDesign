package me.vinhdo.androidsuppordesign.config;

import me.vinhdo.androidsuppordesign.AppApplication;

/**
 * Created by vinh on 10/8/15.
 */
public class ApiConfig {
    public static final String PARAM_TOKEN = "accesstokenkey";
    public static final String PARAM_UID = "uid";
    public static String API_CONFIG_BASE_URL = "http://188.166.251.228/";
    public static String API_CONFIG_URL = API_CONFIG_BASE_URL + "hdv.php";

    public static String HOME_PAGE_URL = "/movie/homepage";
    public static String VIDEO_PLAY_URL = "/movie/play";
    public static String VIDEO_DETAIL_URL = "/movie";
    public static String SEARCH_URL = "/movie/search";
    public static String PARAM_MOVIE_ID = "movieid";
    public static String PARAM_SIGN = "sign";
    public static String PARAM_EP = "ep";
    public static String PARAM_KEY = "key";

    public static String PARAM_SEQUENCE = "sequence";
    public static String getHomePageUrl(){
        return AppApplication.getHdvConfig() != null ? AppApplication.getHdvConfig().getLink() + HOME_PAGE_URL : "";
    }

    public static String getVideoDetailUrl(){
        return AppApplication.getHdvConfig() != null ? AppApplication.getHdvConfig().getLink() + VIDEO_DETAIL_URL : "";
    }

    public static String getVideoPlaylUrl(){
        return AppApplication.getHdvConfig() != null ? AppApplication.getHdvConfig().getLink() + VIDEO_PLAY_URL : "";
    }

    public static String getSearchUrl(){
        return AppApplication.getHdvConfig() != null ? AppApplication.getHdvConfig().getLink() + SEARCH_URL : "";
    }

    public static String getTokenUrl(){
        return AppApplication.getHdvConfig() != null ? AppApplication.getHdvConfig().getLink() + "/token" : "";
    }

    public static String getCateUrl() {
        return AppApplication.getHdvConfig() != null ? AppApplication.getHdvConfig().getLink() + "/category/menu" : "";
    }
}
