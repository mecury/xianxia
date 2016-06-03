package com.mecuryli.xianxia.ui.reading;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.api.ReadingApi;
import com.mecuryli.xianxia.database.cache.cache.ReadingCache;
import com.mecuryli.xianxia.support.adapter.ReadingAdapter;
import com.mecuryli.xianxia.ui.support.BaseListFragment;

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
        BookListFragment fragment = new BookListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout,fragment);
        transaction.commit();
    }

    class BookListFragment extends BaseListFragment{

        @Override
        protected boolean setHeaderTab() {
            return false;
        }

        @Override
        protected boolean setRefreshView() {
            return false;
        }

        @Override
        protected void onCreateCache() {
            cache = new ReadingCache(handler,null,new String[]{url});
        }

        @Override
        protected RecyclerView.Adapter bindAdapter() {
            return new ReadingAdapter(getContext(),cache);
        }

        @Override
        protected void loadFromNet() {
            cache.load();
        }

        @Override
        protected void loadFromCache() {
            loadFromNet();
        }

        @Override
        protected boolean hasData() {
            return cache.hasData();
        }

        @Override
        protected void getArgs() {

        }

        @Override
        protected boolean setCache() {
            return false;
        }
        /*@Override
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
        }*/
    }
}















