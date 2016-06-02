package com.mecuryli.xianxia.cache.cache;

import java.util.List;

/**
 * Created by 海飞 on 2016/5/30.
 */
//将BaseCahce中的方法抽象了出来
public interface ICache<T> {
    void addToCollection(T object); //将object加入“收藏”中
    void execSQL(String sql);   //执行sql语句
    List<T> getmList();     //获得mlist
    boolean hasData();         //判断mlist是否有数据
    void load();            //由网络中加载数据
    void loadFromCache();   //由缓存中加载数据
    void cache();   //缓存
}
