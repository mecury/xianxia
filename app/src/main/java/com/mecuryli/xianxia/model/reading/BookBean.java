package com.mecuryli.xianxia.model.reading;


import java.io.Serializable;

/**
 * Created by 海飞 on 2016/5/11.
 */
public class BookBean implements Serializable {
    private int count;
    private int start;
    private int total;
    private class Book{
        Rating rating;
        String subTitle;
        String [] author;
        String pubdate;
        Tags [] tages;
        String origin_title;
        String image;
        String binding;
        String [] translator;
        String catalog;
        String ebook_url;
        int pages;
        Images images;
        int id;
        String alt;
        String publisher;
        int isbn10;
        int isbn13;
        String title;
        String url;
        String alt_title;
        String aurhor_intro;
        String summary;
        double ebook_price;
        Series series;
        double price;
    }

    private class Rating implements Serializable{
        int max;
        int numRaters;
        double average;
        int min;
    }
    private class Tags implements Serializable{
        int count;
        String name;
        String title;
    }
    private class Images implements  Serializable{
        String small;
        String large;
        String medium;
    }
    private class Series implements Serializable{
        int id;
        double price;
    }
}
