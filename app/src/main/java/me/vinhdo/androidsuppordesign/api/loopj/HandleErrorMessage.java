package me.vinhdo.androidsuppordesign.api.loopj;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

import me.vinhdo.androidsuppordesign.config.ApiConfig;

/**
 * Created by vietthangif on 1/19/2015.
 * <p/>
 * Parse error message from server to show alert
 */
public class HandleErrorMessage implements IStrategyHandleError {
    @Override
    public boolean handleError(int statusCode, Header[] headers, String responseString, Throwable throwable, TextHttpResponseHandler originHandler) {
        Log.d("HDVIET handle E","ERROR ================================");
        Log.d("HDVIET handle E","status: " + statusCode);
        Log.d("HDVIET handle E","response: " + responseString);
        Log.d("HDVIET handle E","ERROR ================================");
        if (originHandler != null) {
            originHandler.onFailure(statusCode, headers, responseString, throwable);
        }

        return true;
    }
}
