package com.mecuryli.xianxia.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.mecuryli.xianxia.R;
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
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    void initData(){
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        header = new AccountHeaderBuilder().withActivity(this)
                .withCompactStyle(false)
                .withHeaderBackground(R.drawable.header)
                .build();
        drawer = new DrawerBuilder().withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.news).withIdentifier(R.mipmap.ic_news),
                        new PrimaryDrawerItem().withName(R.string.reading).withIdentifier(R.mipmap.ic_reading),
                        new PrimaryDrawerItem().withName(R.string.science).withIdentifier(R.mipmap.ic_science),
                        new PrimaryDrawerItem().withName(R.string.video).withIdentifier(R.mipmap.ic_video),
                        new PrimaryDrawerItem().withName(R.string.music).withIdentifier(R.mipmap.ic_music),
                        new SectionDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.setting).withIcon(R.mipmap.ic_setting).withIdentifier(R.mipmap.ic_setting),
                        new SecondaryDrawerItem().withName(R.string.about).withIcon(R.mipmap.ic_about).withIdentifier(R.mipmap.ic_about)
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (drawerItem.getIdentifier()) {
                            case R.mipmap.ic_news:
                                Toast.makeText(MainActivity.this, "news", Toast.LENGTH_SHORT).show();
                                break;
                            case R.mipmap.ic_reading:
                                Toast.makeText(MainActivity.this, "reading", Toast.LENGTH_SHORT).show();
                                break;
                            case R.mipmap.ic_science:
                                Toast.makeText(MainActivity.this, "science", Toast.LENGTH_SHORT).show();
                                break;
                            case R.mipmap.ic_video:
                                Toast.makeText(MainActivity.this, "video", Toast.LENGTH_SHORT).show();
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
                        }
                        return false;
                    }
                }).build();
    }
}
