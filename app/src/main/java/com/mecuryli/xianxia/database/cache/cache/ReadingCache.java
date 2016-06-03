package com.mecuryli.xianxia.database.cache.cache;

import android.database.Cursor;
import android.os.Handler;

import com.google.gson.Gson;
import com.mecuryli.xianxia.database.cache.BaseCache;
import com.mecuryli.xianxia.database.table.ReadingTable;
import com.mecuryli.xianxia.model.reading.BookBean;
import com.mecuryli.xianxia.model.reading.ReadingBean;
import com.mecuryli.xianxia.support.CONSTANT;
import com.mecuryli.xianxia.support.HttpUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by 海飞 on 2016/5/26.
 */
public class ReadingCache extends BaseCache<BookBean> {

    private ReadingTable table;

    public ReadingCache(Handler handler, String category,String[] urls) {
        super(handler,category,urls);
    }

    @Override
    protected void putData() {
        db.execSQL(mHelper.DROP_TABLE + table.NAME);
        db.execSQL(table.CREATE_TABLE);

        for (int i=0;i<mList.size();i++){
            BookBean bookBean = mList.get(i);
            values.put(ReadingTable.TITLE, bookBean.getTitle());
            values.put(ReadingTable.INFO, bookBean.getInfo());
            values.put(ReadingTable.IMAGE, bookBean.getImage());
            values.put(ReadingTable.AUTHOR_INTRO, bookBean.getAuthor_intro()==null ? "":bookBean.getAuthor_intro());
            values.put(ReadingTable.CATALOG, bookBean.getCatalog()==null ? "": bookBean.getCatalog());
            values.put(ReadingTable.EBOOK_URL, bookBean.getEbook_url()==null ? "":bookBean.getEbook_url());
            values.put(ReadingTable.CATEGORY, mCategory);
            values.put(ReadingTable.SUMMARY, bookBean.getSummary()==null ? "" : bookBean.getSummary());
            values.put(ReadingTable.IS_COLLECTED, bookBean.getImage());
            db.insert(ReadingTable.NAME, null, values);
        }
        db.execSQL(table.SQL_INIT_COLLECTION_FLAG);
    }

    @Override
    protected void putData(BookBean bookBean) {
        values.put(ReadingTable.TITLE, bookBean.getTitle());
        values.put(ReadingTable.IMAGE, bookBean.getImage());
        values.put(ReadingTable.INFO, bookBean.getInfo());
        values.put(ReadingTable.AUTHOR_INTRO, bookBean.getAuthor_intro()==null ? "" : bookBean.getAuthor_intro());
        values.put(ReadingTable.CATALOG, bookBean.getCatalog()==null ? "" : bookBean.getCatalog());
        values.put(ReadingTable.EBOOK_URL, bookBean.getEbook_url()==null ? "" : bookBean.getEbook_url());
        values.put(ReadingTable.SUMMARY, bookBean.getSummary()==null ? "" : bookBean.getSummary());
        db.insert(ReadingTable.COLLECTION_NAME,null, values);
    }

    @Override
    public synchronized void loadFromCache() {
        String sql = null;
        if (mCategory == null){
            sql = "select * from " + table.NAME;
        }else{
            sql = "select * from " + table.NAME+" where " + table.CATEGORY+ "=\'"+mCategory+"\'";
        }
        Cursor cursor = query(sql);
        while(cursor.moveToNext()){
            BookBean bookBean = new BookBean();
            bookBean.setTitle(cursor.getString(table.ID_TITLE));
            bookBean.setInfo(cursor.getString(table.ID_INFO));
            bookBean.setImage(cursor.getString(table.ID_IMAGE));
            bookBean.setAuthor_intro(cursor.getString(table.ID_AUTHOR_INTRO));
            bookBean.setCatalog(cursor.getString(table.ID_CATELOG));
            bookBean.setEbook_url(cursor.getString(table.ID_EBOOK_URL));
            bookBean.setSummary(cursor.getString(table.ID_SUMMARY));
            bookBean.setIs_collected(cursor.getInt(table.ID_IS_COLLETED));
            mList.add(bookBean);
        }
        mHandler.sendEmptyMessage(CONSTANT.ID_LOAD_FROM_CACHE);
        //cursor.close();
    }

    @Override
    public void load() {
        for (int i = 0; i < mUrls.length; i++){
            String url = mUrls[i];
            Request.Builder builder = new Request.Builder();
            builder.url(url);
            Request request = builder.build();
            HttpUtil.enqueue(request, new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    mHandler.sendEmptyMessage(CONSTANT.ID_FAILURE);
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    if (response.isSuccessful() == false){
                        mHandler.sendEmptyMessage(CONSTANT.ID_FAILURE);
                        return;
                    }
                    Gson gson = new Gson();
                    BookBean [] bookBeans = gson.fromJson(response.body().string(), ReadingBean.class).getBooks();
                    for (BookBean bookBean : bookBeans){
                        mList.add(bookBean);
                    }
                    mHandler.sendEmptyMessage(CONSTANT.ID_SUCCESS);
                }
            });
        }
    }
}
