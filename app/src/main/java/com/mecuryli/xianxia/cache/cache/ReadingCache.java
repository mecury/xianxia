package com.mecuryli.xianxia.cache.cache;

import android.content.Context;
import android.database.Cursor;

import com.mecuryli.xianxia.cache.table.ReadingTable;
import com.mecuryli.xianxia.model.reading.BookBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 海飞 on 2016/5/26.
 */
public class ReadingCache extends BaseCache {

    private ReadingTable table;
    private List<Object> readingList = new ArrayList<>();

    public ReadingCache(Context context) {
        super(context);
        table = new ReadingTable();
    }

    @Override
    protected void putData(List<? extends Object> list) {
        db.execSQL(mHelper.DROP_TABLE + table.NAME);
        db.execSQL(table.CREATE_TABLE);

        for (Object object : list){
            BookBean bookBean = new BookBean();
            values.put(ReadingTable.TITLE, bookBean.getTitle());
            values.put(ReadingTable.INFO, bookBean.getInfo());
            values.put(ReadingTable.IMAGE, bookBean.getImage());
            values.put(ReadingTable.IS_COLLECTED, bookBean.getImage());
            db.insert(ReadingTable.NAME, null, values);
        }
        db.execSQL(table.SQL_INIT_COLLECTION_FLAG);
    }

    @Override
    protected void putData(Object object) {
        BookBean bookbean = (BookBean) object;
        values.put(ReadingTable.TITLE, bookbean.getTitle());
        values.put(ReadingTable.IMAGE, bookbean.getImage());
        values.put(ReadingTable.INFO, bookbean.getInfo());
        db.insert(ReadingTable.NAME,null, values);
    }

    @Override
    public List<Object> loadFromCache() {
        Cursor cursor = query(table.NAME);
        while(cursor.moveToNext()){
            BookBean bookBean = new BookBean();
            bookBean.setTitle(cursor.getString(table.ID_TITLE));
            bookBean.setImage(cursor.getString(table.ID_IMAGE));
            bookBean.setInfo(cursor.getString(table.ID_INFO));
            bookBean.setIS_COLLECTED(cursor.getInt(table.ID_IS_COLLETED));
            readingList.add(bookBean);
        }
        cursor.close();
        return readingList;
    }

}
