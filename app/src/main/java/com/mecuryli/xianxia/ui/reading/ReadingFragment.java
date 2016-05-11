package com.mecuryli.xianxia.ui.reading;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.api.ReadingApi;
import com.mecuryli.xianxia.model.reading.BookBean;
import com.mecuryli.xianxia.support.adapter.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 海飞 on 2016/5/11.
 */
public class ReadingFragment extends Fragment {

    private View parentView;
    private TextView textView;

    private List<BookBean> items = new ArrayList<>();
    private RequestQueue queue;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = View.inflate(getContext(), R.layout.layout_reading_list,null);
        textView = (TextView) parentView.findViewById(R.id.text);
        //textView.setText(getArguments().getInt("pos"));
        loadNewsFromNet(getArguments().getInt("pos"));
        return parentView;
    }

    private void loadNewsFromNet(int pos){
        queue = Volley.newRequestQueue(getContext());

        String[] tags = ReadingApi.getTags(ReadingApi.getApiTag(pos));
        for (int i =0; i <5; i++){
            String url = ReadingApi.searchByTag+tags[i]+"&count=100";
            Utils.DLog(url);
            final StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Gson gson = new Gson();
                    items.add(gson.fromJson(s, BookBean.class));
                    handler.sendEmptyMessage(0);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Utils.showToast("网络异常，刷新失败");
                }
            });
        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            textView.setText(items.toString());
            return false;
        }
    });
}
