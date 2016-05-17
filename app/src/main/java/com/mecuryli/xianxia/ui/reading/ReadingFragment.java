package com.mecuryli.xianxia.ui.reading;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
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
import com.mecuryli.xianxia.api.ReadingApi;
import com.mecuryli.xianxia.model.reading.BookBean;
import com.mecuryli.xianxia.model.reading.ReadingBean;
import com.mecuryli.xianxia.support.adapter.Utils;
import com.mecuryli.xianxia.support.adapter.adapter.DividerItemDecoration;
import com.mecuryli.xianxia.support.adapter.adapter.ReadingAdapter;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 海飞 on 2016/5/11.
 */
public class ReadingFragment extends Fragment {

    private View parentView;
    private PullToRefreshView refreshView;
    private RecyclerView recyclerView;
    private List<BookBean> items = new ArrayList<>();
    private RequestQueue queue;
    private RecyclerView.LayoutManager mLayoutManager;
    private ReadingAdapter adapter;
    private int pos;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = View.inflate(getContext(), R.layout.layout_reading_list,null);
        initData();
        return parentView;
    }

    private void initData(){
        pos = getArguments().getInt("pos");
        recyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerView);
        refreshView = (PullToRefreshView) parentView.findViewById(R.id.pull_to_refresh);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST)); //设置item间的分割线
        adapter = new ReadingAdapter(items, getContext()); //适配listview
        recyclerView.setAdapter(adapter); //将adaper添加到recylcerView中
        refreshView.setRefreshing(true);
        //由网络中加载数据
        loadNewsFromNet(pos);
        refreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNewsFromNet(pos);
            }
        });
    }


    private void loadNewsFromNet(int pos){
        queue = Volley.newRequestQueue(getContext());

        String[] tags = ReadingApi.getTags(ReadingApi.getApiTag(pos));
        for (int i =0; i < ReadingApi.TAG_LEN; i++){
            String url = ReadingApi.searchByTag+tags[i];
            Utils.DLog(url);

            StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Gson gson = new Gson();
                    BookBean [] bookBeans = gson.fromJson(s,ReadingBean.class).getBooks();
                    for (BookBean bookBean : bookBeans){
                        items.add(bookBean);
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
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            adapter.notifyDataSetChanged();
            return false;
        }
    });
}























