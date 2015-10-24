package me.vinhdo.androidsuppordesign.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import me.vinhdo.androidsuppordesign.models.MovieModel;

/**
 * Created by vinh on 10/8/15.
 */
public class MoviesAdapter extends BaseAdapter{

    private List<MovieModel> data;
    private boolean isExpan = false;

    public MoviesAdapter(Context context, List<MovieModel> data){
        this.data = data;
    }

    public void setExpan(boolean is){
        isExpan = is;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data == null ? 0 : (!isExpan && data.size() >= 4 ) ? 4 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data == null ? null : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
