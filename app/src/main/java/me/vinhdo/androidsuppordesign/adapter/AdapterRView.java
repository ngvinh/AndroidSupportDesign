package me.vinhdo.androidsuppordesign.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.swipe.BaseSwipeAdapter;
import com.malinskiy.superrecyclerview.swipe.SwipeLayout;

import me.vinhdo.androidsuppordesign.R;
import me.vinhdo.androidsuppordesign.activities.BaseActivty;
import me.vinhdo.androidsuppordesign.activities.TabLayoutActivity;

/**
 * Created by vinh on 8/6/15.
 */
public class AdapterRView extends BaseSwipeAdapter<AdapterRView.ViewRHolder> {

    BaseActivty mActivity;

    public AdapterRView(BaseActivty activty){
        this.mActivity = activty;
    }

    @Override
    public ViewRHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_swipe_layout, parent, false);
        SwipeLayout sl = new SwipeLayout(parent.getContext());
        TextView tv = new TextView(parent.getContext());
        LinearLayout ls = new LinearLayout(parent.getContext());
        if (viewType == 0) {
            tv.setTextColor(Color.GREEN);
            tv.setTextSize(20);
            tv.setText("haha hehe hhh");
        } else if (viewType == 1) {
            tv.setTextSize(25);
            tv.setTextColor(Color.BLACK);
            tv.setText("haha .......");
        } else {
            tv.setTextSize(29);
            tv.setTextColor(Color.BLUE);
            tv.setText("haha .ddd ddddfdfdfdf......");
        }
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(new Intent(mActivity, TabLayoutActivity.class));
            }
        });
        ls.addView(tv);
        sl.addView(ls);
        return new ViewRHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewRHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == 0) {
            holder.tv.setTextColor(Color.GREEN);
            holder.tv.setTextSize(20);
            holder.tv.setText("haha hehe hhh");
        } else if (viewType == 1) {
            holder.tv.setTextSize(25);
            holder.tv.setTextColor(Color.BLACK);
            holder.tv.setText("haha .......");
        } else {
            holder.tv.setTextSize(29);
            holder.tv.setTextColor(Color.BLUE);
            holder.tv.setText("haha .ddd ddddfdfdfdf......");
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position % 3;
    }

    @Override
    public int getItemCount() {
        return 200;
    }

    static class ViewRHolder extends BaseSwipeAdapter.BaseSwipeableViewHolder {

        public TextView tv;
        public ViewRHolder(View itemView) {
            super(itemView);
            tv = (TextView)itemView.findViewById(R.id.position);
        }
    }

    public void remove(int p){
        notifyItemRemoved(p);
    }
}
