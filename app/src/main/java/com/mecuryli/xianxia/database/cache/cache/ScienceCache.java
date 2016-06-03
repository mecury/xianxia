package com.mecuryli.xianxia.database.cache.cache;

import android.database.Cursor;
import android.os.Handler;

import com.google.gson.Gson;
import com.mecuryli.xianxia.database.cache.BaseCache;
import com.mecuryli.xianxia.database.table.ScienceTable;
import com.mecuryli.xianxia.model.science.ArticleBean;
import com.mecuryli.xianxia.model.science.ScienceBean;
import com.mecuryli.xianxia.support.CONSTANT;
import com.mecuryli.xianxia.support.HttpUtil;
import com.mecuryli.xianxia.support.Utils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by 海飞 on 2016/5/26.
 */
public class ScienceCache extends BaseCache<ArticleBean> {

    private ScienceTable table;
    public ScienceCache(Handler handler, String category, String url) {
        super(handler, category, url);
    }

    @Override
    protected void putData() {
        db.execSQL(mHelper.DROP_TABLE + table.NAME);
        db.execSQL(table.CREATE_TABLE);

        for(int i=0;i<mList.size();i++){
            ArticleBean articleBean = mList.get(i);
            values.put(ScienceTable.TITLE,articleBean.getTitle());
            values.put(ScienceTable.DESCRIPTION,articleBean.getSummary());
            values.put(ScienceTable.IMAGE,articleBean.getImage_info().getUrl());
            //values.put(ScienceTable.COMMENT_COUNT,articleBean.getReplies_count());
            values.put(ScienceTable.INFO,articleBean.getInfo());
            values.put(ScienceTable.URL, articleBean.getUrl());
            values.put(ScienceTable.CATEGORY,mCategory);
            values.put(ScienceTable.IS_COLLECTED,articleBean.getIs_collected());
            db.insert(ScienceTable.NAME,null,values);
        }
        db.execSQL(table.SQL_INIT_COLLECTION_FLAG);
    }

    @Override
    protected void putData(ArticleBean articleBean) {
        values.put(ScienceTable.TITLE,articleBean.getTitle());
        values.put(ScienceTable.DESCRIPTION,articleBean.getSummary());
        values.put(ScienceTable.IMAGE,articleBean.getImage_info().getUrl());
        //values.put(ScienceTable.COMMENT_COUNT,articleBean.getReplies_count());
        values.put(ScienceTable.INFO,articleBean.getInfo());
        values.put(ScienceTable.URL, articleBean.getUrl());
        db.insert(ScienceTable.COLLECTION_NAME, null, values);
    }

    @Override
    public synchronized void loadFromCache() {
        String sql = null;
        if (mCategory == null){
            sql = "select * from " + table.NAME;
        }else{
            sql = "select * from " + table.NAME+ " where "+table.CATEGORY + "=\'" +mCategory+"\'";
        }
        Cursor cursor = query(sql);
        while (cursor.moveToNext()){
            ArticleBean articleBean = new ArticleBean();
            articleBean.setTitle(cursor.getString(ScienceTable.ID_TITLE));
            articleBean.setSummary(cursor.getString(ScienceTable.ID_DESCRIPTION));
            if (articleBean.getImage_info() == null){
                Utils.DLog(" " + articleBean.getImage_info());
            }else{
                articleBean.getImage_info().setUrl(cursor.getString(ScienceTable.ID_IMAGE));
            }
            articleBean.setReplies_count(cursor.getInt(ScienceTable.ID_COMMENT_COUNT));
            articleBean.setInfo(cursor.getString(ScienceTable.ID_INFO));
            articleBean.setUrl(cursor.getString(ScienceTable.ID_URL));
            articleBean.setIs_collected(cursor.getInt(ScienceTable.ID_IS_COLLETED));
            mList.add(articleBean);
        }
        mHandler.sendEmptyMessage(CONSTANT.ID_LOAD_FROM_CACHE);
        cursor.close();
    }

    @Override
    public void load() {
        Request.Builder builder = new Request.Builder();
        builder.url(mUrl);
        Request request = builder.build();
        HttpUtil.enqueue(request, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mHandler.sendEmptyMessage(CONSTANT.ID_FAILURE);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful() == false) {
                    mHandler.sendEmptyMessage(CONSTANT.ID_FAILURE);
                    return;
                }
                Gson gson = new Gson();
                ArticleBean[] articleBeans = (gson.fromJson(response.body().string(), ScienceBean.class)).getResult();
                for(ArticleBean articleBean: articleBeans){
                    mList.add(articleBean);
                }
                mHandler.sendEmptyMessage(CONSTANT.ID_SUCCESS);
            }
        });
    }

    /*private ScienceTable table ;
    private List<Object> scienceList = new ArrayList<>();

    public ScienceCache(Context context) {
        super(context);
        table = new ScienceTable();
    }

    @Override
    protected void putData(List<? extends Object> list, String category) {
        db.execSQL(mHelper.DROP_TABLE + table.NAME);
        db.execSQL(table.CREATE_TABLE);

        for(int i=0;i<list.size();i++){
            ArticleBean articleBean = (ArticleBean) list.get(i);
            values.put(ScienceTable.TITLE,articleBean.getTitle());
            values.put(ScienceTable.DESCRIPTION,articleBean.getSummary());
            values.put(ScienceTable.IMAGE,articleBean.getImage_info().getUrl());
            values.put(ScienceTable.COMMENT_COUNT,articleBean.getReplies_count());
            values.put(ScienceTable.INFO,articleBean.getInfo());
            values.put(ScienceTable.URL, articleBean.getUrl());
            values.put(ScienceTable.CATEGORY,category);
            values.put(ScienceTable.IS_COLLECTED,articleBean.getIs_collected());
            db.insert(ScienceTable.NAME,null,values);
            }
        db.execSQL(table.SQL_INIT_COLLECTION_FLAG);
    }

    @Override
    protected void putData(Object object) {
        ArticleBean articleBean = (ArticleBean) object;
        values.put(ScienceTable.TITLE,articleBean.getTitle());
        values.put(ScienceTable.DESCRIPTION,articleBean.getSummary());
        values.put(ScienceTable.IMAGE,articleBean.getImage_info().getUrl());
        values.put(ScienceTable.COMMENT_COUNT,articleBean.getReplies_count());
        values.put(ScienceTable.INFO,articleBean.getInfo());
        values.put(ScienceTable.URL, articleBean.getUrl());
        db.insert(ScienceTable.COLLECTION_NAME, null, values);
    }

    @Override
    public synchronized List<Object> loadFromCache(String category) {
        String sql = null;
        if (category == null){
            sql = "select * from " + table.NAME;
        }else{
            sql = "select * from " + table.NAME+ " where "+table.CATEGORY + "=\'" +category+"\'";
        }
        Cursor cursor = query(sql);
        while (cursor.moveToNext()){
            ArticleBean articleBean = new ArticleBean();
            articleBean.setTitle(cursor.getString(ScienceTable.ID_TITLE));
            articleBean.setSummary(cursor.getString(ScienceTable.ID_DESCRIPTION));
            if (articleBean.getImage_info() == null){
                Utils.DLog(" " + articleBean.getImage_info());
            }else{
                articleBean.getImage_info().setUrl(cursor.getString(ScienceTable.ID_IMAGE));
            }
            articleBean.setReplies_count(cursor.getInt(ScienceTable.ID_COMMENT_COUNT));
            articleBean.setInfo(cursor.getString(ScienceTable.ID_INFO));
            articleBean.setUrl(cursor.getString(ScienceTable.ID_URL));
            articleBean.setIs_collected(cursor.getInt(ScienceTable.ID_IS_COLLETED));
            scienceList.add(articleBean);
            }
        cursor.close();
        return scienceList;
    }*/

}
