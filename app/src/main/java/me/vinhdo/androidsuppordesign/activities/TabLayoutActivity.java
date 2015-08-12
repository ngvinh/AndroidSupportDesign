package me.vinhdo.androidsuppordesign.activities;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.vinhdo.androidsuppordesign.R;
import me.vinhdo.androidsuppordesign.adapter.ItemPagerAdapter;

public class TabLayoutActivity extends BaseActivty {

    @Bind(R.id.pager)
    ViewPager mPager;

    ItemPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        ButterKnife.bind(this);
        if(mTabLayout != null){
            mTabLayout.setBackgroundColor(getResources().getColor(R.color.primary));
            mTabLayout.setTabTextColors(Color.WHITE, Color.RED);
            mTabLayout.addTab(mTabLayout.newTab().setText("Tab 1"));
            mTabLayout.addTab(mTabLayout.newTab().setText("Tab 2"));
            mTabLayout.addTab(mTabLayout.newTab().setText("Tab 3"));
        }

        mAdapter = new ItemPagerAdapter(getSupportFragmentManager(), 3);
        mPager.setAdapter(mAdapter);
//        mTabLayout.setTabsFromPagerAdapter(mAdapter);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }
}
