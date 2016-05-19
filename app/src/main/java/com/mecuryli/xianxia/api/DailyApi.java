package com.mecuryli.xianxia.api;

/**
 * Created by 海飞 on 2016/5/18.
 */
public class DailyApi {
    public static String newsLatest = "http://news-at.zhihu.com/api/4/news/latest"; //最新消息的获取
    public static String newsDetail = "http://news-at.zhihu.com/api/4/news/"; //后面加上id,可以获得当前id内容的所代表新闻的详细信息。如：3892357
    public static String newsBefore = "http://news.at.zhihu.com/api/4/news/before/";//后面加上时间，获得那天的新闻内容。如：20131119
}
