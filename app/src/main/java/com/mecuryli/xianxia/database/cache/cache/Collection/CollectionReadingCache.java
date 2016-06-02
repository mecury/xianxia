package com.mecuryli.xianxia.database.cache.cache.Collection;

import android.database.Cursor;
import android.os.Handler;

import com.mecuryli.xianxia.database.cache.cache.BaseCollectionCache;
import com.mecuryli.xianxia.database.cache.table.ReadingTable;
import com.mecuryli.xianxia.model.reading.BookBean;
import com.mecuryli.xianxia.support.CONSTANT;

/**
 * Created by 海飞 on 2016/5/31.
 */
public class CollectionReadingCache extends BaseCollectionCache<BookBean> {

    ReadingTable table;

    public CollectionReadingCache(Handler mHandler) {
        super(mHandler);
    }

    @Override
    public void loadFromCache() {
        Cursor cursor = query(table.SELECT_ALL_FROM_COLLECTION);
        while(cursor.moveToNext()){
            BookBean bookBean = new BookBean();
            bookBean.setTitle(cursor.getString(table.ID_TITLE));
            bookBean.setInfo(cursor.getString(table.ID_INFO));
            bookBean.setImage(cursor.getString(table.ID_IMAGE));
            bookBean.setAuthor_intro(cursor.getString(table.ID_AUTHOR_INTRO));
            bookBean.setCatalog(cursor.getString(table.ID_CATELOG));
            bookBean.setEbook_url(cursor.getString(table.ID_EBOOK_URL));
            bookBean.setSummary(cursor.getString(table.ID_SUMMARY));
            mList.add(bookBean);
        }
        mHandler.sendEmptyMessage(CONSTANT.ID_LOAD_FROM_CACHE);
    }
}
