package com.mecuryli.xianxia.ui.reading;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.api.ReadingApi;
import com.mecuryli.xianxia.model.reading.BookBean;
import com.mecuryli.xianxia.model.reading.ReadingBean;
import com.mecuryli.xianxia.support.CONSTANT;
import com.mecuryli.xianxia.support.HttpUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by 海飞 on 2016/5/24.
 */
public class ReadingActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        initData();
    }

    private void initData(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.text_search_result);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        url = ReadingApi.searchByText + getIntent().getStringExtra(getString(R.string.id_search_text));
        Log.e("","ReadingActivity的url："+url);
        BookListFragment fragment = new BookListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout,fragment);
        transaction.commit();
    }

    class BookListFragment extends ReadingFragment{
        @Override
        protected void getData() {
        }

        @Override
        protected void loadNewsFromNet() {
            refreshView.setRefreshing(true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Request.Builder build = new Request.Builder();
                    build.url(url);
                    Request request = build.build();
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
                            BookBean[] bookBeans = gson.fromJson(response.body().string(), ReadingBean.class).getBooks();
                            for (BookBean bookBean : bookBeans){
                                items.add(bookBean);
                            }
                            handler.sendEmptyMessage(CONSTANT.ID_SUCCESS);
                        }
                    });
                }
            }).start();
        }
    }
}














