package me.vinhdo.androidsuppordesign.fragments;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import me.vinhdo.androidsuppordesign.R;
import me.vinhdo.androidsuppordesign.gui.MySlidingLayer;
import me.vinhdo.androidsuppordesign.gui.SlidingLayer;

/**
 * Created by vinh on 10/10/15.
 */
public class SearchMovieFragment extends MySlidingLayer{

    Activity mActivity;
    public SearchMovieFragment(Activity context) {
        super(context);
        mActivity = context;
        View searchView = LayoutInflater.from(context).inflate(R.layout.search_layout, null, false);
        setStickTo(SlidingLayer.STICK_TO_TOP);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.BELOW, R.id.toolbar);
        addView(searchView);
        setCloseOnTapEnabled(false);
        setLayoutParams(lp);
        setSlidingEnabled(false);
        ButterKnife.bind(this, searchView);
    }
}
