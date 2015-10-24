package me.vinhdo.androidsuppordesign.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import me.vinhdo.androidsuppordesign.AppApplication;
import me.vinhdo.androidsuppordesign.R;
import me.vinhdo.androidsuppordesign.api.loopj.RestClient;
import me.vinhdo.androidsuppordesign.api.loopj.parse.JSONConvert;
import me.vinhdo.androidsuppordesign.models.HDVConfig;
import me.vinhdo.androidsuppordesign.models.ResponseModel;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        RestClient.getHDVConfig(new TextHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                HDVConfig config = JSONConvert.getHDVConfig(responseString);
                AppApplication.setHdvConfig(config);
                if (TextUtils.isEmpty(AppApplication.getToken()) || AppApplication.getToken().equals("")) {
                    RestClient.getToken(new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            ResponseModel responseModel = JSONConvert.getResponse(responseString);
                            if (responseModel.isSuccess()) {
                                String token = "";
                                try {
                                    token = new JSONObject(new JSONObject(responseModel.getData()).getString("r")).getString("token");
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                if (!TextUtils.isEmpty(token)) {
                                    AppApplication.setToken(token);
                                }
                                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                                finish();
                            }
                        }
                    });
                }else {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    finish();
                }
            }
        });
    }
}
