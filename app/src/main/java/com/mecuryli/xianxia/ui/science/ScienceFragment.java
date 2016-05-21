package com.mecuryli.xianxia.ui.science;

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

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.api.ScienceApi;
import com.mecuryli.xianxia.model.science.ArticleBean;
import com.mecuryli.xianxia.model.science.ScienceBean;
import com.mecuryli.xianxia.support.CONSTANT;
import com.mecuryli.xianxia.support.HttpUtil;
import com.mecuryli.xianxia.support.Utils;
import com.mecuryli.xianxia.support.adapter.DividerItemDecoration;
import com.mecuryli.xianxia.support.adapter.ScienceAdapter;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yalantis.phoenix.PullToRefreshView;

import java.io.IOException;
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
        refreshView.setRefreshing(true);
        new Thread(new Runnable() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                Request.Builder builder = new Request.Builder();
                builder.url(url);
                Request request = builder.build();
                HttpUtil.enqueue(request, new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        handler.sendEmptyMessage(CONSTANT.ID_FAILURE);
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        if (response.isSuccessful() == false) {
                            handler.sendEmptyMessage(CONSTANT.ID_FAILURE);
                            return;
                            }
                        Gson gson = new Gson();
                        ArticleBean[] articleBeans = (gson.fromJson(response.body().string(), ScienceBean.class)).getResult();
                        items.clear();
                        for(ArticleBean articleBean: articleBeans){
                            items.add(articleBean);
                            }
                        items.addAll(items);
                        handler.sendEmptyMessage(CONSTANT.ID_SUCCESS);
                    }
                });
            }
        }).start();
    };

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            refreshView.setRefreshing(false);
            switch (msg.what){
                case CONSTANT.ID_FAILURE:
                    Utils.DLog(getString(R.string.Text_Net_Exception));
                    break;
                case CONSTANT.ID_SUCCESS:
                    adapter.notifyDataSetChanged();
                    break;
            }
            return false;
        }
    });
}

























































