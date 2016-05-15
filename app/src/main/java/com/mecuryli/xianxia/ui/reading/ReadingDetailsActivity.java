package com.mecuryli.xianxia.ui.reading;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.support.adapter.adapter.PagerAdapter;

/**
 * Created by 海飞 on 2016/5/15.
 */
public class ReadingDetailsActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private PagerAdapter adapter;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private String [] tabsTitle={"内容简介","目录", "作者简介"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_details);

    }
    public void initdata(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpage);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        setSupportActionBar(toolbar);
        for (String title : tabsTitle){
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }
        adapter = new PagerAdapter(getSupportFragmentManager(),tabsTitle){
            @Override
            public Fragment getItem(int position) {
                ReadingTabFragment fragment = new ReadingTabFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("pos",position);
                fragment.setArguments(bundle);
                return fragment;
            }
        };
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
