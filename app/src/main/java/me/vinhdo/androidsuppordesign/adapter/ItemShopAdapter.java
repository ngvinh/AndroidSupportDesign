package me.vinhdo.androidsuppordesign.adapter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.malinskiy.superrecyclerview.swipe.BaseSwipeAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.vinhdo.androidsuppordesign.R;
import me.vinhdo.androidsuppordesign.custom.view.CustomTextView;
import me.vinhdo.androidsuppordesign.models.CateModel;
import me.vinhdo.androidsuppordesign.models.HomePageMovies;
import me.vinhdo.androidsuppordesign.models.MovieModel;

/**
 * Created by vinh on 8/11/15.
 */
public class ItemShopAdapter extends RecyclerView.Adapter<ItemShopAdapter.ViewRHolder> implements View.OnClickListener{

    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    private static final int ITEM_VIEW_TYPE_STICKHEADER = 3;

    @Override
    public void onClick(View v) {

    }

    public interface ItemShopListener{
        void onClick(View v, MovieModel movie);
    }

    private HomePageMovies mData;
    private List<Integer> mListHeaderID = new ArrayList<>();
    private ItemShopListener mListener;
    private int size;
    private Picasso picasso;

    public ItemShopAdapter(){
        mListener = new ItemShopListener() {
            @Override
            public void onClick(View v, MovieModel movie) {

            }
        };
    }

    public ItemShopAdapter(ItemShopListener listener, HomePageMovies data){
        this.mListener = listener;
        this.mData = data;
        processSize();
    }

    private void processSize(){
        mListHeaderID = new ArrayList<>();
        size = 1;
        for(CateModel cate : mData.getMoviesCates()){
            mListHeaderID.add(size);
            size += 1;
            size += cate.getNumMoviesShow();
        }
        mListHeaderID.add(size);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        picasso = Picasso.with(recyclerView.getContext());
    }

    public boolean isHeader(int p){
        if(p == 1 || p == 0) return true;
        for(int cate : mListHeaderID){
            if(p == cate)
                return true;
            if(p < cate) return false;

        }
        return false;
    }

    public int getCate(int p){
        for(int i = 1; i < mListHeaderID.size(); i++){
            int cateP = mListHeaderID.get(i);
            if(p < cateP) return i-1;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0 ) return ITEM_VIEW_TYPE_STICKHEADER;
        return isHeader(position) ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
    }

    @Override
    public ViewRHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM_VIEW_TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_shop, parent, false);
            return new ViewRHolder(v, viewType);
        }
        if(viewType == ITEM_VIEW_TYPE_HEADER){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new ViewRHolder(v, viewType);
        }
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_movie_banner, parent, false);
        return new ViewRHolder(v, viewType);
    }

    @Override
    public void onBindViewHolder(ViewRHolder holder, int position) {
        if(position == 0){
            if(holder.mSliderLayout != null && holder.isSetData) return;
            for(final MovieModel movie : mData.getMoviesBanner()){
                if(movie.getId() <= 0) continue;
                TextSliderView textSliderView = new TextSliderView(holder.itemView.getContext());
                // initialize a SliderLayout
                textSliderView
                        .description(movie.getName())
                        .image(movie.getCover())
                        .setScaleType(BaseSliderView.ScaleType.Fit);

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putInt("extra", movie.getId());
                textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        mListener.onClick(slider.getView(), movie);
                    }
                });
                holder.mSliderLayout.addSlider(textSliderView);
            }
            holder.mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
            holder.mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            holder.mSliderLayout.setCustomAnimation(new DescriptionAnimation());
            holder.mSliderLayout.setDuration(4000);
            holder.mSliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {

                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });

            holder.isSetData = true;

            return;
        }
        final int catePos = getCate(position);
        if(isHeader(position)){
            holder.tv.setText(mData.getMoviesCates().get(catePos).getName());
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int numShowed = mData.getMoviesCates().get(catePos).getNumMoviesShow();
                    mData.getMoviesCates().get(catePos).setIsExpan(true);
                    processSize();
                    notifyItemRangeInserted(mListHeaderID.get(catePos) + numShowed, mData.getMoviesCates().get(catePos).getMovies().size() - numShowed);
                }
            });
            return;
        }

        final MovieModel movie = mData.getMoviesCates().get(catePos).getMovies().get(position - 1 - mListHeaderID.get(catePos));
        holder.mLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(v, movie);
            }
        });
        holder.mNameTv.setText(movie.getNameTV());
        holder.reqCreator = picasso.load(movie.getPoster100x149());
        if(holder.reqCreator != null){
            holder.reqCreator.fit().centerCrop();
            holder.reqCreator.into(holder.mImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return size;
    }

    static class ViewRHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_shop_ll)
        LinearLayout mLL;

        @Bind(R.id.item_img)
        ImageView mImage;

        @Bind(R.id.name_movie_tv)
        CustomTextView mNameTv;

        TextView tv;

        SliderLayout mSliderLayout;
        boolean isSetData = false;

        RequestCreator reqCreator = null;

        public ViewRHolder(View itemView, int type) {
            super(itemView);
            if(type == ITEM_VIEW_TYPE_HEADER){
                tv = (TextView) itemView.findViewById(R.id.list_item_text);
                return;
            }
            if(type == ITEM_VIEW_TYPE_STICKHEADER) {
                mSliderLayout = (SliderLayout) itemView.findViewById(R.id.slider);
                return;
            }
            ButterKnife.bind(this, itemView);
        }
    }

}
