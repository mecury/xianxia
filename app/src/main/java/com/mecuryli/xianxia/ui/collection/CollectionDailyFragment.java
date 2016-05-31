package com.mecuryli.xianxia.ui.collection;

import android.support.v7.widget.RecyclerView;

import com.mecuryli.xianxia.cache.cache.Collection.CollectionDailyCache;
import com.mecuryli.xianxia.support.CONSTANT;
import com.mecuryli.xianxia.support.adapter.DailyAdapter;
import com.mecuryli.xianxia.ui.support.BaseListFragment;

/**
 * Created by 海飞 on 2016/5/31.
 */
public class CollectionDailyFragment extends BaseListFragment {
    @Override
    protected void onCreateCache() {
        cache = new CollectionDailyCache();
    }

    @Override
    protected RecyclerView.Adapter bindAdapter() {
        return new DailyAdapter(getContext(),cache);
    }

    @Override
    protected void loadFromNet() {
        handler.sendEmptyMessage(CONSTANT.ID_FAILURE);
    }

    @Override
    protected void loadFromCache() {
        cache.loadFromCache();
    }

    @Override
    protected boolean hasData() {
        return cache.hasData();
    }

    @Override
    protected void getArgs() {

    }
}
