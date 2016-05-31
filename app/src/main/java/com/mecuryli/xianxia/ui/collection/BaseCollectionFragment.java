package com.mecuryli.xianxia.ui.collection;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.support.adapter.PagerAdapter;
import com.mecuryli.xianxia.ui.support.AbsTopNavigationFragment;

/**
 * Created by 海飞 on 2016/5/30.
 */
public class BaseCollectionFragment extends AbsTopNavigationFragment {
    private PagerAdapter pagerAdapter;
    @Override
    protected PagerAdapter initPagerAdapter() {
        String [] tabTitles = {getContext().getString(R.string.daily),getContext().getString(R.string.reading),
            getContext().getString(R.string.news),getContext().getString(R.string.science)};
        pagerAdapter = new PagerAdapter(getFragmentManager(), tabTitles){
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = new CollectionFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(getString(R.string.id_pos),position);
                fragment.setArguments(bundle);
                return fragment;
            }
        };
        return pagerAdapter;
    }
}
