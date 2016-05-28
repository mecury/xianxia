package com.mecuryli.xianxia.cache.cache;

import android.content.Context;

import com.mecuryli.xianxia.cache.table.DailyTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 海飞 on 2016/5/26.
 */
public class DailyCache extends BaseCache {

    private DailyTable table;
    private List<Object> dailyList = new ArrayList<>();

    protected DailyCache(Context context) {
        super(context);
        table = new DailyTable();
    }

    @Override
    protected void putData(List<? extends Object> list, String category) {
        db.execSQL(mHelper.DROP_TABLE + table.NAME);
        db.execSQL(table.CREATE_TABLE);

//        for (int i=0; i<list.size(); i++){
//            DailyBean tmpDaily = (DialyBean)list.get(i);
//            values.put(DailyTable.TITLE, tmpDaily.getTitle());
//            values.put(DailyTable.DESCRIPTION,tmpDaily.getDescription());
//            values.put(DailyTable.IMAGE,tmpDaily.getImage());
//            values.put(DailyTable.INFO, tmpDaily.getInfo());
//            values.put(DailyTable.IS_COLLECTED,tmpDaily.getIs_collected());
//            db.insert(DailyTable.NAME, null, values);
//        }
//        db.execSQL(table.SQL_INIT_COLLECTION_FLAG);
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
    public synchronized List<Object> loadFromCache(String category) {
        String sql = "select * from " + table.NAME;
//        Cursor cursor = query(sql);
//        while(cursor.moveToNext()){
//            DailyBean dailyBean = new DailyBean();
//            dailyBean.setTitle(cursor.getString(DailyTable.ID_TITLE));
//            dailyBean.setImage(cursor.getString(DailyTable.ID_IMAGE));
//            dailyBean.setDescription(cursor.getString(DailyTable.ID_DESCRIPTION));
//            dailyBean.setInfo(cursor.getString(DailyTable.ID_INFO));
//            dailyBean.setIs_collected(cursor.getInt(DailyTable.ID_IS_COLLETED));
//            dailyList.add(dailyBean);
//        }
//        cursor.close();
          return null;
    }
}
