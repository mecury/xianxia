package com.mecuryli.xianxia.database.cache.Collection;

import android.database.Cursor;
import android.os.Handler;

import com.mecuryli.xianxia.database.table.DailyTable;
import com.mecuryli.xianxia.model.Daily.DailyBean;
import com.mecuryli.xianxia.support.CONSTANT;
import com.mecuryli.xianxia.support.Utils;

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
            Utils.DLog("CollectionDailyCache1111:" + dailyBean.getImage());
            dailyBean.setId(cursor.getInt(table.ID_ID));
            Utils.DLog("CollectionDailyCache2222:" + dailyBean.getId());
            mList.add(dailyBean);
        }
        mHandler.sendEmptyMessage(CONSTANT.ID_LOAD_FROM_CACHE);
    }
}
