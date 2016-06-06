package com.mecuryli.xianxia.ui.setting;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.support.CONSTANT;
import com.mecuryli.xianxia.support.Settings;
import com.mecuryli.xianxia.support.Utils;

/**
 * Created by 海飞 on 2016/6/3.
 */
public class SettingActivity extends AppCompatActivity implements SensorEventListener{
    private Toolbar toolbar;
    private int mLang = -1;

    private SensorManager mSensorManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLang = Utils.getCurrentLanguage();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (mLang > -1){
            Utils.changeLanguage(this, mLang);
        }

        setContentView(R.layout.activity_setting);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.setting);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getFragmentManager().beginTransaction().replace(R.id.framelayout,new SettingFragment()).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this,mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

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
        if (Settings.isShakeMode == false){
            return;
        }
        float value[] = event.values;
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            if (Math.abs(value[0])+Math.abs(value[1])+Math.abs(value[2])>CONSTANT.shakeValue){
                onBackPressed();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
