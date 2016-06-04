package com.mecuryli.xianxia.support;

import android.content.Context;
import android.net.ConnectivityManager;

import com.mecuryli.xianxia.xianxiaApplication;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * Created by 海飞 on 2016/5/19.
 * 对OKHttp进行的进行的封装
 */
public class HttpUtil {
    private static final OkHttpClient mOkHttpClient = new OkHttpClient();
    public static boolean isWIFI = true;
    static {
        mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(30,TimeUnit.SECONDS);
    }

    //不开启异步线程访问网络
    public static Response execute(Request request) throws IOException {
        return mOkHttpClient.newCall(request).execute();
    }
    //开启异步线程访问网络
    public static void enqueue(Request request, Callback responseCallback){
        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }
    //开启异步线程访问网络，且不在意返回结果（实现空callback）
    public static void enqueue(Request request){
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

            }
        });
    }

    public static boolean readNetworkState(){
        ConnectivityManager cm = (ConnectivityManager) xianxiaApplication.AppContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null && cm.getActiveNetworkInfo()!=null && cm.getActiveNetworkInfo().isConnected()){
            isWIFI = (cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI);
            return true;
        }else{
            return false;
        }
    }
}
