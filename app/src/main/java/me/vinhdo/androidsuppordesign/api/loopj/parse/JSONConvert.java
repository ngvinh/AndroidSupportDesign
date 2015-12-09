package me.vinhdo.androidsuppordesign.api.loopj.parse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import me.vinhdo.androidsuppordesign.AppApplication;
import me.vinhdo.androidsuppordesign.R;
import me.vinhdo.androidsuppordesign.config.ApiConfig;
import me.vinhdo.androidsuppordesign.models.CateModel;
import me.vinhdo.androidsuppordesign.models.HDVConfig;
import me.vinhdo.androidsuppordesign.models.HomePageMovies;
import me.vinhdo.androidsuppordesign.models.MovieDetail;
import me.vinhdo.androidsuppordesign.models.MovieModel;
import me.vinhdo.androidsuppordesign.models.MoviePlay;
import me.vinhdo.androidsuppordesign.models.ResponseModel;
import me.vinhdo.androidsuppordesign.models.Sub;
import me.vinhdo.androidsuppordesign.utils.Log;

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

    public static List<Sub> getSubs(String data){
        List<Sub> subs = new ArrayList<>();
        String nl = "\\n\\d{1,5}\\r";
        String sp = "[ \\t]*";
//        Pattern pattern = Pattern.compile("(?s)(\\d+)" + sp + nl + "(\\d{1,2}):(\\d\\d):(\\d\\d),(\\d\\d\\d)" + sp + "-->" + sp + "(\\d\\d):(\\d\\d):(\\d\\d),(\\d\\d\\d)" + sp + "(X1:\\d.*?)??" + nl + "(.*?)" + nl + nl);
        Pattern pattern = Pattern.compile(nl);
        String[] lines = data.split(nl);
        lines[0] = lines[0].substring(2);
        Log.d("LINES " + lines.length);
        for(int i = 0; i < lines.length; i++){
            String[] dd = lines[i].split("\\r\\n",2);
            Sub s = new Sub(i + 1,dd[0].trim(),dd[1].trim());
            subs.add(s);
        }
        return subs;
    }

    public static List<MovieModel> getMovies(String data) {
        Type type = new TypeToken<List<MovieModel>>(){}.getType();
        return (List<MovieModel>)mGson.fromJson(data, type);
    }
    public static List<CateModel> getListCates(String data) {
        Type type = new TypeToken<List<CateModel>>(){}.getType();
        return (List<CateModel>)mGson.fromJson(data, type);
    }
}
