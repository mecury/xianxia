package com.mecuryli.xianxia.cache.cache;

import android.content.Context;

import java.util.List;

/**
 * Created by 海飞 on 2016/5/26.
 */
public class ScienceCache extends BaseCache {

    protected ScienceCache(Context context) {
        super(context);
    }

    @Override
    protected void putData(List<? extends Object> list) {

    }

    @Override
    protected void putData(Object object) {

    }

    @Override
    public List<Object> loadFromCache() {
        return null;
    }

}
