package com.mecuryli.xianxia.model;

import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.support.adapter.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.InputStream;

/**
 * Created by 海飞 on 2016/5/10.
 * 用于存放res/raw/uews_api中的数据
 */
public class Entity {
    private static String[] newsUrl = null;
    private static String[] newsTitle = null;
    private static Document document = null;

    //DOM方式解析数据
    public static String[] getNewsUrl(){
        if (newsUrl == null){
            if (document == null){
                InputStream is = Utils.readFileFromRaw(R.raw.news_api);
                document = Utils.getDocmentByIS(is);
            }
            NodeList urlList = document.getElementsByTagName("url");
            newsUrl = new String[urlList.getLength()];
            for (int i = 0; i < urlList.getLength(); i++){
                newsUrl[i] = urlList.item(i).getTextContent();
            }
        }
        return newsUrl;
    }

    public static String[] getNewsTitle(){
        if (newsTitle == null){
            if (document == null){
                InputStream is = Utils.readFileFromRaw(R.raw.news_api);
                document = Utils.getDocmentByIS(is);
            }
            NodeList titleList = document.getElementsByTagName("name");
            newsTitle = new String[titleList.getLength()];
            for (int i = 0; i < titleList.getLength(); i++){
                newsTitle[i] = titleList.item(i).getTextContent();
            }
        }
        return newsTitle;
    }
}
