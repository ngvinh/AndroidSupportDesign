package me.vinhdo.androidsuppordesign.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.vinhdo.androidsuppordesign.R;
import me.vinhdo.androidsuppordesign.adapter.AdapterRView;
import me.vinhdo.androidsuppordesign.adapter.ItemShopAdapter;
import me.vinhdo.androidsuppordesign.custom.view.GridDividerDecoration;
import me.vinhdo.androidsuppordesign.custom.view.InsetDecoration;

public class ShopActivity extends BaseActivty {

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(R.layout.custom_search_actionbar);
            mToolbar.setBackgroundColor(getResources().getColor(R.color.red_actionbar));
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDrawerLayout != null)
                        mDrawerLayout.openDrawer(mNavBar);
                }
            });
            if(mTabLayout != null)
                mTabLayout.setVisibility(View.GONE);
        }
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mRecyclerView.addItemDecoration(new InsetDecoration(this));
//        View v = LayoutInflater.from(this).inflate(R.layout.header_list_shop_activity, null, false);
//        mRecyclerView.addView(v);
        mRecyclerView.setAdapter(new ItemShopAdapter(new ItemShopAdapter.ItemShopListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShopActivity.this, SelectedItemActivity.class));
            }
        }));
        RecyclerViewHeader header = RecyclerViewHeader.fromXml(this, R.layout.header_list_shop_activity);
        header.attachTo(mRecyclerView);
    }

}
