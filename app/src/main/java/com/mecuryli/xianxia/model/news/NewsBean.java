package com.mecuryli.xianxia.model.news;

import com.mecuryli.xianxia.support.adapter.Utils;

/**
 * Created by 海飞 on 2016/5/9.
 * 存储信息的是实体类
 */
public class NewsBean {
    private String title;
    private String link;
    private String description;
    private String pubTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = formatClearHtmlLable(title);
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //将description格式化
    public void setDescriptionWithFormat(String description){
        this.description = formatClearHtmlLable(description);
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime){
        this.pubTime = pubTime;
    }
    public void setPubTimeWithFormat(String pubTime) {
        this.pubTime = formatTime(pubTime);
    }

    private String formatClearHtmlLable(String string){
        return this.description = Utils.regexReplace("<[^>\n]*>",string,"");
    }

    //得到时间格式
    private String formatTime(String pubTime){
        Utils.DLog(pubTime);
        String date = Utils.RegexFind("-.{4} ",pubTime)+"年"+
                formatMonth(Utils.RegexFind("-.{3}-", pubTime)) + "月"+
                Utils.RegexFind(",.{1,2}-",pubTime) + "日" +
                Utils.RegexFind(" .{2}:",pubTime) + "点" +
                Utils.RegexFind(":.{2}:",pubTime) + "分" +
                Utils.RegexFind(":.{2} ",pubTime) + "秒";
        Utils.DLog("解析完后："+date);
        return date;
    }

    private final String MONTH[] = {"","Jan","Feb","Mar","Apr",
            "May","Jun","Jul","Aug","Sept","Oct","Nov","Dec"};

    private int formatMonth(String month){
        for (int i=1; i<MONTH.length; i++){
            if (month.equalsIgnoreCase(MONTH[i]))
                return i;
        }
        return -1;
    }
}
