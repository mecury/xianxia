package com.mecuryli.xianxia.ui.daily;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mecuryli.xianxia.api.DailyApi;
import com.mecuryli.xianxia.support.adapter.PagerAdapter;
import com.mecuryli.xianxia.ui.support.AbsTopNavigationFragment;

/**
 * Created by 海飞 on 2016/5/18.
 */
public class BaseDailyFragment extends AbsTopNavigationFragment {

    private PagerAdapter pagerAdapter;
    private String[] list = {"知乎日报"};

    @Override
    protected PagerAdapter initPagerAdapter() {
        pagerAdapter = new PagerAdapter(getFragmentManager(), list){
            @Override
            public Fragment getItem(int position) {
                DailyFragment dailyFragment = new DailyFragment();
                Bundle bundle = new Bundle();
                bundle.putString("url", DailyApi.newsLatest);
                dailyFragment.setArguments(bundle);
                return dailyFragment;
            }
        };
        return pagerAdapter;
    }
}
