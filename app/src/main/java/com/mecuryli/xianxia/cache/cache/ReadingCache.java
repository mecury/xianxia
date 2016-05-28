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
    protected void putData(List<? extends Object> list, String category) {
        db.execSQL(mHelper.DROP_TABLE + table.NAME);
        db.execSQL(table.CREATE_TABLE);

        for (int i=0;i<list.size();i++){
            BookBean bookBean = (BookBean) list.get(i);
            values.put(ReadingTable.TITLE, bookBean.getTitle());
            values.put(ReadingTable.INFO, bookBean.getInfo());
            values.put(ReadingTable.IMAGE, bookBean.getImage());
            values.put(ReadingTable.AUTHOR_INTRO, bookBean.getAuthor_intro());
            values.put(ReadingTable.CATALOG, bookBean.getCatalog());
            values.put(ReadingTable.EBOOK_URL, bookBean.getEbook_url());
            values.put(ReadingTable.CATEGORY, category);
            values.put(ReadingTable.SUMMARY, bookBean.getSummary());
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
        values.put(ReadingTable.AUTHOR_INTRO, bookbean.getAuthor_intro());
        values.put(ReadingTable.CATALOG, bookbean.getCatalog());
        values.put(ReadingTable.EBOOK_URL, bookbean.getEbook_url());
        values.put(ReadingTable.SUMMARY, bookbean.getSummary());
        db.insert(ReadingTable.NAME,null, values);
    }

    @Override
    public List<Object> loadFromCache(String category) {
        String sql = null;
        if (category == null){
            sql = "select * from " + table.NAME;
        }else{
            sql = "select * from " + table.NAME+" where " + table.CATEGORY+ "=\'"+category+"\'";
        }
        Cursor cursor = query(sql);
        while(cursor.moveToNext()){
            BookBean bookBean = new BookBean();
            bookBean.setTitle(cursor.getString(table.ID_TITLE));
            bookBean.setImage(cursor.getString(table.ID_IMAGE));
            bookBean.setInfo(cursor.getString(table.ID_INFO));
            bookBean.setAuthor_intro(cursor.getString(table.ID_AUTHOR_INTRO));
            bookBean.setCatalog(cursor.getString(table.ID_CATELOG));
            bookBean.setEbook_url(cursor.getString(table.ID_EBOOK_URL));
            bookBean.setSummary(cursor.getString(table.ID_SUMMARY));
            bookBean.setIS_COLLECTED(cursor.getInt(table.ID_IS_COLLETED));
            readingList.add(bookBean);
        }
        cursor.close();
        return readingList;
    }

}
