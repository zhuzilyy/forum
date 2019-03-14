package com.chuangsheng.forum.ui.mine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.ui.account.ui.FindByEmailActivity;
import com.chuangsheng.forum.util.ToastUtils;
import com.chuangsheng.forum.util.WebviewUtil;
import com.chuangsheng.forum.view.MyCountDownTimer;

import butterknife.BindView;
import butterknife.OnClick;

public class WebviewActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.wv_webview)
    WebView wv_webview;
    @BindView(R.id.pb_webview)
    ProgressBar pb_webview;
    private WebSettings webSettings;

    /**
     *
     */
    @Override
    protected void initViews() {
        Intent intent=getIntent();
        if (intent!=null){
            Bundle bundle = intent.getExtras();
            String title=bundle.getString("title");
            String url=bundle.getString("url");
            tv_title.setText(title);
            tv_title.setSingleLine(true);
            tv_title.setMaxEms(10);
            tv_title.setEllipsize(TextUtils.TruncateAt.END);
            webSettings=wv_webview.getSettings();
            WebviewUtil.setWebview(wv_webview, webSettings);
            wv_webview.loadUrl(url);
        }
        BaseActivity.activityList.add(this);
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
    @OnClick({R.id.iv_back})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
