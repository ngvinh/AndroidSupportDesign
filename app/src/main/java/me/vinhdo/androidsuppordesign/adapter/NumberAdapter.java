package me.vinhdo.androidsuppordesign.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.vinhdo.androidsuppordesign.R;
import me.vinhdo.androidsuppordesign.listeners.OnEpClickListener;

/**
 * Created by vinh on 10/25/15.
 */
public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.TextViewHolder> {
    private int mCount;
    private int mPage = 0;
    private final static int MAX_IN_PAGE = 19;

    OnEpClickListener mListener;

    public NumberAdapter(int count, OnEpClickListener listener) {
        mCount = count;
        mPage = 0;
        this.mListener = listener;
    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ep, parent, false);
        return new TextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TextViewHolder holder, final int position) {
        if(getItemCount() == 20 && position == getItemCount() - 1){
            holder.textView.setText(">>");
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPage++;
                    notifyItemRangeChanged(0, getItemCount());
                }
            });
        }else {
            holder.textView.setText(String.valueOf(position + 1 + (mPage * MAX_IN_PAGE)));
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onEpClick(position + 1 + (mPage * MAX_IN_PAGE));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCount < (20 + (mPage * MAX_IN_PAGE)) ? (mCount + (mPage * MAX_IN_PAGE))  : 20;
    }

    class TextViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.number_tv);
        }
    }

}
