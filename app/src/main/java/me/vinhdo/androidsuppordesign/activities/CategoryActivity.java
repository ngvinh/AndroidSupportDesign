package me.vinhdo.androidsuppordesign.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.vinhdo.androidsuppordesign.R;
import me.vinhdo.androidsuppordesign.api.loopj.RestClient;

public class CategoryActivity extends BaseActivty {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initViews();
    }

    private void initViews(){
        initModels();
    }

    private void initModels(){

    }
}
