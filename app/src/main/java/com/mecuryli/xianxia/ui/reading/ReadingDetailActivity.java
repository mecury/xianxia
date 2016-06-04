package com.mecuryli.xianxia.ui.reading;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.api.ReadingApi;
import com.mecuryli.xianxia.model.reading.BookBean;
import com.mecuryli.xianxia.support.CONSTANT;
import com.mecuryli.xianxia.support.Settings;
import com.mecuryli.xianxia.support.Utils;
import com.mecuryli.xianxia.support.adapter.PagerAdapter;
import com.mecuryli.xianxia.ui.support.BaseWebViewActivity;

/**
 * Created by 海飞 on 2016/5/15.
 */
public class ReadingDetailActivity extends AppCompatActivity implements SensorEventListener{
    public static BookBean bookBean;
    private ViewPager viewPager;
    private PagerAdapter adapter;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private SimpleDraweeView image;

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

        setContentView(R.layout.activity_reading_details);
        initdata();

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }
    public void initdata(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpage);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        setSupportActionBar(toolbar);
        for (String title : ReadingApi.bookTab_title){
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }
        bookBean = (BookBean) getIntent().getSerializableExtra(getString(R.string.id_book));
        getSupportActionBar().setTitle(bookBean.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        adapter = new PagerAdapter(getSupportFragmentManager(),ReadingApi.bookTab_title){
            @Override
            public Fragment getItem(int position) {
                ReadingTabFragment fragment = new ReadingTabFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(getString(R.string.id_pos),position);
                fragment.setArguments(bundle);
                return fragment;
            }
        };
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ebook,menu);
        if (Utils.hasString(bookBean.getEbook_url())==false)
            menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_ebook:
                Intent intent = new Intent(ReadingDetailActivity.this, BaseWebViewActivity.class);
                intent.putExtra(getString(R.string.id_url),ReadingApi.readEBook+Utils.RegexFind("/[0-9]+/",bookBean.getEbook_url()));
                break;
        }
        return super.onOptionsItemSelected(item);
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
}





















