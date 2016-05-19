package com.mecuryli.xianxia.ui.reading;

import android.content.Intent;
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
import com.mecuryli.xianxia.support.adapter.Utils;
import com.mecuryli.xianxia.support.adapter.PagerAdapter;
import com.mecuryli.xianxia.ui.support.WebViewActivity;

/**
 * Created by 海飞 on 2016/5/15.
 */
public class ReadingDetailActivity extends AppCompatActivity {
    public static BookBean bookBean;
    private ViewPager viewPager;
    private PagerAdapter adapter;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private SimpleDraweeView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_details);
        initdata();
    }
    public void initdata(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpage);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        setSupportActionBar(toolbar);
        for (String title : ReadingApi.bookTab_title){
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }
        bookBean = (BookBean) getIntent().getSerializableExtra("book");
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
                bundle.putInt("pos",position);
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
        if (bookBean.getEbook_url()==null || bookBean.getEbook_url().equals(""))
            menu.getItem(0).setVisible(false);
        else Utils.showToast(bookBean.getEbook_url());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_ebook:
                Intent intent = new Intent(ReadingDetailActivity.this, WebViewActivity.class);
                intent.putExtra("url",ReadingApi.readEBook+Utils.RegexFind("/[0-9]+/",bookBean.getEbook_url()));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}





















