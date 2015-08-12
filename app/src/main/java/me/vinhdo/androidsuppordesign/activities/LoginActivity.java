package me.vinhdo.androidsuppordesign.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.vinhdo.androidsuppordesign.R;

public class LoginActivity extends BaseActivty {

    @OnClick(R.id.login_fb_btn)
    public void login(){
        startActivity(new Intent(LoginActivity.this, ShopActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
}
