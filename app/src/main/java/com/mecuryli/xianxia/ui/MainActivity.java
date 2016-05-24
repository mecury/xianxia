package com.mecuryli.xianxia.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;

import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.support.Utils;
import com.mecuryli.xianxia.ui.daily.DailyFragment;
import com.mecuryli.xianxia.ui.news.BaseNewsFragment;
import com.mecuryli.xianxia.ui.reading.BaseReadingFragment;
import com.mecuryli.xianxia.ui.reading.ReadingActivity;
import com.mecuryli.xianxia.ui.science.BaseScienceFragment;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Drawer drawer;
    private AccountHeader header;

    private FrameLayout frameLayout;
    private android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        switchFragment(new DailyFragment(),getString(R.string.daily),R.menu.menu_daily);
    }

    private void switchFragment(Fragment fragment){
        if (fragment instanceof DailyFragment){
            switchFragment(fragment, getString(R.string.daily), R.menu.menu_daily);
        }else if (fragment instanceof BaseReadingFragment){
            switchFragment(fragment, getString(R.string.reading), R.menu.menu_reading);
        }else if (fragment instanceof BaseNewsFragment){
            switchFragment(fragment, getString(R.string.news), R.menu.menu_news);
        }else if (fragment instanceof BaseScienceFragment){
            switchFragment(fragment, getString(R.string.science),R.menu.menu_science);
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
    }


    void initData(){
        frameLayout = (FrameLayout) findViewById(R.id.fragment);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        header = new AccountHeaderBuilder().withActivity(this)
                .withCompactStyle(false)
                .withHeaderBackground(R.drawable.header)
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
                        new PrimaryDrawerItem().withName(R.string.video).withIcon(R.mipmap.ic_video).withIdentifier(R.mipmap.ic_video),
                        new PrimaryDrawerItem().withName(R.string.shake).withIcon(R.mipmap.ic_shake).withIdentifier(R.mipmap.ic_shake),
                        new SectionDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.setting).withIcon(R.mipmap.ic_setting).withIdentifier(R.mipmap.ic_setting),
                        new SecondaryDrawerItem().withName(R.string.about).withIcon(R.mipmap.ic_about).withIdentifier(R.mipmap.ic_about)
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (drawerItem.getIdentifier()) {
                            case R.mipmap.ic_news:
                                switchFragment(new BaseNewsFragment());
                                break;
                            case R.mipmap.ic_reading:
                                switchFragment(new BaseReadingFragment());
                                break;
                            case R.mipmap.ic_science:
                                switchFragment(new BaseScienceFragment());
                                break;
                            case R.mipmap.ic_home:
                                switchFragment(new DailyFragment());
                                break;
                            case R.mipmap.ic_music:
                                Toast.makeText(MainActivity.this, "music", Toast.LENGTH_SHORT).show();
                                break;
                            case R.mipmap.ic_setting:
                                Toast.makeText(MainActivity.this, "setting", Toast.LENGTH_SHORT).show();
                                break;
                            case R.mipmap.ic_about:
                                Toast.makeText(MainActivity.this, "about", Toast.LENGTH_SHORT).show();
                                break;
                            case R.mipmap.ic_shake:
                                Toast.makeText(MainActivity.this, "shake", Toast.LENGTH_SHORT).show();
                                break;
                        }
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
                switchFragment(new DailyFragment());
                break;
            case R.id.menu_reading:
                drawer.setSelection(R.mipmap.ic_reading);
                switchFragment(new BaseReadingFragment());
                break;
            case R.id.menu_news:
                drawer.setSelection(R.mipmap.ic_news);
                switchFragment(new BaseNewsFragment());
                break;
            case R.id.menu_science:
                drawer.setSelection(R.mipmap.ic_science);
                switchFragment(new BaseScienceFragment());
                break;
            case R.id.menu_search:
                showSearchDialog();
                break;
        }
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
}
