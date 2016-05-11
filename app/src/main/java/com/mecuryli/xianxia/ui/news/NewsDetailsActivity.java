package com.mecuryli.xianxia.ui.news;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.mecuryli.xianxia.R;

/**
 * Created by 海飞 on 2016/5/10.
 */
public class NewsDetailsActivity extends AppCompatActivity {

    private WebView webView;
    private TextView textView;
    private boolean isLoading = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        initData();
    }

    private void initData(){
        webView = (WebView) findViewById(R.id.webView);
        textView = (TextView) findViewById(R.id.text_notify);
        final String url = getIntent().getStringExtra("url");
        webView.getSettings().setJavaScriptEnabled(true); //是否支持javaScript
        webView.getSettings().setSupportMultipleWindows(false);
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(url);
            }
        });
        //setWebViewClient主要用于处理解析，渲染网页等浏览器做的事情
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                textView.setVisibility(View.GONE);
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
                    textView.setText("正在加载。。。"+newProgress*4+"%");
                    if (newProgress > 25){
                        isLoading = false;
                        textView.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
}
