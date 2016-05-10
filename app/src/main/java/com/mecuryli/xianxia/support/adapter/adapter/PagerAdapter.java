package com.mecuryli.xianxia.support.adapter.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by 海飞 on 2016/5/9.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private String[] titles;

    public PagerAdapter(FragmentManager fm ,String[] titles) {
        super(fm);
        this.titles = titles;
    }


    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
