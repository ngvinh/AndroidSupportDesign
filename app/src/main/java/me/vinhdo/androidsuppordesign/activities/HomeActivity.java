package me.vinhdo.androidsuppordesign.activities;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import me.vinhdo.androidsuppordesign.AppApplication;
import me.vinhdo.androidsuppordesign.MainActivity;
import me.vinhdo.androidsuppordesign.R;
import me.vinhdo.androidsuppordesign.adapter.ItemShopAdapter;
import me.vinhdo.androidsuppordesign.adapter.TransformerAdapter;
import me.vinhdo.androidsuppordesign.api.loopj.RestClient;
import me.vinhdo.androidsuppordesign.api.loopj.parse.JSONConvert;
import me.vinhdo.androidsuppordesign.custom.view.InsetDecoration;
import me.vinhdo.androidsuppordesign.fragments.SearchMovieFragment;
import me.vinhdo.androidsuppordesign.models.HDVConfig;
import me.vinhdo.androidsuppordesign.models.HomePageMovies;
import me.vinhdo.androidsuppordesign.models.MovieModel;
import me.vinhdo.androidsuppordesign.models.ResponseModel;
import me.vinhdo.androidsuppordesign.utils.ToastUtil;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.HashMap;

public class HomeActivity extends BaseActivty implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mMainLayout;
    protected SearchMovieFragment searchFragment;

    private HomePageMovies mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        searchFragment = new SearchMovieFragment(this);
        mMainLayout.addView(searchFragment);
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(R.layout.custom_search_actionbar);
            actionBar.getCustomView().findViewById(R.id.search_edt).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchFragment.openLayer(true);
                }
            });
            mToolbar.setBackgroundColor(getResources().getColor(R.color.red_actionbar));
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (mDrawerLayout != null)
//                        mDrawerLayout.openDrawer(mNavBar);
                }
            });
            if(mTabLayout != null)
                mTabLayout.setVisibility(View.GONE);
        }
        RestClient.getHDVConfig(new TextHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                showProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                HDVConfig config = JSONConvert.getHDVConfig(responseString);
                AppApplication.setHdvConfig(config);
                RestClient.getHomePage(new TextHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        hideProgressDialog();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        hideProgressDialog();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        ResponseModel response = JSONConvert.getResponse(responseString);
                        if (response.isSuccess()) {
                            mData = JSONConvert.getHomePageData(response.getData());
                            processData();
                        }
                    }
                });
            }
        });
//        ListView l = (ListView)findViewById(R.id.transformers);
//        l.setAdapter(new TransformerAdapter(this));
//        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mDemoSlider.setPresetTransformer(((TextView) view).getText().toString());
//                Toast.makeText(HomeActivity.this, ((TextView) view).getText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });


    }

    private void processData(){
        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

//        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
//        file_maps.put("Hannibal",R.drawable.hannibal);
//        file_maps.put("Big Bang Theory",R.drawable.bigbang);
//        file_maps.put("House of Cards",R.drawable.house);
//        file_maps.put("Game of Thrones", R.drawable.game_of_thrones);
        final GridLayoutManager manager = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(manager);
//        mRecyclerView.setHasFixedSize(true);
//        View v = LayoutInflater.from(this).inflate(R.layout.header_list_shop_activity, null, false);
//        mRecyclerView.addView(v);
        final ItemShopAdapter adapter = new ItemShopAdapter(new ItemShopAdapter.ItemShopListener() {
            @Override
            public void onClick(View v, MovieModel movie) {
                if(movie.getId() <= 0) return;
                Intent i = new Intent(HomeActivity.this, DetailMovieActivity.class);
                i.putExtra("id", movie.getId());
                startActivity(i);
            }
        }, mData);
        mRecyclerView.setAdapter(adapter);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.isHeader(position) ? manager.getSpanCount() : 1;
            }

            @Override
            public int getSpanIndex(int position, int spanCount) {
                return super.getSpanIndex(position, spanCount);
            }
        });
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    @Override
    public void onBackPressed() {
        if(searchFragment != null && searchFragment.isOpened()){
            searchFragment.closeLayer(true);
        }else
        super.onBackPressed();
    }
}
