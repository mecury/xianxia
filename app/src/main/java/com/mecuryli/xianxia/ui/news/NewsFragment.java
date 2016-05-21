package com.mecuryli.xianxia.ui.news;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.model.news.NewsBean;
import com.mecuryli.xianxia.support.CONSTANT;
import com.mecuryli.xianxia.support.HttpUtil;
import com.mecuryli.xianxia.support.Utils;
import com.mecuryli.xianxia.support.adapter.DividerItemDecoration;
import com.mecuryli.xianxia.support.adapter.NewsAdapter;
import com.mecuryli.xianxia.support.sax.SAXNewsParse;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yalantis.phoenix.PullToRefreshView;

import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by 海飞 on 2016/5/9.
 * 新闻列表界面的fragment
 */

public class NewsFragment extends android.support.v4.app.Fragment {

    private View parentView;
    private PullToRefreshView refreshView;
    private RecyclerView recyclerView;
    private RequestQueue queue;
    private List<NewsBean> items = new ArrayList<>();
    private NewsAdapter adapter ;
    private RecyclerView.LayoutManager mLayoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = View.inflate(getContext(), R.layout.layout_news,null);
        init();
        return parentView;
    }

    void init(){
        adapter = new NewsAdapter(getContext(), items);
        refreshView = (PullToRefreshView) parentView.findViewById(R.id.pull_to_refresh);
        recyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerView);
        final String url = getArguments().getString(getString(R.string.id_url));
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        loadNewsFormNet(url);

        refreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener(){

            @Override
            public void onRefresh() {
            loadNewsFormNet(url);
            }
        });
    }


    //由网络加载
    private void loadNewsFormNet(final String url){
//        queue = Volley.newRequestQueue(getContext());
//        StringRequest request = new StringRequest(url, new Response.Listener<String>(){
//
//            @TargetApi(Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onResponse(String s) {
//                InputStream is = new ByteArrayInputStream(s.getBytes(StandardCharsets.ISO_8859_1));
//                    try {
//                        items.clear();
//                        items.addAll(SAXNewsParse.parse(is)); //解析数据，并将数据添加到items中
//                    } catch (ParserConfigurationException e) {
//                        e.printStackTrace();
//                    } catch (SAXException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                handler.sendEmptyMessage(0);
//                refreshView.setRefreshing(false);
//            }
//        },new Response.ErrorListener(){
//
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Utils.showToast("网络异常，刷新失败");
//                refreshView.setRefreshing(false);
//            }
//        });
//        request.setShouldCache(false);
//        queue.add(request);
        refreshView.setRefreshing(true);
        new Thread(new Runnable() {
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
                        if (response.isSuccessful()==false){
                            handler.sendEmptyMessage(CONSTANT.ID_FAILURE);
                            return;
                        }
                        InputStream is = new ByteArrayInputStream(response.body().string().getBytes(StandardCharsets.UTF_8));
                        items.clear();

                        try {
                            items.addAll(SAXNewsParse.parse(is));
                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        } catch (SAXException e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(CONSTANT.ID_SUCCESS);
                    }
                });
            }
        }).start();
    }

    private Handler handler = new Handler(new Handler.Callback(){

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
