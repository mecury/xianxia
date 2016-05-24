package com.mecuryli.xianxia.ui.support;

import com.mecuryli.xianxia.R;

/**
 * Created by 海飞 on 2016/5/21.
 */
public class WebViewUrlActivity extends BaseWebViewActivity {
    @Override
    protected String getData() {
        return getIntent().getStringExtra(getString(R.string.id_url));
    }

    @Override
    protected void loadData() {
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(data);
            }
        });
    }
}
