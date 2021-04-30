package com.pbph.yuguo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;

import com.pbph.yuguo.activity.InviteActivity;

import java.util.ArrayList;

public class InviteFragmentPageAdapter extends FragmentPagerAdapter {

    private ArrayList<? extends Fragment> fragments = new ArrayList<>();

    public InviteFragmentPageAdapter(FragmentManager fm, ArrayList<? extends Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

}
