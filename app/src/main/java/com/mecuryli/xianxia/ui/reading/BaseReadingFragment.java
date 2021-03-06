package com.mecuryli.xianxia.ui.reading;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.api.ReadingApi;
import com.mecuryli.xianxia.support.adapter.PagerAdapter;
import com.mecuryli.xianxia.ui.support.AbsTopNavigationFragment;

/**
 * Created by 海飞 on 2016/5/11.
 */
public class BaseReadingFragment extends AbsTopNavigationFragment {

    private PagerAdapter pagerAdapter;

    @Override
    protected PagerAdapter initPagerAdapter() {
        pagerAdapter = new PagerAdapter(getChildFragmentManager(), ReadingApi.Tag_Titles){
            @Override
            public Fragment getItem(int position) {
                ReadingFragment fragment = new ReadingFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(getString(R.string.id_pos), position);
                bundle.putString(getString(R.string.id_category), ReadingApi.Tag_Titles[position]);
                fragment.setArguments(bundle);
                return fragment;
            }
        };
        return pagerAdapter;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (getChildFragmentManager().getFragments()!=null){
            getChildFragmentManager().getFragments().clear();
        }
    }
}
