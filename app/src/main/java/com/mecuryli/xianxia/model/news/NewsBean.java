package com.mecuryli.xianxia.model.news;

import com.mecuryli.xianxia.support.CONSTANT;
import com.mecuryli.xianxia.support.Utils;

/**
 * Created by 海飞 on 2016/5/9.
 * 存储信息的是实体类
 */
public class NewsBean {
    private String title;
    private String link;
    private String description;
    private String pubTime;

    public int getIS_COLLECTED() {
        return IS_COLLECTED;
    }

    public void setIS_COLLECTED(int IS_COLLECTED) {
        this.IS_COLLECTED = IS_COLLECTED;
    }

    private int IS_COLLECTED = 0;

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
        return date;
    }

    private int formatMonth(String month){
        for (int i=1; i< CONSTANT.MONTH.length; i++){
            if (month.equalsIgnoreCase(CONSTANT.MONTH[i]))
                return i;
        }
        return -1;
    }
}
