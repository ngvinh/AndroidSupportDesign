package me.vinhdo.androidsuppordesign.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import me.vinhdo.androidsuppordesign.R;
import me.vinhdo.androidsuppordesign.gui.MySlidingLayer;
import me.vinhdo.androidsuppordesign.gui.SlidingLayer;

/**
 * Created by vinh on 11/15/15.
 */
public class MenuSliding extends MySlidingLayer{
    public MenuSliding(Context context) {
        super(context);
        View searchView = LayoutInflater.from(context).inflate(R.layout.menu_layout, null, false);
        setStickTo(SlidingLayer.STICK_TO_LEFT);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        addView(searchView);
        setCloseOnTapEnabled(true);
        setLayoutParams(lp);
        setSlidingEnabled(true);
        ButterKnife.bind(this, searchView);
    }
}
