package com.mecuryli.xianxia.ui;

import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.ui.BaseWebViewActivity;

/**
 * Created by 海飞 on 2016/5/21.
 */
public class WebViewLocalActivity extends BaseWebViewActivity{
        @Override
        protected String getData() {
            return  getIntent().getStringExtra(getString(R.string.id_html_content));
        }
        @Override
        protected void loadData() {
            webView.loadDataWithBaseURL("about:blank", data, "text/html", "utf-8", null);
        }
}
