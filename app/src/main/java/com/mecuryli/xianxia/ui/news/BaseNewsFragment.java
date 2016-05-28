package com.mecuryli.xianxia.ui.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.api.NewsApi;
import com.mecuryli.xianxia.support.Utils;
import com.mecuryli.xianxia.support.adapter.PagerAdapter;
import com.mecuryli.xianxia.ui.support.AbsTopNavigationFragment;

/**
 * Created by 海飞 on 2016/5/9.
 */
public class BaseNewsFragment extends AbsTopNavigationFragment {

    private PagerAdapter pagerAdapter; //tab各个标题栏的适配
    private String[] name;
    private String[] url;
    @Override
    protected PagerAdapter initPagerAdapter() {
        name = NewsApi.getNewsTitle();
        url = NewsApi.getNewsUrl();

        pagerAdapter = new PagerAdapter(getFragmentManager(),name){
            @Override
            public Fragment getItem(int position) {
                Utils.DLog(name.length + " " + url.length);
                NewsFragment fragment = new NewsFragment();
                Bundle bundle = new Bundle();
                bundle.putString(getString(R.string.id_url),url[position]);
                bundle.putString(getString(R.string.id_category), name[position]);
                fragment.setArguments(bundle);
                return fragment;
            }
        };
        return pagerAdapter;
    }
}
