package me.vinhdo.androidsuppordesign.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.swipe.BaseSwipeAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.vinhdo.androidsuppordesign.R;

/**
 * Created by vinh on 8/11/15.
 */
public class ItemShopAdapter extends RecyclerView.Adapter<ItemShopAdapter.ViewRHolder>{


    public interface ItemShopListener{
        void onClick(View v);
    }

    private ItemShopListener mListener;

    public ItemShopAdapter(){
        mListener = new ItemShopListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

    public ItemShopAdapter(ItemShopListener listener){
        this.mListener = listener;
    }

    @Override
    public ViewRHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_shop, parent, false);
        return new ViewRHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewRHolder holder, int position) {
        holder.mLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(v);
            }
        });
        switch (position % 6){
            case 0:
                holder.mImage.setImageResource(R.drawable.ic_cam);
                break;
            case 1:
                holder.mImage.setImageResource(R.drawable.item_shop);
                break;
            case 2:
                holder.mImage.setImageResource(R.drawable.ic_rau);
                break;
            case 3:
                holder.mImage.setImageResource(R.drawable.ic_ot);
                break;
            case 4:
                holder.mImage.setImageResource(R.drawable.ic_thia);
                break;
            case 5:
                    holder.mImage.setImageResource(R.drawable.ic_coc);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    static class ViewRHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_shop_ll)
        LinearLayout mLL;

        @Bind(R.id.item_img)
        ImageView mImage;

        public ViewRHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
