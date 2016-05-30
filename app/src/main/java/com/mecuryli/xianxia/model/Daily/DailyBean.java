package com.mecuryli.xianxia.model.Daily;

/**
 * Created by 海飞 on 2016/5/19.
 */
public class DailyBean {
    private String image;  //图片
    private int type;
    private int id;     //日报id
    private String ga_prefix;
    private String title;   //标题

    private int is_collected = 0;

    public int getIs_collected() {
        return is_collected;
    }

    public void setIs_collected(int is_collected) {
        this.is_collected = is_collected;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
        public String images;

        public String getImage() {
            return images;
        }

        public void setImage(String images) {
            this.images = images;
        }
    }
}
