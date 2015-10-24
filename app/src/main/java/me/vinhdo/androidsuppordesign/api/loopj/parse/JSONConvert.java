package me.vinhdo.androidsuppordesign.api.loopj.parse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import me.vinhdo.androidsuppordesign.AppApplication;
import me.vinhdo.androidsuppordesign.R;
import me.vinhdo.androidsuppordesign.config.ApiConfig;
import me.vinhdo.androidsuppordesign.models.HDVConfig;
import me.vinhdo.androidsuppordesign.models.HomePageMovies;
import me.vinhdo.androidsuppordesign.models.MovieDetail;
import me.vinhdo.androidsuppordesign.models.MoviePlay;
import me.vinhdo.androidsuppordesign.models.ResponseModel;

/**
 * Created by vinh on 10/8/15.
 */
public class JSONConvert {
    public static Gson mGson = new Gson();

    public static HDVConfig getHDVConfig(String responseString){
        HDVConfig config = new HDVConfig();
        try {
            JSONObject jResponse = new JSONObject(responseString);
            config.setSign(jResponse.getString("sign"));
            config.setLink(jResponse.getString("link"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return config;
    }

    public static HomePageMovies getHomePageData(String responseString){
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(responseString, HomePageMovies.class);
    }

    public static ResponseModel getResponse(String responseString) {
        if (responseString == null)
            return new ResponseModel(0, AppApplication.get().getString(R.string.message_network_is_unavailable));

        ResponseModel responseModel = new ResponseModel();
        try {
            JSONObject jResponse = new JSONObject(responseString);
            int success = jResponse.getInt("e");
            responseModel.setSuccess(success);
            boolean hasData = jResponse.has("r");
            boolean hasMessage = jResponse.has("message");
            boolean hasErrorDetails = jResponse.has("error_details");

            if (hasData) {
                String data = jResponse.getString("r");
                responseModel.setData(data);
            }

            if (hasMessage) {
                String message = jResponse.getString("message");
                responseModel.setMessage(message);
            }

            if (hasErrorDetails) {
                String errorDetail = jResponse.getString("error_details");
                responseModel.setErrorDetails(errorDetail);
            }
            return responseModel;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ResponseModel(0, AppApplication.get().getString(R.string.generic_error));
    }

    public static MovieDetail getMovieDetail(String data){
        return mGson.fromJson(data, MovieDetail.class);
    }

    public static MoviePlay getMoviePlay(String data){
        return mGson.fromJson(data, MoviePlay.class);
    }
}
