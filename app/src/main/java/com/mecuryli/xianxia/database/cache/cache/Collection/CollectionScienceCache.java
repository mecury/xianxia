package com.mecuryli.xianxia.database.cache.cache.Collection;

import android.database.Cursor;
import android.os.Handler;

import com.mecuryli.xianxia.database.cache.cache.BaseCollectionCache;
import com.mecuryli.xianxia.database.cache.table.ScienceTable;
import com.mecuryli.xianxia.model.science.ArticleBean;
import com.mecuryli.xianxia.support.CONSTANT;

/**
 * Created by 海飞 on 2016/5/31.
 */
public class CollectionScienceCache extends BaseCollectionCache<ArticleBean> {

    ScienceTable table;

    public CollectionScienceCache(Handler mHandler) {
        super(mHandler);
    }

    @Override
    public void loadFromCache() {
        Cursor cursor = query(table.SELECT_ALL_FROM_COLLECTION);
        while(cursor.moveToNext()){
            ArticleBean articleBean = new ArticleBean();
            articleBean.setTitle(cursor.getString(ScienceTable.ID_TITLE));
            articleBean.setSummary(cursor.getString(ScienceTable.ID_DESCRIPTION));
            articleBean.getImage_info().setUrl(cursor.getString(ScienceTable.ID_IMAGE));
            articleBean.setReplies_count(cursor.getInt(ScienceTable.ID_COMMENT_COUNT));
            articleBean.setInfo(cursor.getString(ScienceTable.ID_INFO));
            articleBean.setUrl(cursor.getString(ScienceTable.ID_URL));
            mList.add(articleBean);
        }
        mHandler.sendEmptyMessage(CONSTANT.ID_LOAD_FROM_CACHE);
    }
}
