package com.mecuryli.xianxia.ui.daily;

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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.api.DailyApi;
import com.mecuryli.xianxia.model.Daily.DailyItem;
import com.mecuryli.xianxia.model.Daily.DailyMain;
import com.mecuryli.xianxia.model.Daily.DailyStories;
import com.mecuryli.xianxia.support.Utils;
import com.mecuryli.xianxia.support.adapter.DailyAdapter;
import com.mecuryli.xianxia.support.adapter.DividerItemDecoration;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 海飞 on 2016/5/18.
 */
public class DailyFragment extends Fragment {

    private View parentView;
    private RecyclerView recyclerView;
    private PullToRefreshView refreshView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<DailyItem> items = new ArrayList<>();
    private RequestQueue queue;
    private DailyAdapter adapter;
    private String url;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = View.inflate(getContext(), R.layout.layout_commont_list,null);
        initData();
        return parentView;
    }

    public void initData(){
        refreshView = (PullToRefreshView) parentView.findViewById(R.id.pull_to_refresh);
        recyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerView);
        adapter = new DailyAdapter(getContext(),items);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(adapter);
        url = DailyApi.newsLatest;
        loadDailyFromNet();
        refreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDailyFromNet();
            }
        });
    }

    public void loadDailyFromNet(){
        queue = Volley.newRequestQueue(getContext());
        Utils.DLog("Daily部分的url：" + url);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Utils.DLog(s);
                Gson gson = new Gson();
                DailyMain main = gson.fromJson(s, DailyMain.class);
                List<DailyStories> dailyStories = main.getStories();
                for (DailyStories d : dailyStories){
                    DailyItem item = new DailyItem();
                    item.setTitle(d.getTitle());
                    item.setGa_prefix(d.getGa_prefix());
                    item.setId(d.getId());
                    item.setType(d.getType());
                    item.setImages(d.getImages());
                    Utils.DLog(item.getTitle());
                    items.add(item);
                }
                handler.sendEmptyMessage(0);
                refreshView.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Utils.showToast("网络错误！");
                refreshView.setRefreshing(false);
            }
        });
        request.setShouldCache(false);
        queue.add(request);
    }
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            adapter.notifyDataSetChanged();
            return false;
        }
    });
}
