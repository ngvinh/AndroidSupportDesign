package me.vinhdo.androidsuppordesign.adapter;

/**
 * Created by vinh on 8/6/15.
 */

import android.support.v4.app.FragmentStatePagerAdapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import me.vinhdo.androidsuppordesign.fragments.ItemFragment;

public class ItemPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ItemPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ItemFragment tab1 = new ItemFragment();
                return tab1;
            case 1:
                ItemFragment tab2 = new ItemFragment();
                return tab2;
            case 2:
                ItemFragment tab3 = new ItemFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}