package com.mecuryli.xianxia.ui.about;

import com.mecuryli.xianxia.support.CONSTANT;
import com.mecuryli.xianxia.ui.support.WebViewUrlActivity;

/**
 * Created by 海飞 on 2016/6/9.
 */
public class DemoVideoActivity extends WebViewUrlActivity {
    @Override
    protected void loadData() {
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(CONSTANT.DEMO_VIDEO_URL);
            }
        });
    }
}
