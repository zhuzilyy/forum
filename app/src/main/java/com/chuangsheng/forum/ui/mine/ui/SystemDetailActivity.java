package com.chuangsheng.forum.ui.mine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SystemDetailActivity extends BaseActivity {
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.iv_pic)
    ImageView iv_pic;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_subject)
    TextView tv_subject;
    @Override
    protected void initViews() {
        tv_title.setText("消息详情");
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.pic);
        Intent intent = getIntent();
        if (intent!=null){
            Bundle extras = intent.getExtras();
            String content = extras.getString("content");
            String time = extras.getString("time");
            String pic = extras.getString("pic");
            String subject = extras.getString("subject");
            tv_subject.setText(subject);
            tv_content.setText(content);
            tv_time.setText(time);
            if (!TextUtils.isEmpty(pic)){
                iv_pic.setVisibility(View.VISIBLE);
                Glide.with(this).applyDefaultRequestOptions(options).load(pic).into(iv_pic);
            }else{
                iv_pic.setVisibility(View.GONE);
            }
            BaseActivity.activityList.add(this);
        }
    }
    @Override
    protected void initData() {

    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_news_detail);
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
