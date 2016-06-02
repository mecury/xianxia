package com.mecuryli.xianxia.ui.collection;

import android.support.v7.widget.RecyclerView;

import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.cache.cache.Collection.CollectionDailyCache;
import com.mecuryli.xianxia.cache.cache.Collection.CollectionNewsCache;
import com.mecuryli.xianxia.cache.cache.Collection.CollectionReadingCache;
import com.mecuryli.xianxia.cache.cache.Collection.CollectionScienceCache;
import com.mecuryli.xianxia.support.CONSTANT;
import com.mecuryli.xianxia.support.adapter.DailyAdapter;
import com.mecuryli.xianxia.support.adapter.NewsAdapter;
import com.mecuryli.xianxia.support.adapter.ReadingAdapter;
import com.mecuryli.xianxia.support.adapter.ScienceAdapter;
import com.mecuryli.xianxia.ui.support.BaseListFragment;

/**
 * Created by 海飞 on 2016/5/31.
 */
public class CollectionFragment extends BaseListFragment {

    private int pos;

    @Override
    protected void setLayout() {
        mLayout = R.layout.layout_collection_list;
    }

    @Override
    protected void onCreateCache() {
        switch (pos){
            case 0:
                cache = new CollectionDailyCache(handler);
                break;
            case 1:
                cache = new CollectionReadingCache(handler);
                break;
            case 2:
                cache = new CollectionNewsCache(handler);
                break;
            case 3:
                cache = new CollectionScienceCache(handler);
                break;
        }
    }

    @Override
    protected RecyclerView.Adapter bindAdapter() {

        switch (pos){
            case 0:
                return new DailyAdapter(getContext(),cache);
            case 1:
                return new ReadingAdapter(getContext(),cache);
            case 2:
                return new NewsAdapter(getContext(),cache);
            case 3:
                return new ScienceAdapter(getContext(),cache);
        }
        return null;
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
        pos = getArguments().getInt(getString(R.string.id_pos));
    }
}
