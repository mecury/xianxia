package com.mecuryli.xianxia.ui.reading;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.api.ReadingApi;
import com.mecuryli.xianxia.database.cache.cache.ReadingCache;
import com.mecuryli.xianxia.support.CONSTANT;
import com.mecuryli.xianxia.support.Settings;
import com.mecuryli.xianxia.support.Utils;
import com.mecuryli.xianxia.support.adapter.ReadingAdapter;
import com.mecuryli.xianxia.ui.support.BaseListFragment;

/**
 * Created by 海飞 on 2016/5/24.
 *
 * 搜索结束后的acitvity界面的
 */
public class ReadingActivity extends AppCompatActivity implements SensorEventListener{
    private Toolbar toolbar;
    private String url;
    private int mLang = -1;

    private SensorManager mSensorManager;
    private boolean isShakeMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLang = Utils.getCurrentLanguage();
        if (mLang > -1){
            Utils.changeLanguage(this, mLang);
        }

        setContentView(R.layout.activity_reading);
        initData();

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
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

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this,mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        isShakeMode = Settings.getInstance().getBoolean(Settings.SHAKE_TO_RETURN,true);
    }

    @Override
    protected void onStop() {
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (isShakeMode == false){
            return;
        }
        float value[] = event.values;
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            if (Math.abs(value[0])> CONSTANT.shakeValue || Math.abs(value[1])>CONSTANT.shakeValue || Math.abs(value[2])>CONSTANT.shakeValue){
                onBackPressed();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

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















