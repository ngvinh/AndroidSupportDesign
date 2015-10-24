package me.vinhdo.androidsuppordesign.api.loopj;

import android.text.TextUtils;

import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;


import java.util.Random;

import me.vinhdo.androidsuppordesign.AppApplication;
import me.vinhdo.androidsuppordesign.config.ApiConfig;

public class RestClient {

    static {
        LoopjRestClient.setHandleError(new HandleErrorMessage());
    }

    public static RestClient synchronize() {
        LoopjRestClient.synchronize();
        return new RestClient();
    }

    public static void downloadFile(String url, FileAsyncHttpResponseHandler responseHandler) {
        LoopjRestClient.download(url, responseHandler);
    }

    public static void getHDVConfig(TextHttpResponseHandler responseHandler){
        LoopjRestClient.get(ApiConfig.API_CONFIG_URL, null, null, responseHandler);
    }

    public static void getToken(TextHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        int o1 = new Random().nextInt(254);
        int o2 = new Random().nextInt(254);
        int o3 = new Random().nextInt(254);
        int o4 = new Random().nextInt(254);
        params.add("ip",o1 + "." + o2 + "." +o3 + "." + o4);
        params.add(ApiConfig.PARAM_UID, AppApplication.getHdvConfig().getSign());
        LoopjRestClient.get(ApiConfig.getTokenUrl(), params, responseHandler);
    }

    public static void getHomePage(TextHttpResponseHandler responseHandler){
        if(TextUtils.isEmpty(ApiConfig.getHomePageUrl())) return;
        LoopjRestClient.get(ApiConfig.getHomePageUrl(), null, null, responseHandler);
    }

    public static void getDetailVideo(int id, TextHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        params.add(ApiConfig.PARAM_MOVIE_ID, String.valueOf(id));
        params.add("width", "1920");
        params.add(ApiConfig.PARAM_SIGN, AppApplication.getHdvConfig().getSign());
        params.add(ApiConfig.PARAM_TOKEN, AppApplication.getToken());
        LoopjRestClient.get(ApiConfig.getVideoDetailUrl() ,params, responseHandler);
    }

    public static void getPlayVideo(int id,int ep, TextHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        params.add(ApiConfig.PARAM_MOVIE_ID, String.valueOf(id));
        params.add(ApiConfig.PARAM_EP, String.valueOf(ep));
        params.add("width", "1920");
        params.add(ApiConfig.PARAM_SIGN, AppApplication.getHdvConfig().getSign());
        params.add(ApiConfig.PARAM_TOKEN, AppApplication.getToken());
        LoopjRestClient.get(ApiConfig.getVideoPlaylUrl() ,params, responseHandler);
    }

    public static void getLinkPlay(String url, TextHttpResponseHandler responseHandler){
        LoopjRestClient.get(url, null, responseHandler);
    }
}
