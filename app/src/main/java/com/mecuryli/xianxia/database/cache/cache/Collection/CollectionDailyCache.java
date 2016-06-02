package com.mecuryli.xianxia.database.cache.cache.Collection;

import android.database.Cursor;
import android.os.Handler;

import com.mecuryli.xianxia.database.cache.cache.BaseCollectionCache;
import com.mecuryli.xianxia.database.cache.table.DailyTable;
import com.mecuryli.xianxia.model.Daily.DailyBean;
import com.mecuryli.xianxia.support.CONSTANT;

/**
 * Created by 海飞 on 2016/5/31.
 */
public class CollectionDailyCache extends BaseCollectionCache<DailyBean> {

    private DailyTable table;

    public CollectionDailyCache(Handler mHandler) {
        super(mHandler);
    }

    @Override
    public synchronized void loadFromCache() {
        Cursor cursor= query(table.SELECT_ALL_FROM_COLLECTION);
        while(cursor.moveToNext()){
            DailyBean dailyBean = new DailyBean();
            dailyBean.setTitle(cursor.getString(table.ID_TITLE));
            dailyBean.setImage(cursor.getString(table.ID_IMAGE));
            mList.add(dailyBean);
        }
        mHandler.sendEmptyMessage(CONSTANT.ID_LOAD_FROM_CACHE);
    }
}
