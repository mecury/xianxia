package com.mecuryli.xianxia.ui.reading;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mecuryli.xianxia.api.ReadingApi;
import com.mecuryli.xianxia.support.adapter.adapter.PagerAdapter;
import com.mecuryli.xianxia.ui.AbsTopNavigationFragment;

/**
 * Created by 海飞 on 2016/5/11.
 */
public class BaseReadingFragment extends AbsTopNavigationFragment {

    private PagerAdapter pagerAdapter;

    @Override
    protected PagerAdapter initPagerAdapter() {
        pagerAdapter = new PagerAdapter(getFragmentManager(), ReadingApi.Tag_Titles){
            @Override
            public Fragment getItem(int position) {
                ReadingFragment fragment = new ReadingFragment();
                Bundle bundle = new Bundle();
                bundle.putString("pos", position + "");
                fragment.setArguments(bundle);
                return fragment;
            }
        };
        return pagerAdapter;
    }
}
