package com.mecuryli.xianxia.support;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;

/**
 * Created by 海飞 on 2016/5/19.
 */
public class ScreenUtil {
    private static int ScreenWidth = 0;
    private static int ScreenHeight = 0;
    private static Context mContext = null;
    public static void init(Context context){
        mContext = context;
    }

    public static int getScreenWidth(){
        if (ScreenWidth != 0)
            return ScreenWidth;
        Display display = ((AppCompatActivity)mContext).getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        ScreenWidth = point.x;
        return ScreenWidth;
    }

    public static int getScreenHeight(){
        if (ScreenWidth != 0)
            return ScreenHeight;
        Display display = ((AppCompatActivity)mContext).getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        ScreenWidth = point.y;
        return ScreenHeight;
    }
}
