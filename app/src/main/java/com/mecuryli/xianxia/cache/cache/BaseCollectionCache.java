package com.mecuryli.xianxia.cache.cache;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.mecuryli.xianxia.database.DatabaseHelper;
import com.mecuryli.xianxia.xianxiaApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 海飞 on 2016/5/30.
 * 用于“收藏”的缓存，复用了之前做缓存的代码
 */
public abstract class BaseCollectionCache<T> implements ICache<T> {

    protected DatabaseHelper mHelper;
    protected SQLiteDatabase db;

    protected List<T> mList = new ArrayList<>();

    protected Handler mHandler;

    public BaseCollectionCache(Handler mHandler){
        this.mHandler = mHandler;
        mHelper = DatabaseHelper.instance(xianxiaApplication.AppContext);
    }

    @Override
    public void addToCollection(T object) {

    }

    @Override
    public void execSQL(String sql) {
        db = mHelper.getWritableDatabase();
        db.execSQL(sql);
    }

    @Override
    public List<T> getmList() {
        return mList;
    }

    @Override
    public boolean hasData() {
        return !mList.isEmpty();
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

    protected Cursor query(String sql){
        return mHelper.getReadableDatabase().rawQuery(sql,null);
    }
}
