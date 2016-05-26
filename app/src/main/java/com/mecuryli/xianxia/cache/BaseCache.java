package com.mecuryli.xianxia.cache;

import android.content.Context;

import java.util.List;

/**
 * Created by 海飞 on 2016/5/26.
 */
public abstract class BaseCache {
    protected Context mContext;
    protected DatabaseHelper mHelper;
    protected BaseCache(Context context){
        mContext = context;
        mHelper = DatabaseHelper.getInstance(context);
    }
    public abstract void cache(List<Object> list);
    public abstract void loadFromCache();
}
