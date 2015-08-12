package me.vinhdo.androidsuppordesign;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.swipe.SwipeDismissRecyclerViewTouchListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.vinhdo.androidsuppordesign.activities.BaseActivty;
import me.vinhdo.androidsuppordesign.activities.TabLayoutActivity;
import me.vinhdo.androidsuppordesign.adapter.AdapterRView;
import me.vinhdo.androidsuppordesign.listeners.SwipeDismissTouchListener;

public class MainActivity extends BaseActivty {

    @Bind(R.id.recycler_view)
    SuperRecyclerView mRecyclerView;

    private AdapterRView mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout != null)
                    mDrawerLayout.openDrawer(mNavBar);
            }
        });
        if(mTabLayout != null){
            mTabLayout.setBackgroundColor(getResources().getColor(R.color.primary));
            mTabLayout.setTabTextColors(Color.WHITE, Color.RED);
            mTabLayout.addTab(mTabLayout.newTab().setText("Tab 1"));
            mTabLayout.addTab(mTabLayout.newTab().setText("Tab 2"));
            mTabLayout.addTab(mTabLayout.newTab().setText("Tab 3"));
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AdapterRView(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TabLayoutActivity.class));
            }
        });
        mRecyclerView.setupSwipeToDismiss(new SwipeDismissRecyclerViewTouchListener.DismissCallbacks() {
            @Override
            public boolean canDismiss(int i) {
                return true;
            }

            @Override
            public void onDismiss(RecyclerView recyclerView, int[] ints) {
            }
        });
//        mRecyclerView.swapAdapter(new AdapterRView(this), true);
    }
}
