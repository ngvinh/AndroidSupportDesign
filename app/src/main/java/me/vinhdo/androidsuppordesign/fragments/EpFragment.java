package me.vinhdo.androidsuppordesign.fragments;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import me.vinhdo.androidsuppordesign.R;
import me.vinhdo.androidsuppordesign.adapter.NumberAdapter;
import me.vinhdo.androidsuppordesign.custom.view.AutofitRecyclerView;
import me.vinhdo.androidsuppordesign.gui.MySlidingLayer;
import me.vinhdo.androidsuppordesign.gui.SlidingLayer;
import me.vinhdo.androidsuppordesign.listeners.OnEpClickListener;

/**
 * Created by vinh on 10/25/15.
 */
public class EpFragment extends MySlidingLayer{

    public EpFragment(Context context, int num, OnEpClickListener listener) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_ep, null, false);
        AutofitRecyclerView recyclerView = (AutofitRecyclerView) view.findViewById(R.id.recycler_view);
        view.findViewById(R.id.back_btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeLayer(true);
            }
        });
        recyclerView.addItemDecoration(new MarginDecoration(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new NumberAdapter(num, listener));
        setStickTo(SlidingLayer.STICK_TO_BOTTOM);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.BELOW, R.id.toolbar);
        addView(view);
        setCloseOnTapEnabled(false);
        setLayoutParams(lp);
        setSlidingEnabled(true);
    }

    class MarginDecoration extends RecyclerView.ItemDecoration {
        private int margin;

        public MarginDecoration(Context context) {
            margin = context.getResources().getDimensionPixelSize(R.dimen.item_margin);
        }

        @Override
        public void getItemOffsets(
                Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(margin, margin, margin, margin);
        }
    }
}
