package com.mecuryli.xianxia.cache.cache;

import java.util.List;

/**
 * Created by 海飞 on 2016/5/30.
 */
//将BaseCahce中的方法抽象了出来
public interface ICache<T> {
    void addToCollection(T object);
    void execSQL(String sql);
    List<T> getmList();
    boolean hasData();
    void load();
}
