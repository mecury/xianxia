package com.mecuryli.xianxia;

import android.app.Application;
import android.content.Context;

/**
 * Created by 海飞 on 2016/5/9.
 */
public class xianxiaApplication extends Application {

    public static Context AppContext = null;
    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = getApplicationContext();
    }
}
