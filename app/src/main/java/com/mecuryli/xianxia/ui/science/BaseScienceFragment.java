package com.mecuryli.xianxia.ui.science;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.api.ScienceApi;
import com.mecuryli.xianxia.support.adapter.PagerAdapter;
import com.mecuryli.xianxia.ui.support.AbsTopNavigationFragment;

/**
 * Created by 海飞 on 2016/5/16.
 */
public class BaseScienceFragment extends AbsTopNavigationFragment {

    private PagerAdapter pagerAdapter;
    @Override
    protected PagerAdapter initPagerAdapter() {
        pagerAdapter = new PagerAdapter(getFragmentManager(), ScienceApi.channel_title){
            @Override
            public Fragment getItem(int position) {
                ScienceFragment fragment = new ScienceFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(getString(R.string.id_pos),position);
                fragment.setArguments(bundle);
                return fragment;
            }
        };
        return pagerAdapter;
    }
}
