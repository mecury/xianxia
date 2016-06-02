package com.mecuryli.xianxia.database.cache.cache.Collection;

import android.database.Cursor;
import android.os.Handler;

import com.mecuryli.xianxia.database.cache.cache.BaseCollectionCache;
import com.mecuryli.xianxia.database.cache.table.NewsTable;
import com.mecuryli.xianxia.model.news.NewsBean;
import com.mecuryli.xianxia.support.CONSTANT;

/**
 * Created by 海飞 on 2016/5/31.
 */
public class CollectionNewsCache extends BaseCollectionCache<NewsBean> {

    private NewsTable table;

    public CollectionNewsCache(Handler mHandler) {
        super(mHandler);
    }

    @Override
    public void loadFromCache() {
        Cursor cursor = query(table.SELECT_ALL_FROM_COLLECTION);
        while(cursor.moveToNext()){
            NewsBean newsBean = new NewsBean();
            newsBean.setTitle(cursor.getString(table.ID_TITLE));
            newsBean.setDescription(cursor.getString(table.ID_DESCRIPTION));
            newsBean.setLink(cursor.getString(table.ID_LINK));
            newsBean.setPubTime(cursor.getString(table.ID_PUBTIME));
            mList.add(newsBean);
        }
        mHandler.sendEmptyMessage(CONSTANT.ID_LOAD_FROM_CACHE);
    }
}
