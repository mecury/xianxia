package com.mecuryli.xianxia.model.Daily;

import java.io.Serializable;

/**
 * Created by 海飞 on 2016/5/19.
 */
public class DailyStories implements Serializable {
    private String[] images;  //图片
    private int type;
    private int id;     //日报id
    private String ga_prefix;
    private String title;   //标题

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static class Image{
        public String getImage() {
            return images;
        }

        public void setImage(String images) {
            this.images = images;
        }

        private String images;

    }
}
