package com.vikas.dev.searchplaces.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.vikas.dev.searchplaces.fragments.SearchIPFragment;
import com.vikas.dev.searchplaces.fragments.SearchPlacesFragment;

/**
 * Created by vikasmalhotra on 12/6/16.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs = 2;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                SearchPlacesFragment tab1 = new SearchPlacesFragment();
                return tab1;
            case 1:
                SearchIPFragment tab2 = new SearchIPFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

