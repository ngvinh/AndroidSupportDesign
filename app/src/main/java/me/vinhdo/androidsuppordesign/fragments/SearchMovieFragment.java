package me.vinhdo.androidsuppordesign.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import me.vinhdo.androidsuppordesign.R;
import me.vinhdo.androidsuppordesign.activities.DetailMovieActivity;
import me.vinhdo.androidsuppordesign.api.loopj.RestClient;
import me.vinhdo.androidsuppordesign.api.loopj.parse.JSONConvert;
import me.vinhdo.androidsuppordesign.custom.view.CustomEditText;
import me.vinhdo.androidsuppordesign.custom.view.MyLinearLayoutManager;
import me.vinhdo.androidsuppordesign.gui.MySlidingLayer;
import me.vinhdo.androidsuppordesign.gui.SlidingLayer;
import me.vinhdo.androidsuppordesign.listeners.ItemClickListenner;
import me.vinhdo.androidsuppordesign.listeners.OnDrawableClickListener;
import me.vinhdo.androidsuppordesign.models.MovieModel;
import me.vinhdo.androidsuppordesign.models.ResponseModel;
import me.vinhdo.androidsuppordesign.utils.Log;

/**
 * Created by vinh on 10/10/15.
 */
public class SearchMovieFragment extends MySlidingLayer{

    @Bind(R.id.search_edt)
    CustomEditText mSearchEdt;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.not_found_tv)
    TextView mNotfountTv;

    List<MovieModel> listMovies;
    RequestHandle mSearchRequest;

    @OnClick(R.id.close_btn)
    public void close(){
        mRecyclerView.setAdapter(null);
        mSearchEdt.setText("");
        closeLayer(true);
    }

    public SearchMovieFragment(Context context) {
        super(context);
        View searchView = LayoutInflater.from(context).inflate(R.layout.search_layout, null, false);
        setStickTo(SlidingLayer.STICK_TO_TOP);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        addView(searchView);
        setCloseOnTapEnabled(false);
        setLayoutParams(lp);
        setSlidingEnabled(false);
        ButterKnife.bind(this, searchView);
        initViews();
    }

    @Override
    public void openLayer(boolean smoothAnim) {
        super.openLayer(smoothAnim);
//        if(mSearchEdt != null){
//            mSearchEdt.requestFocus();
//        }
    }

    private void initViews(){
        mRecyclerView.setLayoutManager(new MyLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mSearchEdt.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    mSearchEdt.setText("");
                    mRecyclerView.setAdapter(null);
                    closeLayer(true);
                    return true;
                }
                return false;
            }
        });

        mSearchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mSearchRequest != null) {
                    mSearchRequest.cancel(true);
                    mNotfountTv.setVisibility(GONE);
                }
                if (count >= 3) {
                    mSearchRequest = RestClient.search(s.toString().trim().toString(), new TextHttpResponseHandler() {
                        @Override
                        public void onCancel() {
                            super.onCancel();
                        }

                        @Override
                        public void onStart() {
                            super.onStart();
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            mRecyclerView.setAdapter(null);
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            if (mSearchEdt.getText().toString().trim().length() < 3) {
                                return;
                            }
                            ResponseModel responseModel = JSONConvert.getResponse(responseString);
                            if (responseModel.isSuccess()) {
                                try {
                                    JSONArray ja = new JSONArray(responseModel.getData());
                                    if(ja != null && ja.length() > 0){
                                        JSONObject jo = ja.getJSONObject(0);
                                        listMovies = JSONConvert.getMovies(jo.getString("Data"));
                                        mRecyclerView.setAdapter(new SearchAdapter(listMovies, new ItemClickListenner() {
                                            @Override
                                            public void onClick(int p) {
                                                Intent i = new Intent(getContext(), DetailMovieActivity.class);
                                                i.putExtra("id",listMovies.get(p).getId());
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                getContext().startActivity(i);
                                            }
                                        }));
                                        if(listMovies.size() == 0){
                                            mNotfountTv.setVisibility(VISIBLE);
                                        }
                                    }
                                }catch(Exception ex){
                                    ex.printStackTrace();
                                    mNotfountTv.setVisibility(VISIBLE);
                                }
                            }
                        }
                    });
                } else {
                    mRecyclerView.setAdapter(null);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        List<MovieModel> mData;
        ItemClickListenner mListener;

        public SearchAdapter(List<MovieModel> data){
            this.mData = data;
        }

        public SearchAdapter(List<MovieModel> data, ItemClickListenner l){
            this.mData = data;
            this.mListener = l;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView view = new TextView(parent.getContext());
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0,10,10);
            view.setLayoutParams(lp);
            view.setPadding(10,10,10,10);
            view.setTextColor(Color.GRAY);
            view.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_more, 0);
            view.setCompoundDrawablePadding(10);
            view.setBackgroundResource(R.drawable.bg_tranf_selector);
            return new RecyclerView.ViewHolder(view) {
                @Override
                public String toString() {
                    return super.toString();
                }
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ((TextView)holder.itemView).setText(mData.get(position).getName()
            );
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null)
                        mListener.onClick(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }
    }
}
