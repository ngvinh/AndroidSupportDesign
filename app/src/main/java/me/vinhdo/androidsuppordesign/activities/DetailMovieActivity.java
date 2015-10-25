package me.vinhdo.androidsuppordesign.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.VideoView;
import me.vinhdo.androidsuppordesign.R;
import me.vinhdo.androidsuppordesign.api.loopj.RestClient;
import me.vinhdo.androidsuppordesign.api.loopj.parse.JSONConvert;
import me.vinhdo.androidsuppordesign.custom.view.CustomTextView;
import me.vinhdo.androidsuppordesign.custom.view.StaticGridView;
import me.vinhdo.androidsuppordesign.fragments.EpFragment;
import me.vinhdo.androidsuppordesign.fragments.SearchMovieFragment;
import me.vinhdo.androidsuppordesign.gui.CustomViewPager;
import me.vinhdo.androidsuppordesign.listeners.OnEpClickListener;
import me.vinhdo.androidsuppordesign.models.MovieDetail;
import me.vinhdo.androidsuppordesign.models.ResponseModel;
import me.vinhdo.androidsuppordesign.utils.ToastUtil;

public class DetailMovieActivity extends BaseActivty {

    @Bind(R.id.title_tv)
    CustomTextView mTitleTv;
    @Bind(R.id.imdb_rate_tv)
    CustomTextView mImdbTv;
    @Bind(R.id.backdrop)
    ImageView mBackdropIv;
    @Bind(R.id.poster_iv)
    ImageView mPosterIv;
    @Bind(R.id.description_tv)
    CustomTextView mDescriptionTv;
    @Bind(R.id.more_btn)
    CustomTextView mMoreBtn;
    @Bind(R.id.tapphim_gv)
    StaticGridView mGridView;
    @Bind(R.id.play_btn)
    FloatingActionButton mPlayBtn;

    EpFragment mEpFragment;

    private Picasso picasso;

    private int id;
    private MovieDetail movieDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getIntExtra("id", 0);
        RestClient.getDetailVideo(id, new TextHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                showProgressDialog();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                hideProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ResponseModel response = JSONConvert.getResponse(responseString);
                if (response.isSuccess()) {
                    movieDetail = JSONConvert.getMovieDetail(response.getData());
                    setContentView(R.layout.activity_detail_movie);
                    ButterKnife.bind(DetailMovieActivity.this);
                    picasso = Picasso.with(DetailMovieActivity.this);
                    ActionBar actionBar = getSupportActionBar();
                    if (actionBar != null) {
                        actionBar.setDisplayShowTitleEnabled(false);
                        actionBar.setDisplayShowHomeEnabled(false);
                        actionBar.setDisplayShowCustomEnabled(true);
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.black_50));
                        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
                        mToolbar.setTitleTextColor(Color.WHITE);
                        mToolbar.setTitle("Phim");
                        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                        if (mTabLayout != null)
                            mTabLayout.setVisibility(View.GONE);
                    }
                    initView();
                } else {
                    ToastUtil.show("Đã có lỗi xảy ra, vui lòng thử lại sau.");
                }
            }
        });
    }

    private void initView(){
        initModel();
    }

    @OnClick(R.id.play_btn)
    public void play(){
        Intent i = new Intent(DetailMovieActivity.this, PlayerActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("id", movieDetail.getId());
        i.putExtra("ep", 0);
        startActivity(i);
    }

    @OnClick(R.id.more_btn)
    public void moreDes(){
        if(mMoreBtn.getText().toString().startsWith("XEM")){
            mDescriptionTv.setMaxLines(100);
            mMoreBtn.setText("ĐÓNG");
        }else{
            mDescriptionTv.setMaxLines(3);
            mMoreBtn.setText("XEM THÊM");
        }
    }

    @OnClick(R.id.list_ep_btn)
    public void showEp(){
        if(mEpFragment != null){
            mEpFragment.openLayer(true);
        }
    }

    @Override
    public void onBackPressed() {
        if(mEpFragment != null && mEpFragment.isOpened()){
            mEpFragment.closeLayer(true);
        }else{
            super.onBackPressed();
        }
    }

    private void initModel(){
        mTitleTv.setText(movieDetail.getName());
        mImdbTv.setText(movieDetail.getNameTV());
        mDescriptionTv.setText(Html.fromHtml(movieDetail.getPlotVI()));
        RequestCreator rcBd = picasso.load(movieDetail.getBackdrop());
        if(rcBd != null){
            rcBd.fit().centerCrop();
            rcBd.into(mBackdropIv);
        }
        RequestCreator rcCv = picasso.load(movieDetail.getPoster100x149());
        if(rcCv != null){
            rcCv.fit().centerCrop();
            rcCv.into(mPosterIv);
        }

        mEpFragment = new EpFragment(this, movieDetail.getSequence(), new OnEpClickListener() {
            @Override
            public void onEpClick(int ep) {
                Intent i = new Intent(DetailMovieActivity.this, PlayerActivity.class);
                i.putExtra("id", movieDetail.getId());
                i.putExtra("ep", ep);
                startActivity(i);
            }
        });
        mDrawerLayout.addView(mEpFragment);

//        mGridView.setAdapter(new BaseAdapter() {
//            @Override
//            public int getCount() {
//                return movieDetail.getSequence();
//            }
//
//            @Override
//            public Object getItem(int position) {
//                return null;
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return 0;
//            }
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                CustomTextView tv = (CustomTextView)LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ep,parent, false);
//                tv.setText(String.valueOf(position + 1));
//                return tv;
//            }
//        });
//        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent i = new Intent(DetailMovieActivity.this, PlayerActivity.class);
//                i.putExtra("id", movieDetail.getId());
//                i.putExtra("ep", position + 1);
//                startActivity(i);
//            }
//        });
    }
}
