package com.mecuryli.xianxia.model.reading;


import android.content.Context;

import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.xianxiaApplication;

import java.io.Serializable;

/**
 * Created by 海飞 on 2016/5/11.
 */
public class BookBean implements Serializable {

        String [] author;
        String pubdate;
        String image;
        String catalog;
        String ebook_url;
        String pages;
        String id;
        String title;
        String author_intro;
        String summary;
    /*
    self define
     */
    public String info;
    public int is_collected = 0;

    public String getInfo() {
        if(info == null) return toString();
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getIs_collected() {
        return is_collected;
    }

    public void setIs_collected(int is_collected) {
        this.is_collected = is_collected;
    }

    public String[] getAuthor() {
        return author;
    }

    public void setAuthor(String[] author) {
        this.author = author;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getEbook_url() {
        return ebook_url;
    }

    public void setEbook_url(String ebook_url) {
        this.ebook_url = ebook_url;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor_intro() {
        return author_intro;
    }

    public void setAuthor_intro(String author_intro) {
        this.author_intro = author_intro;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    String price;



    private class Rating implements Serializable{
        int max;
        int numRaters;
        String average;
        int min;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getNumRaters() {
            return numRaters;
        }

        public void setNumRaters(int numRaters) {
            this.numRaters = numRaters;
        }

        public String getAverage() {
            return average;
        }

        public void setAverage(String average) {
            this.average = average;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }
    private class Tags implements Serializable{
        int count;
        String name;
        String title;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
    private class Images implements  Serializable{
        String small;
        String large;
        String medium;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }
    }
    private class Series implements Serializable{
        int id;
        String price;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    @Override
    public String toString() {
        Context mContext = xianxiaApplication.AppContext;
        StringBuffer sb = new StringBuffer();
        for ( String s : getAuthor()){
            sb.append(" " + s);
        }
        return mContext.getString(R.string.text_author) + sb + "\n"+
                mContext.getString(R.string.text_pubdate)+getPubdate()+"\n" +
                mContext.getString(R.string.pages) + getPages()+"\n"+
                mContext.getString(R.string.text_price)+getPrice();
    }
}
