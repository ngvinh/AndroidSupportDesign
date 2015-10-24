package me.vinhdo.androidsuppordesign.api.loopj;

import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public interface IStrategyHandleError {
    boolean handleError(int statusCode, Header[] headers,
                        String responseString, Throwable throwable, TextHttpResponseHandler originHandler);
}
