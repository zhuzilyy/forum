package com.chuangsheng.forum.ui.mine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.util.WebviewUtil;

import butterknife.BindView;

public class WebviewActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.wv_webview)
    WebView wv_webview;
    @BindView(R.id.pb_webview)
    ProgressBar pb_webview;
    private WebSettings webSettings;
    @Override
    protected void initViews() {
        Intent intent=getIntent();
        if (intent!=null){
            Bundle bundle = intent.getExtras();
            String title=bundle.getString("title");
            String url=bundle.getString("url");
            tv_title.setText(title);
            webSettings=wv_webview.getSettings();
            WebviewUtil.setWebview(wv_webview, webSettings);
            wv_webview.loadUrl(url);
        }
    }

    @Override
    protected void initData() {
        // 加载webview
        loadingWebview();
    }
    private void loadingWebview() {
        wv_webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (pb_webview!=null){
                    pb_webview.setProgress(newProgress);
                    if(newProgress==100){
                        pb_webview.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_webview);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
}
