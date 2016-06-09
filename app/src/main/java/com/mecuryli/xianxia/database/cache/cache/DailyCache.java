package com.mecuryli.xianxia.database.cache.cache;

import android.database.Cursor;
import android.os.Handler;

import com.google.gson.Gson;
import com.mecuryli.xianxia.api.DailyApi;
import com.mecuryli.xianxia.database.cache.BaseCache;
import com.mecuryli.xianxia.database.table.DailyTable;
import com.mecuryli.xianxia.model.Daily.DailyBean;
import com.mecuryli.xianxia.model.Daily.DailyMain;
import com.mecuryli.xianxia.model.Daily.DailyStories;
import com.mecuryli.xianxia.model.Daily.DailyTop_stories;
import com.mecuryli.xianxia.support.CONSTANT;
import com.mecuryli.xianxia.support.HttpUtil;
import com.mecuryli.xianxia.support.Utils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

/**
 * Created by 海飞 on 2016/5/26.
 */
public class DailyCache extends BaseCache<DailyBean> {

    private DailyTable table;

    public DailyCache(Handler handler) {
        super(handler);
    }

    @Override
    protected void putData() {
        db.execSQL(mHelper.DROP_TABLE + table.NAME);
        db.execSQL(table.CREATE_TABLE);

        for (int i=0; i<mList.size(); i++){
            DailyBean tmpDaily = mList.get(i);
            values.put(DailyTable.TITLE, tmpDaily.getTitle());
            //values.put(DailyTable.DESCRIPTION,tmpDaily.getDescription());
            values.put(DailyTable.IMAGE,tmpDaily.getImage());
            //values.put(DailyTable.INFO, tmpDaily.getInfo());
            values.put(DailyTable.ID,tmpDaily.getId());
            values.put(DailyTable.IS_COLLECTED,tmpDaily.getIs_collected());

            db.insert(DailyTable.NAME, null, values);
        }
        db.execSQL(table.SQL_INIT_COLLECTION_FLAG);
    }

    @Override
    protected void putData(DailyBean dailyBean) {
        values.put(DailyTable.TITLE,dailyBean.getTitle());
        //values.put(DailyTable.DESCRIPTION,dailyBean.getDescription());
        values.put(DailyTable.IMAGE,dailyBean.getImage());
        values.put(DailyTable.ID,dailyBean.getId());
        //values.put(DailyTable.INFO, dailyBean.getInfo());
        db.insert(DailyTable.COLLECTION_NAME, null, values);
    }

    @Override
    public synchronized void loadFromCache() {
       // String sql = "select * from " + table.NAME;
        String sql = null;
        sql = "select * from " + table.NAME;
        Cursor cursor = query(sql);
        while(cursor.moveToNext()){
            DailyBean dailyBean = new DailyBean();
            dailyBean.setTitle(cursor.getString(DailyTable.ID_TITLE));
            dailyBean.setImage(cursor.getString(DailyTable.ID_IMAGE));
            dailyBean.setId(cursor.getInt(DailyTable.ID_ID));
            //dailyBean.setDescription(cursor.getString(DailyTable.ID_DESCRIPTION));
            //dailyBean.setInfo(cursor.getString(DailyTable.ID_INFO));
            dailyBean.setIs_collected(cursor.getInt(DailyTable.ID_IS_COLLETED));
            mList.add(dailyBean);
        }
        mHandler.sendEmptyMessage(CONSTANT.ID_LOAD_FROM_CACHE);
        cursor.close();
    }

    public void load(){
        Request.Builder builder = new Request.Builder();
        builder.url(DailyApi.newsLatest);
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
                }
                Gson gson = new Gson();
                String s = response.body().string();
                DailyMain main = gson.fromJson(s, DailyMain.class);
                List<DailyStories> dailyStories = main.getStories();
                List<DailyTop_stories> dailyTop_stories = main.getTop_stories();
                for (DailyStories d : dailyStories){
                    DailyBean item = new DailyBean();
                    item.setTitle(d.getTitle());
                    item.setGa_prefix(d.getGa_prefix());
                    item.setId(d.getId());
                    Utils.DLog("DailyCache111:" + d.getId());
                    item.setType(d.getType());
                    item.setImage(d.getImages()[0]);
                    mList.add(item);
                }
                for (DailyTop_stories d : dailyTop_stories){
                    DailyBean item = new DailyBean();
                    item.setTitle(d.getTitle());
                    item.setGa_prefix(d.getGa_prefix());
                    item.setId(d.getId());
                    Utils.DLog("DailyCache222:" + d.getId());
                    item.setType(d.getType());
                    item.setImage(d.getImage());
                    mList.add(item);
                }
                cache();
                mHandler.sendEmptyMessage(CONSTANT.ID_SUCCESS);
            }
        });
    }
}
