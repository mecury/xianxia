package com.mecuryli.xianxia.ui.support;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.support.adapter.PagerAdapter;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

/**
 * Created by 海飞 on 2016/5/9.
 * 侧滑的抽屉列表
 */
public abstract class AbsTopNavigationFragment extends android.support.v4.app.Fragment {

    protected  View parentView;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private SmartTabLayout smartTablayout;
    private Toolbar toolbar;
    protected abstract PagerAdapter initPagerAdapter();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = View.inflate(getContext(), R.layout.layout_top_navigation,null);
        viewPager = (ViewPager) parentView.findViewById(R.id.inner_viewPager);
        smartTablayout = (SmartTabLayout) getActivity().findViewById(R.id.tab_layout);
        smartTablayout.setVisibility(View.VISIBLE);

        pagerAdapter = initPagerAdapter();
        viewPager.setAdapter(pagerAdapter);

        smartTablayout.setViewPager(viewPager);
        return parentView;
    }

}
