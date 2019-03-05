package com.chuangsheng.forum.ui.mine.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.util.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class SafeCenterActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @Override
    protected void initViews() {
        tv_title.setText("安全中心");
        BaseActivity.activityList.add(this);
    }
    @Override
    protected void initData() {

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_safe_center);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.rl_changePhone,R.id.rl_bindEmail,R.id.iv_back})
    public void click(View view){
        switch (view.getId()){
            case R.id.rl_changePhone:
                jumpActivity(this,ChangePhoneActivity.class);
                break;
            case R.id.rl_bindEmail:
                String user_id = (String) SPUtils.get(SafeCenterActivity.this, "user_id", "");
                Bundle bundle = new Bundle();
                bundle.putString("userId", user_id);
                bundle.putString("intentFrom","safeCenter");
                jumpActivity(this,BindEmailActivity.class,bundle);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
