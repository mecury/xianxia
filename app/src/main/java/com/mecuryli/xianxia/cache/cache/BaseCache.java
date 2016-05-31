package com.mecuryli.xianxia.cache.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.mecuryli.xianxia.cache.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 海飞 on 2016/5/26.
 */
public abstract class BaseCache<T> implements ICache<T>{
    protected Context mContext;
    protected DatabaseHelper mHelper;
    protected SQLiteDatabase db;

    protected ContentValues values;
    protected List<T> mList = new ArrayList<>();

    protected Handler mHandler;
    protected String mCategory;

    protected String mUrl;
    protected String[] mUrls;

    protected BaseCache(Context context,Handler handler, String category){
        mContext = context;
        mHelper = DatabaseHelper.getInstance(context);
        mHandler = handler;
        mCategory = category;
    }

    protected BaseCache(Context context,Handler handler, String category,String[] urls){
        this(context,handler,category);
        mUrls = urls;
    }

    protected BaseCache(Context context,Handler handler, String category, String url){
        this(context,handler,category);
        mUrl = url;
    }

    protected BaseCache(Context context, Handler handler){
        this(context,handler,null);
    }

    protected abstract void putData();
    protected abstract void putData(T object);

    public synchronized void cache(){
        db = mHelper.getWritableDatabase();
        db.beginTransaction();
        values = new ContentValues();
        putData();
        db.setTransactionSuccessful();
        db.endTransaction();
        //db.close();
    }

    public synchronized void addToCollection(T object){
        db = mHelper.getWritableDatabase();
        db.beginTransaction();
        values = new ContentValues();
        putData(object);
        db.setTransactionSuccessful();
        db.endTransaction();
        //db.close();
    }
    public synchronized void execSQL(String sql){
        db = mHelper.getWritableDatabase();
        db.execSQL(sql);
        //db.close();
    }
    public List<T> getmList(){
        return mList;
    }
    public boolean hasData(){
        return !mList.isEmpty();
    }
    public abstract void loadFromCache();

    protected Cursor query(String sql){
        return mHelper.getReadableDatabase().rawQuery(sql,null);
    }
}
