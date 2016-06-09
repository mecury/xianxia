package com.mecuryli.xianxia.ui.daily;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.model.Daily.DailyDetailsBean;
import com.mecuryli.xianxia.support.CONSTANT;
import com.mecuryli.xianxia.support.HttpUtil;
import com.mecuryli.xianxia.support.Utils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by 海飞 on 2016/6/9.
 */
public class DailyDetailActivity extends AppCompatActivity{
    private SimpleDraweeView simpleDraweeView;
    private Toolbar toolbar;
    private WebView webView;
    private ProgressBar progressBar;
    private DailyDetailsBean dailyDetailsBean;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_details);
        getData();
        initView();
    }
    private void getData(){
        url = getIntent().getStringExtra(getString(R.string.id_url));
        Utils.DLog(url);
    }
    private void initView(){
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.ivImage);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        webView = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                view.loadUrl("file:///android_asset/error.html");
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                view.loadUrl("file:///android_asset/error.html");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        setSupportActionBar(toolbar);
        loadDataFromNet();
    }

    private void loadDataFromNet(){
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        Request request = builder.build();
        HttpUtil.enqueue(request, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(CONSTANT.ID_FAILURE);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String res = response.body().string();
                Gson gson = new Gson();
                dailyDetailsBean = gson.fromJson(res, DailyDetailsBean.class);
                handler.sendEmptyMessage(CONSTANT.ID_SUCCESS);
            }
        });
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case CONSTANT.ID_FAILURE:
                    break;
                case CONSTANT.ID_SUCCESS:
                    getSupportActionBar().setTitle(dailyDetailsBean.getTitle());
                    simpleDraweeView.setImageURI(Uri.parse(dailyDetailsBean.getImage()));
                    webView.loadDataWithBaseURL("file:///android_asset/", "<link rel=\"stylesheet\" type=\"text/css\" href=\"dailycss.css\" />"+dailyDetailsBean.getBody(), "text/html", "utf-8", null);
                    break;
            }
            progressBar.setVisibility(View.GONE);
            return true;
        }
    });
}
