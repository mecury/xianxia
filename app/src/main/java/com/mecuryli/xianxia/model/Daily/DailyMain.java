package com.mecuryli.xianxia.model.Daily;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 海飞 on 2016/5/19.
 */
public class DailyMain implements Serializable{
    private String date;
    private List<DailyStories> stories;
    private List<DailyTop_stories> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<DailyStories> getStories() {
        return stories;
    }

    public void setStories(List<DailyStories> stories) {
        this.stories = stories;
    }

    public List<DailyTop_stories> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<DailyTop_stories> top_stories) {
        this.top_stories = top_stories;
    }
}
