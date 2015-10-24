package me.vinhdo.androidsuppordesign.activities;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.TextHttpResponseHandler;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import me.vinhdo.androidsuppordesign.AppApplication;
import me.vinhdo.androidsuppordesign.R;
import me.vinhdo.androidsuppordesign.api.loopj.RestClient;
import me.vinhdo.androidsuppordesign.api.loopj.parse.JSONConvert;
import me.vinhdo.androidsuppordesign.models.HDVConfig;
import me.vinhdo.androidsuppordesign.service.BackgroundService;

public class LoginActivity extends BaseActivty {

    @OnClick(R.id.login_fb_btn)
    public void login(){
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
}
