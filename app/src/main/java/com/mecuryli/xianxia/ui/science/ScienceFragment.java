package com.mecuryli.xianxia.ui.science;

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
import com.mecuryli.xianxia.api.ScienceApi;
import com.mecuryli.xianxia.model.science.ArticleBean;
import com.mecuryli.xianxia.model.science.ScienceBean;
import com.mecuryli.xianxia.support.adapter.Utils;
import com.mecuryli.xianxia.support.adapter.adapter.DividerItemDecoration;
import com.mecuryli.xianxia.support.adapter.adapter.ScienceAdapter;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 海飞 on 2016/5/16.
 */
public class ScienceFragment extends Fragment {

    private View parentView;
    private ScienceBean scienceBean;
    private List<ArticleBean> items = new ArrayList<>();
    private PullToRefreshView refreshView;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ScienceAdapter adapter;
    private RequestQueue queue;
    private String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = View.inflate(getContext(), R.layout.layout_commont_list, null);
        initData();
        return parentView;
    }

    private void initData() {
        url = ScienceApi.science_channel_url + ScienceApi
                .channel_tag[getArguments().getInt(getString(R.string.id_pos))];
        refreshView = (PullToRefreshView) parentView.findViewById(R.id.pull_to_refresh);
        recyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerView);
        adapter = new ScienceAdapter(getContext(), items);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(adapter);
        loadNewsFromNet();
    }

    private void loadNewsFromNet() {
        queue = Volley.newRequestQueue(getContext());
        Utils.DLog(url);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                ArticleBean[] articleBeans = gson.fromJson(s, ScienceBean.class).getResult();
                for (ArticleBean articleBean : articleBeans) {
                    items.add(articleBean);
                }
                handler.sendEmptyMessage(0);
                refreshView.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Utils.showToast("网络异常，刷新失败");
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

























































