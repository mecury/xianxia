package com.mecuryli.xianxia.ui.about;

import com.mecuryli.xianxia.ui.support.WebViewUrlActivity;

/**
 * Created by 海飞 on 2016/6/9.
 */
public class AppIntroActivity extends WebViewUrlActivity {
    @Override
    protected void loadData() {
        webView.loadUrl("file:///android_asset/LeisureIntroduction.html");
    }
}
