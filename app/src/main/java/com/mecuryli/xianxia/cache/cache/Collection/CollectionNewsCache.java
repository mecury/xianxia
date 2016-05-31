package com.mecuryli.xianxia.cache.cache.Collection;

import com.mecuryli.xianxia.cache.cache.ICache;
import com.mecuryli.xianxia.model.news.NewsBean;

import java.util.List;

/**
 * Created by 海飞 on 2016/5/31.
 */
public class CollectionNewsCache implements ICache<NewsBean> {
    @Override
    public void addToCollection(NewsBean object) {

    }

    @Override
    public void execSQL(String sql) {

    }

    @Override
    public List<NewsBean> getmList() {
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
