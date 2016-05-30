package com.mecuryli.xianxia.ui.daily;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.api.DailyApi;
import com.mecuryli.xianxia.cache.cache.DailyCache;
import com.mecuryli.xianxia.model.Daily.DailyBean;
import com.mecuryli.xianxia.model.Daily.DailyMain;
import com.mecuryli.xianxia.model.Daily.DailyStories;
import com.mecuryli.xianxia.model.Daily.DailyTop_stories;
import com.mecuryli.xianxia.support.CONSTANT;
import com.mecuryli.xianxia.support.HttpUtil;
import com.mecuryli.xianxia.support.Utils;
import com.mecuryli.xianxia.support.adapter.DailyAdapter;
import com.mecuryli.xianxia.support.adapter.DividerItemDecoration;
import com.mecuryli.xianxia.ui.support.BaseListFragment;
import com.mecuryli.xianxia.xianxiaApplication;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yalantis.phoenix.PullToRefreshView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 海飞 on 2016/5/18.
 */
public class DailyFragment extends BaseListFragment {

    private DailyCache dailyCache;

    @Override
    protected boolean setHeaderTab() {
        return false;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreateCache() {
        dailyCache = new DailyCache(getContext(),handler);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected RecyclerView.Adapter bindAdapter() {
        return new DailyAdapter(dailyCache,getContext());
    }

    @Override
    protected void loadFromNet() {
        dailyCache.load();
    }

    @Override
    protected void loadFromCache() {
        dailyCache.loadFromCache();
    }

    @Override
    protected boolean hasData() {
        return dailyCache.hasData();
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        parentView = inflater.inflate(R.layout.layout_commont_list,container,false);
//        initData();
//        return parentView;
//    }

//    public void initData(){
//        refreshView = (PullToRefreshView) parentView.findViewById(R.id.pull_to_refresh);
//        recyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerView);
//        adapter = new DailyAdapter(getContext(),items);
//        mLayoutManager = new LinearLayoutManager(xianxiaApplication.AppContext);
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
//        recyclerView.setAdapter(adapter);
//        url = DailyApi.newsLatest;
//        progressBar = (ProgressBar) parentView.findViewById(R.id.progressbar);
//        progressBar.setVisibility(View.GONE);
//        sad_face = (ImageView) parentView.findViewById(R.id.sad_face);
//
//        refreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                loadDailyFromNet();
//            }
//        });
//
//        sad_face.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sad_face.setVisibility(View.GONE);
//                loadDailyFromNet();
//            }
//        });
//
//        cache = new DailyCache(xianxiaApplication.AppContext);
//        loadCache();
//    }
//
//    public void loadDailyFromNet(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Request.Builder builder = new Request.Builder();
//                builder.url(url);
//                Request request = builder.build();
//                HttpUtil.enqueue(request, new Callback() {
//                    @Override
//                    public void onFailure(Request request, IOException e) {
//                        handler.sendEmptyMessage(CONSTANT.ID_FAILURE);
//                    }
//
//                    @Override
//                    public void onResponse(Response response) throws IOException {
//                        if (response.isSuccessful() == false){
//                            handler.sendEmptyMessage(CONSTANT.ID_FAILURE);
//                        }
//                        Gson gson = new Gson();
//                        String s = response.body().string();
//                        DailyMain main = gson.fromJson(s, DailyMain.class);
//                        List<DailyStories> dailyStories = main.getStories();
//                        List<DailyTop_stories> dailyTop_stories = main.getTop_stories();
//                        for (DailyStories d : dailyStories){
//                            DailyBean item = new DailyBean();
//                            item.setTitle(d.getTitle());
//                            item.setGa_prefix(d.getGa_prefix());
//                            item.setId(d.getId());
//                            item.setType(d.getType());
//                            item.setImage(d.getImages()[0]);
//                            tmpItems.add(item);
//                        }
//                        for (DailyTop_stories d : dailyTop_stories){
//                            DailyBean item = new DailyBean();
//                            item.setTitle(d.getTitle());
//                            item.setGa_prefix(d.getGa_prefix());
//                            item.setId(d.getId());
//                            item.setType(d.getType());
//                            item.setImage(d.getImage());
//                            tmpItems.add(item);
//                        }
//                        handler.sendEmptyMessage(CONSTANT.ID_SUCCESS);
//                    }
//                });
//            }
//        }).start();
//    }
//    private Handler handler = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            switch (msg.what){
//                case CONSTANT.ID_FAILURE:
//                    Utils.showToast("网络错误！");
//                    break;
//                case CONSTANT.ID_SUCCESS:
//                    cache.cache(tmpItems,null);
//                    loadCache();
//                    break;
//                case CONSTANT.ID_LOAD_FROM_NET:
//                    loadDailyFromNet();
//                    break;
//                case CONSTANT.ID_UPDATE_UI:
//                    if (items.isEmpty()){
//                        sad_face.setVisibility(View.VISIBLE);
//                    }else{
//                        sad_face.setVisibility(View.GONE);
//                    }
//                    refreshView.setRefreshing(false);
//                    progressBar.setVisibility(View.GONE);
//                    adapter.notifyDataSetChanged();
//                    break;
//            }
//            adapter.notifyDataSetChanged();
//            return false;
//        }
//    });
//
//    private void loadCache(){
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                tmpItems.clear();
//                List<Object> tmpList = cache.loadFromCache(null);
//                Utils.DLog(tmpList.size() +"");
//                for (int i=0;i<tmpList.size();i++){
//                    tmpItems.add((DailyBean) tmpList.get(i));
//                }
//                tmpList.clear();
//                items.clear();
//                items.addAll(tmpItems);
//                tmpItems.clear();
//                if (progressBar.getVisibility() == View.VISIBLE){
//                    if (items.isEmpty()){
//                        handler.sendEmptyMessage(CONSTANT.ID_LOAD_FROM_NET);
//                    }
//                }
//                handler.sendEmptyMessage(CONSTANT.ID_UPDATE_UI);
//            }
//        });
//        thread.start();
//    }
}
