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
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.api.ReadingApi;
import com.mecuryli.xianxia.model.reading.BookBean;
import com.mecuryli.xianxia.model.reading.ReadingBean;
import com.mecuryli.xianxia.support.CONSTANT;
import com.mecuryli.xianxia.support.HttpUtil;
import com.mecuryli.xianxia.support.Utils;
import com.mecuryli.xianxia.support.adapter.DividerItemDecoration;
import com.mecuryli.xianxia.support.adapter.ReadingAdapter;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yalantis.phoenix.PullToRefreshView;

import java.io.IOException;
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
    private ImageView sad_face;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = View.inflate(getContext(), R.layout.layout_reading_list,null);
        initData();
        return parentView;
    }

    private void initData(){
        sad_face = (ImageView) parentView.findViewById(R.id.sad_face);
        pos = getArguments().getInt(getString(R.string.id_pos));
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
        sad_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sad_face.setVisibility(View.GONE);
                loadNewsFromNet(pos);
            }
        });
    }


    private void loadNewsFromNet(int pos){
        final String[] tags = ReadingApi.getTags(ReadingApi.getApiTag(pos));
        refreshView.setRefreshing(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<ReadingApi.TAG_LEN;i++){
                    String url = ReadingApi.searchByTag+tags[i];
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
                            if (response.isSuccessful() == false){
                                handler.sendEmptyMessage(CONSTANT.ID_FAILURE);
                                return;
                            }
                            Gson gson = new Gson();
                            BookBean [] bookBeans = gson.fromJson(response.body().string(), ReadingBean.class).getBooks();
                            for (BookBean bookBean : bookBeans){
                                items.add(bookBean);
                            }
                            handler.sendEmptyMessage(CONSTANT.ID_SUCCESS);
                        }
                    });
                }
            }
        }).start();
    }

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
            if (items.isEmpty()){
                sad_face.setVisibility(View.VISIBLE);
            }else{
                sad_face.setVisibility(View.GONE);
            }
            return false;
        }
    });
}























