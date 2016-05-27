package com.mecuryli.xianxia.cache.cache;

import android.content.Context;
import android.database.Cursor;

import com.mecuryli.xianxia.cache.table.NewsTable;
import com.mecuryli.xianxia.model.news.NewsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 海飞 on 2016/5/26.
 */
public class NewsCache extends BaseCache {

    private NewsTable table; //由于缓存的表
    private List<Object> newsList = new ArrayList<>();

    public NewsCache(Context context) {
        super(context);
        table = new NewsTable();
    }

    @Override
    protected void putData(List<? extends Object> list) {
        db.execSQL(mHelper.DROP_TABLE + table.NAME);
        db.execSQL(table.CREATE_TABLE);

        for (Object object : list){
            NewsBean newsBean = (NewsBean) object;
            values.put(NewsTable.TITLE, newsBean.getTitle());
            values.put(NewsTable.DESCRIPTION, newsBean.getDescription());
            values.put(NewsTable.PUBTIME, newsBean.getPubTime());
            values.put(NewsTable.IS_COLLECTION, newsBean.getIS_COLLECTED());
            db.insert(NewsTable.NAME,null,values);
        }
        db.execSQL(table.SQL_INIT_COLLECTION_FLAG); //更新标志位，表示已经缓存
    }

    @Override
    protected void putData(Object object) {
        NewsBean newsBean = (NewsBean) object;
        values.put(NewsTable.TITLE, newsBean.getTitle());
        values.put(NewsTable.DESCRIPTION, newsBean.getTitle());
        values.put(NewsTable.PUBTIME, newsBean.getPubTime());
        db.insert(NewsTable.COLLECTION_NAME, null,values);
    }

    @Override
    public List<Object> loadFromCache() {
        Cursor cursor = query(table.NAME);
        while(cursor.moveToNext()){
            NewsBean newsBean = new NewsBean();
            newsBean.setTitle(cursor.getString(NewsTable.ID_TITLE));
            newsBean.setDescription(cursor.getString(NewsTable.ID_DESCRIPTION));
            newsBean.setPubTime(cursor.getString(NewsTable.ID_PUBTIME));
            newsBean.setIS_COLLECTED(cursor.getInt(NewsTable.ID_IS_COLLECTION));
            newsList.add(newsBean);
        }
        cursor.close();
        return newsList;
    }


}