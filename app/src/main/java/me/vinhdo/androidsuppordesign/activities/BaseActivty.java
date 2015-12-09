package me.vinhdo.androidsuppordesign.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.vinhdo.androidsuppordesign.R;
import me.vinhdo.androidsuppordesign.custom.view.ScrimInsetsScrollView;
import me.vinhdo.androidsuppordesign.fragments.MenuSliding;

/**
 * Created by vinh on 8/6/15.
 */
public abstract class BaseActivty extends AppCompatActivity{

    // Primary toolbar and drawer toggle
    protected Toolbar mToolbar;
    protected MenuSliding mNavBar;
    protected LinearLayout mContentNavLl;
    protected DrawerLayout mDrawerLayout;
    protected TabLayout mTabLayout;
    protected BaseActivty mBaseActivity;

    protected ProgressDialog mProgressDialog;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getActionBarToolbar();
        if(mToolbar != null)
            setupActionBar();
        setupNarBar();
        setupTabLayout();
    }

    protected Toolbar getActionBarToolbar() {
        if (mToolbar == null) {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (mToolbar != null) {
                setSupportActionBar(mToolbar);
            }
        }
        return mToolbar;
    }

    private void setupActionBar(){
        mToolbar.setBackgroundColor(getResources().getColor(R.color.primary));
        mToolbar.setTitle(getTitle());
        mToolbar.setTitleTextColor(Color.BLACK);
        mToolbar.setNavigationIcon(R.drawable.ic_mp_move);
    }

    protected void setupNarBar(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(mDrawerLayout == null) return;
        mNavBar = new MenuSliding(this);
        mDrawerLayout.addView(mNavBar);
        if(mNavBar == null) return;
        mContentNavLl = (LinearLayout)mNavBar.findViewById(R.id.content_nav_ll);
    }
    protected void setupTabLayout(){
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if(mTabLayout == null) return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaseActivity = this;
    }

    public void showProgressDialog() {
        try {
            mProgressDialog = new ProgressDialog(mBaseActivity, ProgressDialog.THEME_HOLO_LIGHT);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showProgressDialog(String message) {
        try {
            mProgressDialog = new ProgressDialog(mBaseActivity);
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            try {
                mProgressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(mNavBar != null && mNavBar.isOpened()){
            mNavBar.closeLayer(true);
            return;
        }
        super.onBackPressed();
    }
}
