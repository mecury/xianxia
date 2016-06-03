package com.mecuryli.xianxia.ui.support;

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
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.database.cache.ICache;
import com.mecuryli.xianxia.support.CONSTANT;
import com.mecuryli.xianxia.support.Utils;
import com.mecuryli.xianxia.xianxiaApplication;
import com.yalantis.phoenix.PullToRefreshView;

/**
 * Created by 海飞 on 2016/5/30.
 */
public abstract class BaseListFragment extends android.support.v4.app.Fragment {
    protected View parentView;
    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected PullToRefreshView refreshView;

    protected ImageView placeHolder;
    protected ProgressBar progressBar;

    protected RecyclerView.Adapter adapter;
    protected ICache cache;

    protected int mLayout = 0;

    protected boolean withHeaderTab = true;     //tab标题栏
    protected boolean withRefreshView = true;  //刷新状态
    protected boolean needCache = true;     //缓存

    //子类需要重写的方法
    protected abstract void onCreateCache();

    protected abstract RecyclerView.Adapter bindAdapter();

    protected abstract void loadFromNet();
    protected abstract void loadFromCache();
    protected abstract boolean hasData();
    protected abstract void getArgs();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setLayout();
        needCache = setCache();
        getArgs();
        parentView = inflater.inflate(mLayout,container,false);
        withHeaderTab = setHeaderTab();
        withRefreshView = setRefreshView();

        progressBar = (ProgressBar) parentView.findViewById(R.id.progressbar);
        recyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerView);
        placeHolder = (ImageView) parentView.findViewById(R.id.placeholder);

        onCreateCache();

        adapter = bindAdapter();

        mLayoutManager = new LinearLayoutManager(xianxiaApplication.AppContext);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(mLayoutManager);
        refreshView = (PullToRefreshView) parentView.findViewById(R.id.pull_to_refresh);

        View view = getActivity().findViewById(R.id.tab_layout);
        if (withHeaderTab){
            view.setVisibility(View.VISIBLE);
        }else{
            if (view != null){
                view.setVisibility(View.GONE);
            }
        }

        if (withRefreshView){
            refreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    loadFromNet();
                }
            });

            placeHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    placeHolder.setVisibility(View.GONE);
                    loadFromNet();
                }
            });
        }else{
            refreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refreshView.setRefreshing(false);
                }
            });
        }
        loadFromCache();
        return parentView;
    }

    protected boolean setHeaderTab(){
        return true;
    }    //tab切换标题栏
    protected boolean setRefreshView(){
        return true;
    }
    protected void setLayout(){
        mLayout = R.layout.layout_commont_list;
    }

    protected boolean setCache(){
        return true;
    }

    protected Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case CONSTANT.ID_FAILURE:
                    //当前fragment添加到activity
                    if (isAdded()){
                        Utils.DLog(getString(R.string.Text_Net_Exception));
                    }
                    break;
                case CONSTANT.ID_SUCCESS:
                    if (isAdded()){
                        Utils.DLog(getString(R.string.text_refresh_success));
                        refreshView.setRefreshing(false);
                    }
                    if (needCache){
                        cache.cache();
                    }
                    break;
                case CONSTANT.ID_LOAD_FROM_CACHE:
                    if (withRefreshView && hasData() == false){
                        loadFromNet();
                        return false;
                    }
                    break;
            }
            if (withRefreshView){
                refreshView.setRefreshing(false);
            }

            if (hasData()){
                placeHolder.setVisibility(View.GONE);
            }else{
                placeHolder.setVisibility(View.VISIBLE);
            }

            progressBar.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            return false;
        }
    });
}











