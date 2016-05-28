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
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.api.ReadingApi;
import com.mecuryli.xianxia.cache.cache.ReadingCache;
import com.mecuryli.xianxia.model.reading.BookBean;
import com.mecuryli.xianxia.model.reading.ReadingBean;
import com.mecuryli.xianxia.support.CONSTANT;
import com.mecuryli.xianxia.support.HttpUtil;
import com.mecuryli.xianxia.support.Utils;
import com.mecuryli.xianxia.support.adapter.DividerItemDecoration;
import com.mecuryli.xianxia.support.adapter.ReadingAdapter;
import com.mecuryli.xianxia.xianxiaApplication;
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
    protected PullToRefreshView refreshView;
    private RecyclerView recyclerView;
    protected List<BookBean> items = new ArrayList<>();
    private List<BookBean> tmpItems = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    private ReadingAdapter adapter;
    private int pos;

    private ImageView sad_face;
    private ProgressBar progressBar;

    private String category;
    private String url;
    private ReadingCache cache;

    private Thread thread;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = View.inflate(getContext(), R.layout.layout_reading_list,null);
        initData();
        return parentView;
    }

    protected void getData(){
        pos = getArguments().getInt(getString(R.string.id_pos));
        category = getArguments().getString(getString(R.string.id_category));
        Utils.DLog("Reading====>:" + category);
    }
    protected void initData(){
        sad_face = (ImageView) parentView.findViewById(R.id.sad_face);
        progressBar = (ProgressBar) parentView.findViewById(R.id.progressbar);
        getData();
        recyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerView);
        refreshView = (PullToRefreshView) parentView.findViewById(R.id.pull_to_refresh);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST)); //设置item间的分割线
        adapter = new ReadingAdapter(items, getContext()); //适配listview
        recyclerView.setAdapter(adapter); //将adaper添加到recylcerView中

        refreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNewsFromNet();
            }
        });
        sad_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sad_face.setVisibility(View.GONE);
                loadNewsFromNet();
            }
        });

        cache = new ReadingCache(xianxiaApplication.AppContext);
        loadCache();
    }


    protected void loadNewsFromNet(){
        final String[] tags = ReadingApi.getTags(ReadingApi.getApiTag(pos));
        refreshView.setRefreshing(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<ReadingApi.TAG_LEN;i++){
                    url = ReadingApi.searchByTag+tags[i];
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
                                tmpItems.add(bookBean);
                            }
                            handler.sendEmptyMessage(CONSTANT.ID_SUCCESS);
                        }
                    });
                }
            }
        }).start();
    }

    protected Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            refreshView.setRefreshing(false);
            switch (msg.what){
                case CONSTANT.ID_FAILURE:
                    Utils.DLog(getString(R.string.Text_Net_Exception));
                    break;
                case CONSTANT.ID_SUCCESS:
                    cache.cache(tmpItems,category);  //将由网上加载的数据缓存起来
                    loadCache();
                    break;
                case CONSTANT.ID_LOAD_FROM_NET:
                    loadNewsFromNet();
                    break;
                case CONSTANT.ID_UPDATE_UI:
                    if (items.isEmpty()){
                        sad_face.setVisibility(View.VISIBLE);
                    }else{
                        sad_face.setVisibility(View.GONE);
                    }
                    progressBar.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    break;
            }
            return false;
        }
    });

    private synchronized void loadCache(){
        thread  = new Thread(new Runnable() {
            @Override
            public void run() {
                tmpItems.clear();
                List<Object> temList = cache.loadFromCache(category);
                for (Object object : temList){
                    tmpItems.add((BookBean) object);
                }
                temList.clear();
                items.clear();
                items.addAll(tmpItems);
                tmpItems.clear();
                if (progressBar.getVisibility() == View.VISIBLE) {
                    if (items.isEmpty()) {
                        handler.sendEmptyMessage(CONSTANT.ID_LOAD_FROM_NET);
                    }
                }
                handler.sendEmptyMessage(CONSTANT.ID_UPDATE_UI);
            }
        });
        thread.start();
    }
}























