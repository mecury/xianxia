package com.mecuryli.xianxia.cache.cache.Collection;

import com.mecuryli.xianxia.cache.cache.ICache;
import com.mecuryli.xianxia.model.reading.ReadingBean;

import java.util.List;

/**
 * Created by 海飞 on 2016/5/31.
 */
public class CollectionReadingCache implements ICache<ReadingBean> {
    @Override
    public void addToCollection(ReadingBean object) {

    }

    @Override
    public void execSQL(String sql) {

    }

    @Override
    public List<ReadingBean> getmList() {
        return null;
    }

    @Override
    public boolean hasData() {
        return false;
    }

    @Override
    public void load() {

    }

    @Override
    public void loadFromCache() {

    }

    @Override
    public void cache() {

    }
}
