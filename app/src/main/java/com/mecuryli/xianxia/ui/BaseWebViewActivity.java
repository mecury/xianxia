package com.mecuryli.xianxia.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.mecuryli.xianxia.R;

/**
 * Created by 海飞 on 2016/5/10.
 */
public abstract class BaseWebViewActivity extends AppCompatActivity {

    protected WebView webView;
    protected ProgressBar progressBar;
    protected boolean isLoading = true;
    protected String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initData();
    }

    protected abstract String getData();
    protected abstract void loadData();

    private void initData(){
        data = getData();
        webView = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.pb_progerssbar);
        webView.getSettings().setJavaScriptEnabled(true); //是否支持javaScript
        webView.getSettings().setSupportMultipleWindows(false);

        loadData();

        //setWebViewClient主要用于处理解析，渲染网页等浏览器做的事情
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        //setWebChrome是辅助webView处理javascript的对话框，网站图标、网站title、加载进度
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (isLoading){
                    progressBar.incrementProgressBy(newProgress-progressBar.getProgress());
                    if (newProgress > 25){
                        isLoading = false;
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
}
