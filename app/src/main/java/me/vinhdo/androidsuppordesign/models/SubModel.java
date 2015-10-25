package me.vinhdo.androidsuppordesign.models;

import com.google.gson.annotations.SerializedName;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import me.vinhdo.androidsuppordesign.api.loopj.RestClient;
import me.vinhdo.androidsuppordesign.api.loopj.parse.JSONConvert;
import me.vinhdo.androidsuppordesign.listeners.LoadSubListener;
import me.vinhdo.androidsuppordesign.utils.Log;

/**
 * Created by vinh on 10/15/15.
 */
public class SubModel {
    @SerializedName("Label")
    private String label;
    @SerializedName("Source")
    private String source;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    private List<Sub> subs;

    private Hashtable<Sub, String> hashSubs;


    public List<Sub> getSubs() {
        return subs;
    }

    public void setSubs(List<Sub> subs) {
        this.subs = subs;
    }

    public void loadSub(final LoadSubListener listener) {
        RestClient.getSubFile(getSource(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFinish(getLabel());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    subs = JSONConvert.getSubs(responseString);
                    Log.d("SIZE " + subs.size());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    subs = new ArrayList<>();
                }
                listener.onFinish(getLabel());
            }
        });
    }

    public synchronized Sub getSub(int time){
        if(subs.size() == 0) return null;
        for(Sub sub : subs){
            if(sub.equals(time)) return sub;
        }
        return null;
    }

    public synchronized Sub getSub(int time, int curPosition){
        if(subs.size() == 0) return null;
        if(curPosition < 0) curPosition = 0;
        if(subs.get(curPosition).equals(time)) return subs.get(curPosition);
        int i = curPosition + 1;
        while (i < subs.size() && i >= 0){
            if(subs.get(i).equals(time)) return subs.get(i);
            i += 1;
        }
        i = curPosition - 1;
        while (i >= 0 && i < subs.size()){
            if(subs.get(i).equals(time)) return subs.get(i);
            i -= 1;
        }
        return null;
    }

    public Hashtable getHashSubs() {
        return hashSubs;
    }

    public void setHashSubs(Hashtable hashSubs) {
        this.hashSubs = hashSubs;
    }

}
