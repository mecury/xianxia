package com.mecuryli.xianxia.ui.about;

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
 * Created by 海飞 on 2016/6/2.
 */
public class AboutActivity extends AppCompatActivity implements SensorEventListener{

    private Toolbar toolbar;

    private SensorManager mSensorManager;
    private boolean isShakeMode = false;
    private int mLang = -1;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLang = Utils.getCurrentLanguage();
        if (mLang > -1){
            Utils.changeLanguage(this, mLang);
        }

        //夜间模式
        if (Settings.isNightMode){
            this.setTheme(R.style.NightTheme);
        }else{
            this.setTheme(R.style.DayTheme);
        }

        setContentView(R.layout.activity_about);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.about));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
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
            if (Math.abs(value[0])+Math.abs(value[1])+Math.abs(value[2])>CONSTANT.shakeValue){
                onBackPressed();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
