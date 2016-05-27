package com.mecuryli.xianxia.cache.cache;

import android.content.Context;
import android.database.Cursor;

import com.mecuryli.xianxia.cache.table.DailyTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 海飞 on 2016/5/26.
 */
public class DailyCache extends BaseCache {

    protected DailyTable table;
    private List<Object> dailyList = new ArrayList<>();

    protected DailyCache(Context context) {
        super(context);
        table = new DailyTable();
    }

    @Override
    protected void putData(List<? extends Object> list) {
        db.execSQL(mHelper.DROP_TABLE + table.NAME);
        db.execSQL(table.CREATE_TABLE);

        for (Object dailyBean : list){
//            DailyBean tmpDaily = dailyBean;
//            values.put(DailyTable.TITLE, tmpDaily.getTitle());
//            values.put(DailyTable.DESCRIPTION,tmpDaily.getDescription());
//            values.put(DailyTable.IMAGE,tmpDaily.getImage());
//            values.put(DailyTable.INFO, tmpDaily.toString());
//            values.put(DailyTable.IS_COLLECTED,tmpDaily.getIs_collected());
//            db.insert(DailyTable.NAME, null, values);
        }
        db.execSQL(table.SQL_INIT_COLLECTION_FLAG);
    }

    @Override
    protected void putData(Object object) {
//        DailyBean dailyBean = (DailyBean)object;
//        values.put(DailyTable.TITLE,dailyBean.getTitle());
//        values.put(DailyTable.DESCRIPTION,dailyBean.getDescription());
//        values.put(DailyTable.IMAGE,dailyBean.getImage());
//        values.put(DailyTable.INFO, dailyBean.getInfo());
//        db.insert(DailyTable.COLLECTION_NAME, null, values);
    }

    @Override
    public List<Object> loadFromCache() {
        Cursor cursor = query(table.NAME);
        while(cursor.moveToNext()){
//            DailyBean dailyBean = new DailyBean();
//            dailyBean.setTitle(cursor.getString(DailyTable.ID_TITLE));
//            dailyBean.setImage(cursor.getString(DailyTable.ID_IMAGE));
 //           dailyBean.setDescription(cursor.getString(DailyTable.ID_DESCRIPTION));
//            dailyBean.setInfo(cursor.getString(DailyTable.ID_INFO));
//            dailyBean.setIs_collected(cursor.getInt(DailyTable.ID_IS_COLLETED));
//            dailyList.add(dailyBean);
        }
        cursor.close();
        return null;
    }
}
