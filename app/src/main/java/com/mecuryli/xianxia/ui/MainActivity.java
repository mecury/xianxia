package com.mecuryli.xianxia.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.support.CONSTANT;
import com.mecuryli.xianxia.support.Settings;
import com.mecuryli.xianxia.support.Utils;
import com.mecuryli.xianxia.ui.about.AboutActivity;
import com.mecuryli.xianxia.ui.collection.BaseCollectionFragment;
import com.mecuryli.xianxia.ui.daily.DailyFragment;
import com.mecuryli.xianxia.ui.news.BaseNewsFragment;
import com.mecuryli.xianxia.ui.reading.BaseReadingFragment;
import com.mecuryli.xianxia.ui.reading.ReadingActivity;
import com.mecuryli.xianxia.ui.science.BaseScienceFragment;
import com.mecuryli.xianxia.ui.setting.SettingActivity;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private Toolbar toolbar;
    private Drawer drawer;
    private AccountHeader header;

    private FrameLayout frameLayout;
    private android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction;
    private Fragment currentFragment;
    private Menu menu;

    private int mLang = -1; //选择语言

    private SensorManager mSensorManager; //晃动感应

    private boolean isShake = false;
    private boolean isShakeMode = true;
    private boolean isExitConfirm = true;
    private long lastPressTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLang = Utils.getCurrentLanguage();
        if (mLang > -1){
            Utils.changeLanguage(this, mLang);
        }

        setContentView(R.layout.activity_main);
        initData();

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        currentFragment = new DailyFragment();
        switchFragment();
    }

    private void switchFragment(){

        if (currentFragment instanceof DailyFragment){
            switchFragment(currentFragment, getString(R.string.daily), R.menu.menu_daily);
        }else if (currentFragment instanceof BaseReadingFragment){
            switchFragment(currentFragment, getString(R.string.reading), R.menu.menu_reading);
        }else if (currentFragment instanceof BaseNewsFragment){
            switchFragment(currentFragment, getString(R.string.news), R.menu.menu_news);
        }else if (currentFragment instanceof BaseScienceFragment){
            switchFragment(currentFragment, getString(R.string.science),R.menu.menu_science);
        }else if (currentFragment instanceof BaseCollectionFragment){
            switchFragment(currentFragment,getString(R.string.collection),R.menu.menu_daily);
        }
    }
    //切换fragment
    private void switchFragment(Fragment fragment, String title, int resourceMenu){

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(title);
        if (menu != null){
            menu.clear();
            getMenuInflater().inflate(resourceMenu, menu);
        }
        fragment = null;
    }


    void initData(){
        frameLayout = (FrameLayout) findViewById(R.id.fragment);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        header = new AccountHeaderBuilder().withActivity(this)
                .withCompactStyle(false)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(new ProfileDrawerItem().withIcon(R.drawable.logo)
                    .withEmail(getString(R.string.author_email))
                    .withName(getString(R.string.author_name)))
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(intent);
                        return false;
                    }
                })
                .build();
        drawer = new DrawerBuilder().withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.daily).withIcon(R.mipmap.ic_home).withIdentifier(R.mipmap.ic_home),
                        new PrimaryDrawerItem().withName(R.string.news).withIcon(R.mipmap.ic_news).withIdentifier(R.mipmap.ic_news),
                        new PrimaryDrawerItem().withName(R.string.reading).withIcon(R.mipmap.ic_reading).withIdentifier(R.mipmap.ic_reading),
                        new PrimaryDrawerItem().withName(R.string.science).withIcon(R.mipmap.ic_science).withIdentifier(R.mipmap.ic_science),
                        new PrimaryDrawerItem().withName(R.string.collection).withIcon(R.mipmap.ic_collect).withIdentifier(R.mipmap.ic_collect),
                        new SectionDrawerItem().withName(R.string.app_name),
                        new SecondaryDrawerItem().withName(R.string.setting).withIcon(R.mipmap.ic_setting).withIdentifier(R.mipmap.ic_setting),
                        new SecondaryDrawerItem().withName(R.string.about).withIcon(R.mipmap.ic_about).withIdentifier(R.mipmap.ic_about)
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (drawerItem.getIdentifier()) {
                            case R.mipmap.ic_news:
                                if (currentFragment instanceof BaseNewsFragment){
                                    return false;
                                }
                                currentFragment = new BaseNewsFragment();
                                break;
                            case R.mipmap.ic_reading:
                                if (currentFragment instanceof BaseReadingFragment){
                                    return false;
                                }
                                currentFragment = new BaseReadingFragment();
                                break;
                            case R.mipmap.ic_science:
                                if (currentFragment instanceof BaseScienceFragment){
                                    return false;
                                }
                                currentFragment = new BaseScienceFragment();
                                break;
                            case R.mipmap.ic_home:
                                if (currentFragment instanceof DailyFragment){
                                    return false;
                                }
                                currentFragment = new DailyFragment();
                                break;
                            case R.mipmap.ic_collect:
                                if (currentFragment instanceof BaseCollectionFragment){
                                    return false;
                                }
                                currentFragment = new BaseCollectionFragment();
                                break;
                            case R.mipmap.ic_setting:
                                Intent toSetting = new Intent(MainActivity.this, SettingActivity.class);
                                startActivity(toSetting);
                                return false;
                            case R.mipmap.ic_about:
                                Intent toAbout = new Intent(MainActivity.this,AboutActivity.class);
                                startActivity(toAbout);
                                return false;
                        }
                        switchFragment();
                        return false;
                    }
                }).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_daily, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_home:
                drawer.setSelection(R.mipmap.ic_home);
                currentFragment = new DailyFragment();
                break;
            case R.id.menu_reading:
                drawer.setSelection(R.mipmap.ic_reading);
                currentFragment = new BaseReadingFragment();
                break;
            case R.id.menu_news:
                drawer.setSelection(R.mipmap.ic_news);
                currentFragment = new BaseNewsFragment();
                break;
            case R.id.menu_science:
                drawer.setSelection(R.mipmap.ic_science);
                currentFragment = new BaseScienceFragment();
                break;
            case R.id.menu_search:
                showSearchDialog();
                return  true;
        }
        switchFragment();
        currentFragment = null;
        return super.onOptionsItemSelected(item);
    }

    private void showSearchDialog(){
        final EditText editText = new EditText(this);
        editText.setGravity(Gravity.CENTER);
        editText.setSingleLine();
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.text_search_books))
                .setIcon(R.mipmap.ic_search)
                .setView(editText)
                .setPositiveButton(getString(R.string.text_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Utils.hasString(editText.getText().toString())){
                            Intent intent = new Intent(MainActivity.this, ReadingActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(getString(R.string.id_search_text),editText.getText().toString());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                }).show();
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen()){
            drawer.closeDrawer();
        }else if (isShake == false && canExit()){
            super.onBackPressed();
        }
        isShake = false;
    }

    public boolean canExit(){
        if (isExitConfirm){
            if (System.currentTimeMillis()-lastPressTime>CONSTANT.exitConfirmTime){
                lastPressTime = System.currentTimeMillis();
                Snackbar.make(getCurrentFocus(),R.string.notify_exit_confirm,Snackbar.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        isShakeMode = Settings.getInstance().getBoolean(Settings.SHAKE_TO_RETURN,true);
        if (Settings.needRecreate){
            Settings.needRecreate = false;
            this.recreate();
        }
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
            return ;
        }
        float value[] = event.values;
        if (event.sensor.getType() ==Sensor.TYPE_ACCELEROMETER){
            if (Math.abs(value[0])+Math.abs(value[1])+Math.abs(value[2])>CONSTANT.shakeValue){
                isShake =true;

                Utils.DLog("Value.length"+value.length + " " + value[0] + " " + value[1]+" "+ value[2]);
                onBackPressed();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
