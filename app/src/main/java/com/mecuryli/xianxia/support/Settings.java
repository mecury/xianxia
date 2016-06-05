package com.mecuryli.xianxia.support;

import android.content.Context;
import android.content.SharedPreferences;

import com.mecuryli.xianxia.xianxiaApplication;

/**
 * Created by 海飞 on 2016/6/3.
 */
public class Settings {

    public static boolean needRecreate = false;
    public static final String XML_NAME = "settings";
    public static final String SHAKE_TO_RETURN = "shake_to_return";
    public static final String NO_PIC_MODE = "no_pic_mode";
    public static final String NIGHT_MODE = "night_mode";
    public static final String AUTO_REFRESH="auto_refresh";
    public static final String LANGUAGE = "language";
    public static final String EXIT_CONFIRM = "exit_confirm";

    private static Settings sInstance;

    private SharedPreferences mPrefs;

    public  static Settings getInstance(){
        if (sInstance == null){
            sInstance = new Settings(xianxiaApplication.AppContext);
        }
        return sInstance;
    }

    private Settings(Context context){
        mPrefs = context.getSharedPreferences(XML_NAME, Context.MODE_PRIVATE);
    }

    public Settings putBoolean(String key,boolean value){
        mPrefs.edit().putBoolean(key,value).commit();
        return this;
    }

    //第二个参数为当找不到key对应的value时，默认的返回值
    public boolean getBoolean(String key, boolean def){
        return mPrefs.getBoolean(key,def);
    }

    public Settings putInt(String key,int value){
        mPrefs.edit().putInt(key, value).commit();
        return this;
    }

    public int getInt(String key, int defValue){
        return mPrefs.getInt(key,defValue);
    }

    public Settings putString(String key,String value){
        mPrefs.edit().putString(key, value).commit();
        return this;
    }

    public String getString(String key,String defValue){
        return mPrefs.getString(key, defValue);
    }
}










