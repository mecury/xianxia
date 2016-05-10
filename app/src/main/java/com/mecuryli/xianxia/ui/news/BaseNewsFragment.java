package com.mecuryli.xianxia.ui.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.support.adapter.Utils;
import com.mecuryli.xianxia.support.adapter.adapter.PagerAdapter;
import com.mecuryli.xianxia.ui.AbsTopNavigationFragment;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.InputStream;

/**
 * Created by 海飞 on 2016/5/9.
 */
public class BaseNewsFragment extends AbsTopNavigationFragment {

    private PagerAdapter pagerAdapter;
    private String[] name;
    private String[] url;
    @Override
    protected PagerAdapter initPagerAdapter() {
        //由api打开输入流
        InputStream is = Utils.readFileFromRaw(R.raw.news_api);
        Document document = Utils.getDocmentByIS(is);
        NodeList urlList = document.getElementsByTagName("url");
        NodeList nameList = document.getElementsByTagName("name");
        int nodeLength = urlList.getLength();
        name = new String[nodeLength];
        url = new String[nodeLength];

        for (int i = 0; i < nodeLength; i++){
            url[i] = urlList.item(i).getTextContent();
            name[i] = nameList.item(i).getTextContent();
        }

        pagerAdapter = new PagerAdapter(getFragmentManager(),name){
            @Override
            public Fragment getItem(int position) {
                NewsFragment fragment = new NewsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("url",url[position]);
                bundle.putString("name",name[position]);
                fragment.setArguments(bundle);
                return fragment;
            }
        };
        return pagerAdapter;
    }
}
